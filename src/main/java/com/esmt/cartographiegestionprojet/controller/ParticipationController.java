package com.esmt.cartographiegestionprojet.controller;

import com.esmt.cartographiegestionprojet.dto.AddParticipantRequest;
import com.esmt.cartographiegestionprojet.dto.ParticipationDTO;
import com.esmt.cartographiegestionprojet.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.esmt.cartographiegestionprojet.service.ParticipationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/participations")
@CrossOrigin(origins = "*")
public class ParticipationController {

    @Autowired
    private ParticipationService participationService;

    /**
     * GET /api/participations/projet/{projetId}
     * Récupérer les participants actifs d'un projet
     */
    @GetMapping("/projet/{projetId}")
    public ResponseEntity<ApiResponse<List<ParticipationDTO>>> getParticipantsActifs(
            @PathVariable UUID projetId) {
        List<ParticipationDTO> participants = participationService.getParticipantsActifs(projetId);
        return ResponseEntity.ok(ApiResponse.success(participants));
    }

    /**
     * GET /api/participations/user/{userId}
     * Récupérer toutes les participations d'un utilisateur
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<ParticipationDTO>>> getParticipationsByUser(
            @PathVariable UUID userId) {
        List<ParticipationDTO> participations = participationService.getParticipationsByUser(userId);
        return ResponseEntity.ok(ApiResponse.success(participations));
    }

    /**
     * GET /api/participations/user/{userId}/actives
     * Récupérer les participations actives d'un utilisateur
     */
    @GetMapping("/user/{userId}/actives")
    public ResponseEntity<ApiResponse<List<ParticipationDTO>>> getParticipationsActivesUser(
            @PathVariable UUID userId) {
        List<ParticipationDTO> participations = participationService.getParticipationsActivesUser(userId);
        return ResponseEntity.ok(ApiResponse.success(participations));
    }

    /**
     * POST /api/participations
     * Ajouter un participant à un projet
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ParticipationDTO>> addParticipant(
            @RequestBody AddParticipantRequest request) {

        ParticipationDTO participation = participationService.addParticipant(
                request.getProjetId(),
                request.getParticipantId(),
                request.getRoleParticipant()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Participant ajouté avec succès", participation));
    }

    /**
     * DELETE /api/participations/{projetId}/participants/{participantId}
     * Retirer un participant d'un projet
     */
    @DeleteMapping("/{projetId}/participants/{participantId}")  // ✅ Mieux structuré
    public ResponseEntity<ApiResponse<Void>> removeParticipant(
            @PathVariable UUID projetId,
            @PathVariable UUID participantId) {

        participationService.removeParticipant(projetId, participantId);
        return ResponseEntity.ok(ApiResponse.success("Participant retiré avec succès", null));
    }
}