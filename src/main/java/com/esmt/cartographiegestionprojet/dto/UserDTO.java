package com.esmt.cartographiegestionprojet.dto;

import com.esmt.cartographiegestionprojet.utils.Role;
import java.time.LocalDate;
import java.util.UUID;

public class UserDTO {
    private UUID id;
    private String nom;
    private String prenom;
    private String email;
    private Role role;
    private LocalDate dateCreation;
    private boolean actif;

    // Constructeurs
    public UserDTO() {}

    public UserDTO(UUID id, String nom, String prenom, String email, Role role, LocalDate dateCreation, boolean actif) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.role = role;
        this.dateCreation = dateCreation;
        this.actif = actif;
    }

    // Getters et Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }
    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }
}