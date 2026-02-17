package com.esmt.cartographiegestionprojet.dto;

import java.time.LocalDate;
import java.util.UUID;

public class ParticipationDTO {
    private UUID id;
    private UUID participantId;
    private String participantNom;
    private String participantEmail;
    private UUID projetId;
    private String projetTitre;
    private LocalDate dateParticipation;
    private LocalDate dateFin;
    private String roleParticipant;
    private Boolean actif;

    // Constructeurs
    public ParticipationDTO() {}

    // Getters et Setters (tous les champs)
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getParticipantId() { return participantId; }
    public void setParticipantId(UUID participantId) { this.participantId = participantId; }
    public String getParticipantNom() { return participantNom; }
    public void setParticipantNom(String participantNom) { this.participantNom = participantNom; }
    public String getParticipantEmail() { return participantEmail; }
    public void setParticipantEmail(String participantEmail) { this.participantEmail = participantEmail; }
    public UUID getProjetId() { return projetId; }
    public void setProjetId(UUID projetId) { this.projetId = projetId; }
    public String getProjetTitre() { return projetTitre; }
    public void setProjetTitre(String projetTitre) { this.projetTitre = projetTitre; }
    public LocalDate getDateParticipation() { return dateParticipation; }
    public void setDateParticipation(LocalDate dateParticipation) { this.dateParticipation = dateParticipation; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public String getRoleParticipant() { return roleParticipant; }
    public void setRoleParticipant(String roleParticipant) { this.roleParticipant = roleParticipant; }
    public Boolean getActif() { return actif; }
    public void setActif(Boolean actif) { this.actif = actif; }
}