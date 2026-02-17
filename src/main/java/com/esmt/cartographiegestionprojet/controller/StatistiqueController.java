package com.esmt.cartographiegestionprojet.controller;

import com.esmt.cartographiegestionprojet.dto.response.ApiResponse;
import com.esmt.cartographiegestionprojet.service.StatistiqueService;
import com.esmt.cartographiegestionprojet.utils.StatutProjet;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/statistiques")
@Tag(name = "Statistiques", description = "Tableaux de bord et statistiques (GESTIONNAIRE et ADMIN uniquement)")
@SecurityRequirement(name = "Bearer Authentication")

public class StatistiqueController {

    @Autowired
    private StatistiqueService statistiqueService;

    /**
     * GET /api/statistiques/total-projets
     * Nombre total de projets
     */
    @GetMapping("/total-projets")
    public ResponseEntity<ApiResponse<Long>> getNombreTotalProjets() {
        Long total = statistiqueService.getNombreTotalProjets();
        return ResponseEntity.ok(ApiResponse.success(total));
    }

    /**
     * GET /api/statistiques/projets-par-domaine
     * Graphique 1 : Projets par domaine
     */
    @GetMapping("/projets-par-domaine")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getProjetsParDomaine() {
        Map<String, Long> stats = statistiqueService.getNombreProjetsByDomaine();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    /**
     * GET /api/statistiques/projets-par-statut
     * Graphique 2 : Projets par statut
     */
    @GetMapping("/projets-par-statut")
    public ResponseEntity<ApiResponse<Map<StatutProjet, Long>>> getProjetsParStatut() {
        Map<StatutProjet, Long> stats = statistiqueService.getRepartitionParStatut();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    /**
     * GET /api/statistiques/evolution-temporelle
     * Graphique 3 : Évolution temporelle des projets
     */
    @GetMapping("/evolution-temporelle")
    public ResponseEntity<ApiResponse<Map<Integer, Long>>> getEvolutionTemporelle() {
        Map<Integer, Long> stats = statistiqueService.getEvolutionTemporelleProjets();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    /**
     * GET /api/statistiques/projets-par-participant
     * Graphique 4 : Charge des participants
     */
    @GetMapping("/projets-par-participant")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getProjetsParParticipant() {
        Map<String, Long> stats = statistiqueService.getNombreProjetsByParticipant();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    /**
     * GET /api/statistiques/budget-par-domaine
     * Statistique 5 : Budget total par domaine
     */
    @GetMapping("/budget-par-domaine")
    public ResponseEntity<ApiResponse<Map<String, Double>>> getBudgetParDomaine() {
        Map<String, Double> stats = statistiqueService.getBudgetTotalByDomaine();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    /**
     * GET /api/statistiques/taux-moyen-avancement
     * Statistique 6 : Taux moyen d'avancement
     */
    @GetMapping("/taux-moyen-avancement")
    public ResponseEntity<ApiResponse<Double>> getTauxMoyenAvancement() {
        Double taux = statistiqueService.getTauxMoyenAvancement();
        return ResponseEntity.ok(ApiResponse.success(taux));
    }

    /**
     * GET /api/statistiques/dashboard
     * Dashboard complet : Toutes les statistiques en une seule requête
     */
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboard() {
        Map<String, Object> dashboard = statistiqueService.getDashboardComplet();
        return ResponseEntity.ok(ApiResponse.success(dashboard));
    }
}