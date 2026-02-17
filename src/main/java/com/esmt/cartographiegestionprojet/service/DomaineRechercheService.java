package com.esmt.cartographiegestionprojet.service;

import com.esmt.cartographiegestionprojet.dto.DomaineRechercheDTO;
import com.esmt.cartographiegestionprojet.entity.DomaineRecherche;
import com.esmt.cartographiegestionprojet.exception.BusinessException;
import com.esmt.cartographiegestionprojet.exception.NotFoundException;
import com.esmt.cartographiegestionprojet.mapper.DomaineRechercheMapper;
import com.esmt.cartographiegestionprojet.repository.DomaineRechercheRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DomaineRechercheService {

    @Autowired
    private DomaineRechercheRepository domaineRepository;

    @Autowired
    private DomaineRechercheMapper domaineMapper;

    /**
     * Créer un nouveau domaine de recherche
     */
    @Transactional
    public DomaineRechercheDTO createDomaine(DomaineRechercheDTO dto) {
        // Validation : Nom unique
        if (domaineRepository.existsByNomDomaine(dto.getNomDomaine())) {
            throw new BusinessException("Un domaine avec ce nom existe déjà");
        }

        if (dto.getNomDomaine() == null || dto.getNomDomaine().trim().isEmpty()) {
            throw new BusinessException("Le nom du domaine est obligatoire");
        }

        DomaineRecherche domaine = domaineMapper.toEntity(dto);
        DomaineRecherche savedDomaine = domaineRepository.save(domaine);

        return domaineMapper.toDTO(savedDomaine);
    }

    /**
     * Récupérer un domaine par ID
     */
    public DomaineRechercheDTO getDomaineById(UUID id) {
        DomaineRecherche domaine = domaineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Domaine de recherche non trouvé avec l'ID : " + id));
        return domaineMapper.toDTO(domaine);
    }

    /**
     * Récupérer un domaine par nom
     */
    public DomaineRechercheDTO getDomaineByNom(String nomDomaine) {
        DomaineRecherche domaine = domaineRepository.findByNomDomaine(nomDomaine)
                .orElseThrow(() -> new NotFoundException("Domaine de recherche non trouvé : " + nomDomaine));
        return domaineMapper.toDTO(domaine);
    }

    /**
     * Récupérer tous les domaines
     */
    public List<DomaineRechercheDTO> getAllDomaines() {
        return domaineRepository.findAll().stream()
                .map(domaineMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Mettre à jour un domaine
     */
    @Transactional
    public DomaineRechercheDTO updateDomaine(UUID id, DomaineRechercheDTO dto) {
        DomaineRecherche domaine = domaineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Domaine non trouvé"));

        // Validation : Nom unique (sauf si c'est le même)
        if (!domaine.getNomDomaine().equals(dto.getNomDomaine()) &&
                domaineRepository.existsByNomDomaine(dto.getNomDomaine())) {
            throw new BusinessException("Un domaine avec ce nom existe déjà");
        }

        domaineMapper.updateEntityFromDTO(domaine, dto);
        DomaineRecherche updatedDomaine = domaineRepository.save(domaine);

        return domaineMapper.toDTO(updatedDomaine);
    }

    /**
     * Supprimer un domaine
     */
    @Transactional
    public void deleteDomaine(UUID id) {
        DomaineRecherche domaine = domaineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Domaine non trouvé"));

        // Validation : Vérifier qu'aucun projet n'utilise ce domaine
        if (!domaine.getProjets().isEmpty()) {
            throw new BusinessException("Impossible de supprimer ce domaine car " +
                    domaine.getProjets().size() + " projet(s) l'utilisent");
        }

        domaineRepository.deleteById(id);
    }

    /**
     * Recherche ou création d'un domaine (utile pour l'import CSV)
     */
    @Transactional
    public DomaineRecherche findOrCreateDomaine(String nomDomaine) {
        return domaineRepository.findByNomDomaine(nomDomaine)
                .orElseGet(() -> {
                    DomaineRecherche nouveauDomaine = new DomaineRecherche();
                    nouveauDomaine.setNomDomaine(nomDomaine);
                    nouveauDomaine.setDescription("Créé automatiquement depuis l'import");
                    return domaineRepository.save(nouveauDomaine);
                });
    }

    /**
     * Récupérer l'entité DomaineRecherche (pour usage interne)
     */
    public DomaineRecherche getDomaineEntityById(UUID id) {
        return domaineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Domaine non trouvé"));
    }
}