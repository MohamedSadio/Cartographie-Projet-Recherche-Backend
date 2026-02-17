package com.esmt.cartographiegestionprojet.mapper;

import com.esmt.cartographiegestionprojet.dto.ParticipationDTO;
import com.esmt.cartographiegestionprojet.entity.Participation;
import com.esmt.cartographiegestionprojet.entity.Projet;
import com.esmt.cartographiegestionprojet.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ParticipationMapper {

    @Autowired
    private UserMapper userMapper;

    /**
     * Convertir Participation → ParticipationDTO
     */
    public ParticipationDTO toDTO(Participation participation) {
        if (participation == null) {
            return null;
        }

        ParticipationDTO dto = new ParticipationDTO();
        dto.setId(participation.getId());
        dto.setDateParticipation(participation.getDateParticipation());
        dto.setDateFin(participation.getDateFin());
        dto.setRoleParticipant(participation.getRoleParticipant());
        dto.setActif(participation.getActif());

        // Informations du participant
        if (participation.getParticipant() != null) {
            dto.setParticipantId(participation.getParticipant().getId());
            dto.setParticipantNom(userMapper.getFullName(participation.getParticipant()));
            dto.setParticipantEmail(participation.getParticipant().getEmail());
        }

        // Informations du projet
        if (participation.getProjet() != null) {
            dto.setProjetId(participation.getProjet().getProjectId());
            dto.setProjetTitre(participation.getProjet().getTitreProjet());
        }

        return dto;
    }

    /**
     * Créer une Participation entity
     */
    public Participation toEntity(Projet projet, User participant, String roleParticipant) {
        if (projet == null || participant == null) {
            return null;
        }

        Participation participation = new Participation();
        participation.setProjet(projet);
        participation.setParticipant(participant);
        participation.setDateParticipation(LocalDate.now());
        participation.setRoleParticipant(roleParticipant != null ? roleParticipant : "Participant");
        participation.setActif(true);

        return participation;
    }
}