package com.esmt.cartographiegestionprojet.entity;

import jakarta.persistence.*;
import com.esmt.cartographiegestionprojet.utils.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "date_creation", updatable = false)
    private LocalDate dateCreation;

    @Column(name = "date_modification")
    private LocalDate dateModification;

    @OneToMany(mappedBy = "responsableProjet")
    private List<Projet> projetsResponsable = new ArrayList<>();

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
    private List<Participation> participations = new ArrayList<>();

    private boolean actif;

    public User() {
    }

    public User(UUID id, String nom, String prenom, String email, String password, Role role, List<Projet> projetsResponsable,List<Participation> participations) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.dateCreation = LocalDate.now();
        this.dateModification = LocalDate.now();
        this.actif = true;
        this.projetsResponsable = projetsResponsable;
        this.participations = participations;
    }

    public User(UUID id, String nom, String prenom, String email, String password, Role role, LocalDate dateCreation, LocalDate dateModification, boolean actif, List<Projet> projetsResponsable, List<Participation> participations) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.actif = actif;
        this.projetsResponsable = projetsResponsable;
        this.participations = participations;
    }

    public User(String nom, String prenom, String email, String password, Role role, LocalDate dateCreation, LocalDate dateModification, List<Projet> projetsResponsable, List<Participation> participations, boolean actif) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.projetsResponsable = projetsResponsable;
        this.participations = participations;
        this.actif = actif;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public List<Projet> getProjetsResponsable() {
        return projetsResponsable;
    }

    public void setProjetsResponsable(List<Projet> projetsResponsable) {
        this.projetsResponsable = projetsResponsable;
    }

    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }
}