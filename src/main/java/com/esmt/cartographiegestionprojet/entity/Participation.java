package com.esmt.cartographiegestionprojet.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "participations",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"projet_id", "participant_id"}
        )
)
public class Participation {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID id ;

    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private User participant;

    @Column(name = "date_participation", nullable = false)
    private LocalDate dateParticipation;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "role_participant")
    private String roleParticipant;

    @Column(name = "actif")
    private Boolean actif = true;

    public Participation() {
    }

    public Participation(UUID id, Projet projet, User participant, LocalDate dateParticipation, LocalDate dateFin, String roleParticipant, Boolean actif) {
        this.id = id;
        this.projet = projet;
        this.participant = participant;
        this.dateParticipation = dateParticipation;
        this.dateFin = dateFin;
        this.roleParticipant = roleParticipant;
        this.actif = actif;
    }

    public Participation(Projet projet, User participant, LocalDate dateParticipation, LocalDate dateFin, String roleParticipant, Boolean actif) {
        this.projet = projet;
        this.participant = participant;
        this.dateParticipation = dateParticipation;
        this.dateFin = dateFin;
        this.roleParticipant = roleParticipant;
        this.actif = actif;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public User getParticipant() {
        return participant;
    }

    public void setParticipant(User participant) {
        this.participant = participant;
    }

    public LocalDate getDateParticipation() {
        return dateParticipation;
    }

    public void setDateParticipation(LocalDate dateParticipation) {
        this.dateParticipation = dateParticipation;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getRoleParticipant() {
        return roleParticipant;
    }

    public void setRoleParticipant(String roleParticipant) {
        this.roleParticipant = roleParticipant;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }
}
