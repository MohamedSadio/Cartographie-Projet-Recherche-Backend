package com.esmt.cartographiegestionprojet.service;

import com.esmt.cartographiegestionprojet.repository.ParticipationRepository;
import com.esmt.cartographiegestionprojet.repository.ProjetRepository;
import com.esmt.cartographiegestionprojet.utils.StatutProjet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatistiqueService {

    @Autowired
    private ProjetRepository projetRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    /**
     * STATISTIQUE 1 : Nombre total de projets
     */
    public Long getNombreTotalProjets() {
        return projetRepository.countTotalProjets();
    }

    /**
     * GRAPHIQUE 1 : Nombre de projets par domaine
     * Retourne: {"Intelligence Artificielle": 12, "Santé": 8, ...}
     */
    public Map<String, Long> getNombreProjetsByDomaine() {
        List<Object[]> results = projetRepository.countProjetsByDomaine();
        Map<String, Long> stats = new LinkedHashMap<>();

        for (Object[] result : results) {
            String domaine = (String) result[0];
            Long count = (Long) result[1];
            stats.put(domaine, count);
        }

        return stats;
    }

    /**
     * GRAPHIQUE 2 : Répartition par statut
     * Retourne: {"EN_COURS": 15, "TERMINE": 8, "SUSPENDU": 2}
     */
    public Map<StatutProjet, Long> getRepartitionParStatut() {
        List<Object[]> results = projetRepository.countProjetsByStatut();
        Map<StatutProjet, Long> stats = new LinkedHashMap<>();

        for (Object[] result : results) {
            StatutProjet statut = (StatutProjet) result[0];
            Long count = (Long) result[1];
            stats.put(statut, count);
        }

        return stats;
    }

    /**
     * GRAPHIQUE 3 : Évolution temporelle des projets
     * Retourne: {2024: 10, 2025: 15, 2026: 20}
     */
    public Map<Integer, Long> getEvolutionTemporelleProjets() {
        List<Object[]> results = projetRepository.countProjetsByYear();
        Map<Integer, Long> stats = new LinkedHashMap<>();

        for (Object[] result : results) {
            Integer annee = (Integer) result[0];
            Long count = (Long) result[1];
            stats.put(annee, count);
        }

        return stats;
    }

    /**
     * GRAPHIQUE 4 : Charge des participants
     * Nombre de projets par participant
     * Retourne: {"Amadou Diallo": 5, "Fatou Sow": 3, ...}
     */
    public Map<String, Long> getNombreProjetsByParticipant() {
        List<Object[]> results = participationRepository.countProjetsParParticipant();
        Map<String, Long> stats = new LinkedHashMap<>();

        for (Object[] result : results) {
            String participant = (String) result[0];
            Long count = (Long) result[1];
            stats.put(participant, count);
        }

        return stats;
    }

    /**
     * STATISTIQUE 5 : Budget total par domaine
     * Retourne: {"Intelligence Artificielle": 500000.0, "Santé": 300000.0, ...}
     */
    public Map<String, Double> getBudgetTotalByDomaine() {
        List<Object[]> results = projetRepository.sumBudgetByDomaine();
        Map<String, Double> stats = new LinkedHashMap<>();

        for (Object[] result : results) {
            String domaine = (String) result[0];
            Double budget = (Double) result[1];
            stats.put(domaine, budget != null ? budget : 0.0);
        }

        return stats;
    }

    /**
     * STATISTIQUE 6 : Taux moyen d'avancement
     * Retourne: 65.5 (en pourcentage)
     */
    public Double getTauxMoyenAvancement() {
        Double average = projetRepository.averageNiveauAvancement();
        return average != null ? average : 0.0;
    }

    /**
     * DASHBOARD COMPLET : Toutes les statistiques en une seule requête
     * Pour optimiser les appels depuis le frontend
     */
    public Map<String, Object> getDashboardComplet() {
        Map<String, Object> dashboard = new HashMap<>();

        dashboard.put("totalProjets", getNombreTotalProjets());
        dashboard.put("projetsParDomaine", getNombreProjetsByDomaine());
        dashboard.put("projetsParStatut", getRepartitionParStatut());
        dashboard.put("evolutionTemporelle", getEvolutionTemporelleProjets());
        dashboard.put("projetsParParticipant", getNombreProjetsByParticipant());
        dashboard.put("budgetParDomaine", getBudgetTotalByDomaine());
        dashboard.put("tauxMoyenAvancement", getTauxMoyenAvancement());

        return dashboard;
    }
}