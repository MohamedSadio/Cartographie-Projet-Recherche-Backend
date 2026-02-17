package com.esmt.cartographiegestionprojet.dto;

import java.util.UUID;

public class AddParticipantRequest {
    private UUID projetId;
    private UUID participantId;
    private String roleParticipant;

    // Constructeurs
    public AddParticipantRequest() {}

    public AddParticipantRequest(UUID projetId, UUID participantId, String roleParticipant) {
        this.projetId = projetId;
        this.participantId = participantId;
        this.roleParticipant = roleParticipant;
    }

    // Getters et Setters
    public UUID getProjetId() {
        return projetId;
    }

    public void setProjetId(UUID projetId) {
        this.projetId = projetId;
    }

    public UUID getParticipantId() {
        return participantId;
    }

    public void setParticipantId(UUID participantId) {
        this.participantId = participantId;
    }

    public String getRoleParticipant() {
        return roleParticipant;
    }

    public void setRoleParticipant(String roleParticipant) {
        this.roleParticipant = roleParticipant;
    }
}