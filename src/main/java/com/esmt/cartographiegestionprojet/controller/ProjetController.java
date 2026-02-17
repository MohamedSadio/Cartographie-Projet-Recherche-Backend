package com.esmt.cartographiegestionprojet.controller;

import com.esmt.cartographiegestionprojet.dto.ProjetCreateDTO;
import com.esmt.cartographiegestionprojet.dto.ProjetDTO;
import com.esmt.cartographiegestionprojet.dto.response.ApiResponse;
import com.esmt.cartographiegestionprojet.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.esmt.cartographiegestionprojet.service.ProjetService;
import com.esmt.cartographiegestionprojet.utils.StatutProjet;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projets")
@Tag(name = "Projets", description = "Gestion des projets de recherche")
@SecurityRequirement(name = "Bearer Authentication")
public class ProjetController {

    @Autowired
    private ProjetService projetService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    /**
     * GET /api/projets
     * Récupérer tous les projets (filtré selon le rôle de l'utilisateur)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjetDTO>>> getAllProjets(
            @RequestHeader("Authorization") String authHeader) {  // ✅ Récupérer le token

        UUID userId = extractUserIdFromToken(authHeader);  // ✅ Extraire userId du token
        List<ProjetDTO> projets = projetService.getAllProjets(userId);
        return ResponseEntity.ok(ApiResponse.success(projets));
    }

    /**
     * GET /api/projets/{id}
     * Récupérer un projet par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjetDTO>> getProjetById(
            @PathVariable UUID id,
            @RequestHeader("Authorization") String authHeader) {

        UUID userId = extractUserIdFromToken(authHeader);
        ProjetDTO projet = projetService.getProjetById(id, userId);
        return ResponseEntity.ok(ApiResponse.success(projet));
    }

    /**
     * GET /api/projets/mes-projets
     * Récupérer les projets de l'utilisateur connecté
     */
    @GetMapping("/mes-projets")
    public ResponseEntity<ApiResponse<List<ProjetDTO>>> getMesProjets(
            @RequestHeader("Authorization") String authHeader) {

        UUID userId = extractUserIdFromToken(authHeader);
        List<ProjetDTO> projets = projetService.getMesProjets(userId);
        return ResponseEntity.ok(ApiResponse.success(projets));
    }

    /**
     * GET /api/projets/domaine/{domaineId}
     * Récupérer les projets par domaine
     */
    @GetMapping("/domaine/{domaineId}")
    public ResponseEntity<ApiResponse<List<ProjetDTO>>> getProjetsByDomaine(
            @PathVariable UUID domaineId) {
        List<ProjetDTO> projets = projetService.getProjetsByDomaine(domaineId);
        return ResponseEntity.ok(ApiResponse.success(projets));
    }

    /**
     * GET /api/projets/statut/{statut}
     * Récupérer les projets par statut
     */
    @GetMapping("/statut/{statut}")
    public ResponseEntity<ApiResponse<List<ProjetDTO>>> getProjetsByStatut(
            @PathVariable StatutProjet statut) {
        List<ProjetDTO> projets = projetService.getProjetsByStatut(statut);
        return ResponseEntity.ok(ApiResponse.success(projets));
    }

    /**
     * POST /api/projets
     * Créer un nouveau projet
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProjetDTO>> createProjet(
            @RequestBody ProjetCreateDTO dto,
            @RequestHeader("Authorization") String authHeader) {

        UUID createurId = extractUserIdFromToken(authHeader);
        ProjetDTO createdProjet = projetService.createProjet(dto, createurId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Projet créé avec succès", createdProjet));
    }

    /**
     * PUT /api/projets/{id}
     * Mettre à jour un projet
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjetDTO>> updateProjet(
            @PathVariable UUID id,
            @RequestBody ProjetCreateDTO dto,
            @RequestHeader("Authorization") String authHeader) {

        UUID userId = extractUserIdFromToken(authHeader);
        ProjetDTO updatedProjet = projetService.updateProjet(id, dto, userId);
        return ResponseEntity.ok(ApiResponse.success("Projet mis à jour avec succès", updatedProjet));
    }

    /**
     * PUT /api/projets/{id}/statut
     * Mettre à jour le statut d'un projet
     */
    @PutMapping("/{id}/statut")
    public ResponseEntity<ApiResponse<ProjetDTO>> updateStatut(
            @PathVariable UUID id,
            @RequestParam StatutProjet statut,
            @RequestHeader("Authorization") String authHeader) {

        UUID userId = extractUserIdFromToken(authHeader);
        ProjetDTO updatedProjet = projetService.updateStatut(id, statut, userId);
        return ResponseEntity.ok(ApiResponse.success("Statut mis à jour avec succès", updatedProjet));
    }

    /**
     * PUT /api/projets/{id}/avancement
     * Mettre à jour le niveau d'avancement
     */
    @PutMapping("/{id}/avancement")
    public ResponseEntity<ApiResponse<ProjetDTO>> updateAvancement(
            @PathVariable UUID id,
            @RequestParam Integer avancement,
            @RequestHeader("Authorization") String authHeader) {

        UUID userId = extractUserIdFromToken(authHeader);
        ProjetDTO updatedProjet = projetService.updateAvancement(id, avancement, userId);
        return ResponseEntity.ok(ApiResponse.success("Avancement mis à jour avec succès", updatedProjet));
    }

    /**
     * DELETE /api/projets/{id}
     * Supprimer un projet (GESTIONNAIRE et ADMIN uniquement)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GESTIONNAIRE')")  // ✅ Vérification du rôle
    public ResponseEntity<ApiResponse<Void>> deleteProjet(
            @PathVariable UUID id,
            @RequestHeader("Authorization") String authHeader) {

        UUID userId = extractUserIdFromToken(authHeader);
        projetService.deleteProjet(id, userId);
        return ResponseEntity.ok(ApiResponse.success("Projet supprimé avec succès", null));
    }

    /**
     * 🔧 HELPER METHOD : Extraire l'userId du token JWT
     */
    private UUID extractUserIdFromToken(String authHeader) {
        String token = authHeader.substring(7); // Enlever "Bearer "
        return tokenProvider.getUserIdFromToken(token);
    }
}