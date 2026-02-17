package com.esmt.cartographiegestionprojet.repository;

import com.esmt.cartographiegestionprojet.entity.Participation;
import com.esmt.cartographiegestionprojet.entity.Projet;
import com.esmt.cartographiegestionprojet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, UUID> {

    boolean existsByProjetAndParticipant(Projet projet, User participant);

    Optional<Participation> findByProjetAndParticipant(Projet projet, User participant);

    List<Participation> findByProjetAndActifTrue(Projet projet);

    List<Participation> findByParticipant(User participant);

    List<Participation> findByParticipantAndActifTrue(User participant);

    List<Participation> findByProjet(Projet projet);

    long countByParticipantAndActifTrue(User participant);

    long countByProjetAndActifTrue(Projet projet);

    //Méthode pour  statistique

    /**
     * Graphique 4 : Charge des participants
     * Nombre de projets actifs par participant
     * Retourne: [["Amadou Diallo", 5], ["Fatou Sow", 3], ...]
     */
    @Query("SELECT CONCAT(u.prenom, ' ', u.nom), COUNT(p) FROM Participation p " +
            "JOIN p.participant u " +
            "WHERE p.actif = true " +
            "GROUP BY u.id, u.prenom, u.nom " +
            "ORDER BY COUNT(p) DESC")
    List<Object[]> countProjetsParParticipant();
}