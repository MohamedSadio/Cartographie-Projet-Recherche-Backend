package com.esmt.cartographiegestionprojet.repository;

import com.esmt.cartographiegestionprojet.entity.DomaineRecherche;
import com.esmt.cartographiegestionprojet.entity.Projet;
import com.esmt.cartographiegestionprojet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.esmt.cartographiegestionprojet.utils.StatutProjet;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, UUID> {
    List<Projet> findByDomaineRecherche(DomaineRecherche domaine);

    List<Projet> findByStatutProjet(StatutProjet statut);

    List<Projet> findByParticipations_Participant(User user);

    //Méthodes pour statistiques

    /**
     * Compter le nombre de projets par domaine
     * Retourne: [["IA", 12], ["Santé", 8], ...]
     */
    @Query("SELECT d.nomDomaine, COUNT(p) FROM Projet p " +
            "JOIN p.domaineRecherche d " +
            "GROUP BY d.nomDomaine " +
            "ORDER BY COUNT(p) DESC")
    List<Object[]> countProjetsByDomaine();

    /**
     * Compter le nombre de projets par statut
     * Retourne: [["EN_COURS", 15], ["TERMINE", 8], ...]
     */
    @Query("SELECT p.statutProjet, COUNT(p) FROM Projet p " +
            "GROUP BY p.statutProjet")
    List<Object[]> countProjetsByStatut();

    /**
     * Évolution temporelle : nombre de projets par année
     * Retourne: [[2024, 10], [2025, 15], [2026, 20]]
     */
    @Query("SELECT YEAR(p.dateDebut), COUNT(p) FROM Projet p " +
            "GROUP BY YEAR(p.dateDebut) " +
            "ORDER BY YEAR(p.dateDebut)")
    List<Object[]> countProjetsByYear();

    /**
     * Budget total par domaine
     * Retourne: [["IA", 500000.0], ["Santé", 300000.0], ...]
     */
    @Query("SELECT d.nomDomaine, SUM(p.budgetEstime) FROM Projet p " +
            "JOIN p.domaineRecherche d " +
            "GROUP BY d.nomDomaine " +
            "ORDER BY SUM(p.budgetEstime) DESC")
    List<Object[]> sumBudgetByDomaine();

    /**
     * Taux moyen d'avancement de tous les projets
     */
    @Query("SELECT AVG(p.niveauAvancement) FROM Projet p")
    Double averageNiveauAvancement();

    /**
     * Compter le nombre total de projets
     */
    @Query("SELECT COUNT(p) FROM Projet p")
    Long countTotalProjets();
}
