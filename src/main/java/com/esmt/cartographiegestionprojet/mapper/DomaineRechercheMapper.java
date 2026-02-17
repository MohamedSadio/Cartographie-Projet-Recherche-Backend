package com.esmt.cartographiegestionprojet.mapper;

import com.esmt.cartographiegestionprojet.dto.DomaineRechercheDTO;
import com.esmt.cartographiegestionprojet.entity.DomaineRecherche;
import org.springframework.stereotype.Component;

@Component
public class DomaineRechercheMapper {

    /**
     * Convertir DomaineRecherche → DomaineRechercheDTO
     */
    public DomaineRechercheDTO toDTO(DomaineRecherche domaine) {
        if (domaine == null) {
            return null;
        }

        DomaineRechercheDTO dto = new DomaineRechercheDTO();
        dto.setId(domaine.getId());
        dto.setNomDomaine(domaine.getNomDomaine());
        dto.setDescription(domaine.getDescription());
        dto.setCouleur(domaine.getCouleur());
        dto.setNombreProjets(domaine.getProjets() != null ? domaine.getProjets().size() : 0);

        return dto;
    }

    /**
     * Convertir DTO → Entity
     */
    public DomaineRecherche toEntity(DomaineRechercheDTO dto) {
        if (dto == null) {
            return null;
        }

        DomaineRecherche domaine = new DomaineRecherche();
        domaine.setNomDomaine(dto.getNomDomaine());
        domaine.setDescription(dto.getDescription());
        domaine.setCouleur(dto.getCouleur());

        return domaine;
    }

    /**
     * Mettre à jour une entité depuis un DTO
     */
    public void updateEntityFromDTO(DomaineRecherche domaine, DomaineRechercheDTO dto) {
        if (domaine == null || dto == null) {
            return;
        }

        domaine.setNomDomaine(dto.getNomDomaine());
        domaine.setDescription(dto.getDescription());
        domaine.setCouleur(dto.getCouleur());
    }
}