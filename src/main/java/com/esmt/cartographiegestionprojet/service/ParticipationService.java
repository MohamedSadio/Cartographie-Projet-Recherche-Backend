package com.esmt.cartographiegestionprojet.service;

import com.esmt.cartographiegestionprojet.dto.ParticipationDTO;
import com.esmt.cartographiegestionprojet.entity.Participation;
import com.esmt.cartographiegestionprojet.entity.Projet;
import com.esmt.cartographiegestionprojet.entity.User;
import com.esmt.cartographiegestionprojet.exception.BusinessException;
import com.esmt.cartographiegestionprojet.exception.NotFoundException;
import com.esmt.cartographiegestionprojet.mapper.ParticipationMapper;
import com.esmt.cartographiegestionprojet.repository.ParticipationRepository;
import com.esmt.cartographiegestionprojet.repository.ProjetRepository;
import com.esmt.cartographiegestionprojet.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParticipationService {

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipationMapper participationMapper;

    /**
     * Ajouter un participant à un projet
     */
    @Transactional
    public ParticipationDTO addParticipant(UUID projetId, UUID participantId, String roleParticipant) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new NotFoundException("Projet non trouvé"));

        User participant = userRepository.findById(participantId)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé"));

        // Vérifier si la participation existe déjà
        if (participationRepository.existsByProjetAndParticipant(projet, participant)) {
            throw new BusinessException("Cet utilisateur participe déjà à ce projet");
        }

        Participation participation = participationMapper.toEntity(projet, participant, roleParticipant);
        Participation savedParticipation = participationRepository.save(participation);

        return participationMapper.toDTO(savedParticipation);
    }

    /**
     * Retirer un participant d'un projet (soft delete)
     */
    @Transactional
    public void removeParticipant(UUID projetId, UUID participantId) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new NotFoundException("Projet non trouvé"));

        User participant = userRepository.findById(participantId)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé"));

        Participation participation = participationRepository.findByProjetAndParticipant(projet, participant)
                .orElseThrow(() -> new NotFoundException("Participation non trouvée"));

        if (projet.getResponsableProjet().getId().equals(participantId)) {
            throw new BusinessException("Le responsable du projet ne peut pas être retiré des participants");
        }

        participation.setActif(false);
        participation.setDateFin(LocalDate.now());
        participationRepository.save(participation);
    }

    /**
     * Récupérer les participants actifs d'un projet
     */
    public List<ParticipationDTO> getParticipantsActifs(UUID projetId) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new NotFoundException("Projet non trouvé"));

        return participationRepository.findByProjetAndActifTrue(projet).stream()
                .map(participationMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer toutes les participations d'un utilisateur
     */
    public List<ParticipationDTO> getParticipationsByUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé"));

        return participationRepository.findByParticipant(user).stream()
                .map(participationMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer les participations actives d'un utilisateur
     */
    public List<ParticipationDTO> getParticipationsActivesUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé"));

        return participationRepository.findByParticipantAndActifTrue(user).stream()
                .map(participationMapper::toDTO)
                .collect(Collectors.toList());
    }
}