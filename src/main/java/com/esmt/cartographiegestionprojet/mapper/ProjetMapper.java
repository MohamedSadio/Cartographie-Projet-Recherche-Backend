package com.esmt.cartographiegestionprojet.mapper;

import com.esmt.cartographiegestionprojet.dto.ProjetCreateDTO;
import com.esmt.cartographiegestionprojet.dto.ProjetDTO;
import com.esmt.cartographiegestionprojet.entity.DomaineRecherche;
import com.esmt.cartographiegestionprojet.entity.Participation;
import com.esmt.cartographiegestionprojet.entity.Projet;
import com.esmt.cartographiegestionprojet.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.esmt.cartographiegestionprojet.utils.StatutProjet;

@Component
public class ProjetMapper {

    @Autowired  // ✅ Spring injection
    private UserMapper userMapper;

    /**
     * Convertir Projet → ProjetDTO
     */
    public ProjetDTO toDTO(Projet projet) {
        if (projet == null) {
            return null;
        }

        ProjetDTO dto = new ProjetDTO();
        dto.setProjectId(projet.getProjectId());
        dto.setTitreProjet(projet.getTitreProjet());
        dto.setDescription(projet.getDescription());
        dto.setDateDebut(projet.getDateDebut());
        dto.setDateFin(projet.getDateFin());
        dto.setStatutProjet(projet.getStatutProjet());
        dto.setBudgetEstime(projet.getBudgetEstime());
        dto.setInstitution(projet.getInstitution());
        dto.setNiveauAvancement(projet.getNiveauAvancement());

        // Domaine
        if (projet.getDomaineRecherche() != null) {
            dto.setDomaineId(projet.getDomaineRecherche().getId());
            dto.setDomaineNom(projet.getDomaineRecherche().getNomDomaine());
        }

        // Responsable
        if (projet.getResponsableProjet() != null) {
            dto.setResponsableId(projet.getResponsableProjet().getId());
            dto.setResponsableNom(userMapper.getFullName(projet.getResponsableProjet()));
        }

        // Nombre de participants actifs
        long nombreParticipants = projet.getParticipations().stream()
                .filter(Participation::getActif)
                .count();
        dto.setNombreParticipants((int) nombreParticipants);

        return dto;
    }

    /**
     * Convertir ProjetCreateDTO → Projet entity
     */
    public Projet toEntity(ProjetCreateDTO dto, DomaineRecherche domaine, User responsable) {
        if (dto == null) {
            return null;
        }

        Projet projet = new Projet();
        projet.setTitreProjet(dto.getTitreProjet());
        projet.setDescription(dto.getDescription());
        projet.setDateDebut(dto.getDateDebut());
        projet.setDateFin(dto.getDateFin());
        projet.setStatutProjet(StatutProjet.EN_COURS);
        projet.setBudgetEstime(dto.getBudgetEstime());
        projet.setInstitution(dto.getInstitution());
        projet.setDomaineRecherche(domaine);
        projet.setResponsableProjet(responsable);
        projet.setNiveauAvancement(0);

        return projet;
    }

    /**
     * Mettre à jour une entité Projet depuis un DTO
     */
    public void updateEntityFromDTO(Projet projet, ProjetCreateDTO dto) {
        if (projet == null || dto == null) {
            return;
        }

        projet.setTitreProjet(dto.getTitreProjet());
        projet.setDescription(dto.getDescription());
        projet.setDateDebut(dto.getDateDebut());
        projet.setDateFin(dto.getDateFin());
        projet.setBudgetEstime(dto.getBudgetEstime());
        projet.setInstitution(dto.getInstitution());
    }

    /**
     * Version simplifiée pour les listes
     */
    public ProjetDTO toSimpleDTO(Projet projet) {
        if (projet == null) {
            return null;
        }

        ProjetDTO dto = new ProjetDTO();
        dto.setProjectId(projet.getProjectId());
        dto.setTitreProjet(projet.getTitreProjet());
        dto.setStatutProjet(projet.getStatutProjet());
        dto.setNiveauAvancement(projet.getNiveauAvancement());

        if (projet.getDomaineRecherche() != null) {
            dto.setDomaineNom(projet.getDomaineRecherche().getNomDomaine());
        }

        return dto;
    }
}