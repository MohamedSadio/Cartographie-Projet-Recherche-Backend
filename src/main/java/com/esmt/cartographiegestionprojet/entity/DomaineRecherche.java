package com.esmt.cartographiegestionprojet.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "domaine_recherches")
public class DomaineRecherche {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID id ;

    @Column(name = "nom_domaine",nullable = false, unique = true)
    private String nomDomaine;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "domaineRecherche")
    private List<Projet> projets = new ArrayList<>();

    @Column(name = "couleur")
    private String couleur;

    public DomaineRecherche() {
    }

    public DomaineRecherche(UUID id, String nomDomaine, String description,String couleur, List<Projet> projets) {
        this.id = id;
        this.nomDomaine = nomDomaine;
        this.description = description;
        this.projets = projets;
        this.couleur = couleur;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomDomaine() {
        return nomDomaine;
    }

    public void setNomDomaine(String nomDomaine) {
        this.nomDomaine = nomDomaine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Projet> getProjets() {
        return projets;
    }

    public void setProjets(List<Projet> projets) {
        this.projets = projets;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
}
