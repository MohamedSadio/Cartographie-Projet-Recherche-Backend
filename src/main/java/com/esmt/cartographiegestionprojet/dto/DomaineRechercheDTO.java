package com.esmt.cartographiegestionprojet.dto;

import java.util.UUID;

public class DomaineRechercheDTO {
    private UUID id;
    private String nomDomaine;
    private String description;
    private String couleur;
    private int nombreProjets;

    // Constructeurs
    public DomaineRechercheDTO() {}

    // Getters et Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNomDomaine() { return nomDomaine; }
    public void setNomDomaine(String nomDomaine) { this.nomDomaine = nomDomaine; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCouleur() { return couleur; }
    public void setCouleur(String couleur) { this.couleur = couleur; }
    public int getNombreProjets() { return nombreProjets; }
    public void setNombreProjets(int nombreProjets) { this.nombreProjets = nombreProjets; }
}