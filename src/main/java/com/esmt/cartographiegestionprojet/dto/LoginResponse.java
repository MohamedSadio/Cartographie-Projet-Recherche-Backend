package com.esmt.cartographiegestionprojet.dto;

import com.esmt.cartographiegestionprojet.utils.Role;
import java.util.UUID;

public class LoginResponse {
    private String token;           // Le TOKEN JWT
    private String tokenType = "Bearer";  // Type de token (toujours "Bearer" pour JWT)
    private UUID userId;            // ID de l'utilisateur
    private String email;           // Email
    private String nom;             // Nom
    private String prenom;          // Prénom
    private Role role;              // Rôle (CANDIDAT, GESTIONNAIRE, ADMIN)

    // Constructeurs
    public LoginResponse() {}

    public LoginResponse(String token, UUID userId, String email, String nom, String prenom, Role role) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
    }

    // Getters et Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}