package com.esmt.cartographiegestionprojet.service;

import com.esmt.cartographiegestionprojet.dto.ProjetCreateDTO;
import com.esmt.cartographiegestionprojet.dto.ProjetDTO;
import com.esmt.cartographiegestionprojet.entity.DomaineRecherche;
import com.esmt.cartographiegestionprojet.entity.Projet;
import com.esmt.cartographiegestionprojet.entity.User;
import com.esmt.cartographiegestionprojet.exception.BusinessException;
import com.esmt.cartographiegestionprojet.exception.NotFoundException;
import com.esmt.cartographiegestionprojet.exception.UnauthorizedException;
import com.esmt.cartographiegestionprojet.mapper.ProjetMapper;
import com.esmt.cartographiegestionprojet.repository.ProjetRepository;
import com.esmt.cartographiegestionprojet.utils.Role;
import com.esmt.cartographiegestionprojet.utils.StatutProjet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjetService {

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DomaineRechercheService domaineService;

    @Autowired
    private ParticipationService participationService;

    @Autowired
    private ProjetMapper projetMapper;

    /**
     * Créer un nouveau projet
     */
    @Transactional
    public ProjetDTO createProjet(ProjetCreateDTO dto, UUID createurId) {
        User createur = userService.getUserEntityById(createurId);

        validateProjetData(dto);

        DomaineRecherche domaine = domaineService.getDomaineEntityById(dto.getDomaineId());

        Projet projet = projetMapper.toEntity(dto, domaine, createur);
        Projet savedProjet = projetRepository.save(projet);

        // Le créateur devient automatiquement participant
        participationService.addParticipant(savedProjet.getProjectId(), createurId, "Créateur/Responsable");

        return projetMapper.toDTO(savedProjet);
    }

    /**
     * Récupérer un projet par ID (avec contrôle d'accès)
     */
    public ProjetDTO getProjetById(UUID projetId, UUID userId) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new NotFoundException("Projet non trouvé avec l'ID : " + projetId));

        User user = userService.getUserEntityById(userId);

        if (!canAccessProjet(projet, user)) {
            throw new UnauthorizedException("Vous n'avez pas accès à ce projet");
        }

        return projetMapper.toDTO(projet);
    }

    /**
     * Récupérer tous les projets (filtré selon le rôle)
     */
    public List<ProjetDTO> getAllProjets(UUID userId) {
        User user = userService.getUserEntityById(userId);

        List<Projet> projets;

        if (user.getRole() == Role.ADMIN || user.getRole() == Role.GESTIONNAIRE) {
            projets = projetRepository.findAll();
        } else {
            projets = projetRepository.findByParticipations_Participant(user);
        }

        return projets.stream()
                .map(projetMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer les projets d'un utilisateur
     */
    public List<ProjetDTO> getMesProjets(UUID userId) {
        User user = userService.getUserEntityById(userId);
        List<Projet> projets = projetRepository.findByParticipations_Participant(user);

        return projets.stream()
                .map(projetMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer les projets par domaine
     */
    public List<ProjetDTO> getProjetsByDomaine(UUID domaineId) {
        DomaineRecherche domaine = domaineService.getDomaineEntityById(domaineId);
        List<Projet> projets = projetRepository.findByDomaineRecherche(domaine);

        return projets.stream()
                .map(projetMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer les projets par statut
     */
    public List<ProjetDTO> getProjetsByStatut(StatutProjet statut) {
        List<Projet> projets = projetRepository.findByStatutProjet(statut);

        return projets.stream()
                .map(projetMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Mettre à jour un projet
     */
    @Transactional
    public ProjetDTO updateProjet(UUID projetId, ProjetCreateDTO dto, UUID userId) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new NotFoundException("Projet non trouvé"));

        User user = userService.getUserEntityById(userId);

        if (!canModifyProjet(projet, user)) {
            throw new UnauthorizedException("Vous n'êtes pas autorisé à modifier ce projet");
        }

        validateProjetData(dto);

        projetMapper.updateEntityFromDTO(projet, dto);

        if (dto.getDomaineId() != null) {
            DomaineRecherche domaine = domaineService.getDomaineEntityById(dto.getDomaineId());
            projet.setDomaineRecherche(domaine);
        }

        Projet updatedProjet = projetRepository.save(projet);
        return projetMapper.toDTO(updatedProjet);
    }

    /**
     * Mettre à jour le statut d'un projet
     */
    @Transactional
    public ProjetDTO updateStatut(UUID projetId, StatutProjet nouveauStatut, UUID userId) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new NotFoundException("Projet non trouvé"));

        User user = userService.getUserEntityById(userId);

        if (!canModifyProjet(projet, user)) {
            throw new UnauthorizedException("Vous n'êtes pas autorisé à modifier ce projet");
        }

        if (nouveauStatut == StatutProjet.TERMINE && projet.getNiveauAvancement() < 100) {
            throw new BusinessException(
                    "Impossible de marquer comme terminé : avancement à " + projet.getNiveauAvancement() + "%"
            );
        }

        projet.setStatutProjet(nouveauStatut);

        Projet updatedProjet = projetRepository.save(projet);
        return projetMapper.toDTO(updatedProjet);
    }

    /**
     * Mettre à jour le niveau d'avancement
     */
    @Transactional
    public ProjetDTO updateAvancement(UUID projetId, Integer niveauAvancement, UUID userId) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new NotFoundException("Projet non trouvé"));

        User user = userService.getUserEntityById(userId);

        if (!canModifyProjet(projet, user)) {
            throw new UnauthorizedException("Vous n'êtes pas autorisé à modifier ce projet");
        }

        if (niveauAvancement < 0 || niveauAvancement > 100) {
            throw new BusinessException("L'avancement doit être entre 0 et 100");
        }

        projet.setNiveauAvancement(niveauAvancement);

        if (niveauAvancement == 100 && projet.getStatutProjet() == StatutProjet.EN_COURS) {
            projet.setStatutProjet(StatutProjet.TERMINE);
        }

        Projet updatedProjet = projetRepository.save(projet);
        return projetMapper.toDTO(updatedProjet);
    }

    /**
     * Supprimer un projet
     */
    @Transactional
    public void deleteProjet(UUID projetId, UUID userId) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new NotFoundException("Projet non trouvé"));

        User user = userService.getUserEntityById(userId);

        if (user.getRole() != Role.ADMIN && user.getRole() != Role.GESTIONNAIRE) {
            throw new UnauthorizedException("Seuls les administrateurs et gestionnaires peuvent supprimer un projet");
        }

        projetRepository.deleteById(projetId);
    }

    /**
     * Validation des données du projet
     */
    private void validateProjetData(ProjetCreateDTO dto) {
        if (dto.getTitreProjet() == null || dto.getTitreProjet().trim().isEmpty()) {
            throw new BusinessException("Le titre du projet est obligatoire");
        }
        if (dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
            throw new BusinessException("La description est obligatoire");
        }
        if (dto.getDateDebut() == null) {
            throw new BusinessException("La date de début est obligatoire");
        }
        if (dto.getDateFin() == null) {
            throw new BusinessException("La date de fin est obligatoire");
        }
        if (dto.getDateFin().isBefore(dto.getDateDebut())) {
            throw new BusinessException("La date de fin doit être après la date de début");
        }
        if (dto.getBudgetEstime() < 0) {
            throw new BusinessException("Le budget doit être positif");
        }
    }

    /**
     * Vérifier si un utilisateur peut accéder à un projet
     */
    private boolean canAccessProjet(Projet projet, User user) {
        if (user.getRole() == Role.ADMIN || user.getRole() == Role.GESTIONNAIRE) {
            return true;
        }

        return projet.getResponsableProjet().getId().equals(user.getId()) ||
                projet.getParticipations().stream()
                        .anyMatch(p -> p.getParticipant().getId().equals(user.getId()) && p.getActif());
    }

    /**
     * Vérifier si un utilisateur peut modifier un projet
     */
    private boolean canModifyProjet(Projet projet, User user) {
        if (user.getRole() == Role.ADMIN || user.getRole() == Role.GESTIONNAIRE) {
            return true;
        }

        return projet.getResponsableProjet().getId().equals(user.getId());
    }
}