package com.esmt.cartographiegestionprojet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import com.esmt.cartographiegestionprojet.utils.StatutProjet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "projets")
public class Projet {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID projectId ;

    @Column(name = "titre_projet", nullable = false)
    private String titreProjet;

    @ManyToOne(fetch = FetchType.LAZY)
    private DomaineRecherche domaineRecherche;

    @Column(nullable = false)
    private String description;

    @Column(name = "date_debut",nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin",nullable = false)
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_projet",nullable = false)
    private StatutProjet  statutProjet;

    @Column(name ="budget_estime",nullable = false)
    private double budgetEstime;

    @Column(nullable = false)
    private String institution;

    @ManyToOne(fetch = FetchType.LAZY)
    private User responsableProjet;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participation> participations = new ArrayList<>();


    @Column(name = "niveau_avancement")
    @Min(0)
    @Max(100)
    private Integer niveauAvancement;

    public Projet() {
    }

    public Projet(UUID projectId, String titreProjet, DomaineRecherche domaineRecherche, String description, LocalDate dateDebut, LocalDate dateFin, StatutProjet statutProjet, double budgetEstime, String institution, User responsableProjet, List<Participation> participations, Integer niveauAvancement) {
        this.projectId = projectId;
        this.titreProjet = titreProjet;
        this.domaineRecherche = domaineRecherche;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statutProjet = statutProjet;
        this.budgetEstime = budgetEstime;
        this.institution = institution;
        this.responsableProjet = responsableProjet;
        this.participations = participations;
        this.niveauAvancement = niveauAvancement;
    }

    public Projet(String titreProjet, DomaineRecherche domaineRecherche, String description, LocalDate dateDebut, LocalDate dateFin, StatutProjet statutProjet, double budgetEstime, String institution, User responsableProjet, List<Participation> participations, Integer niveauAvancement) {
        this.titreProjet = titreProjet;
        this.domaineRecherche = domaineRecherche;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statutProjet = statutProjet;
        this.budgetEstime = budgetEstime;
        this.institution = institution;
        this.responsableProjet = responsableProjet;
        this.participations = participations;
        this.niveauAvancement = niveauAvancement;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public String getTitreProjet() {
        return titreProjet;
    }

    public void setTitreProjet(String titreProjet) {
        this.titreProjet = titreProjet;
    }

    public DomaineRecherche getDomaineRecherche() {
        return domaineRecherche;
    }

    public void setDomaineRecherche(DomaineRecherche domaineRecherche) {
        this.domaineRecherche = domaineRecherche;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public StatutProjet getStatutProjet() {
        return statutProjet;
    }

    public void setStatutProjet(StatutProjet statutProjet) {
        this.statutProjet = statutProjet;
    }

    public double getBudgetEstime() {
        return budgetEstime;
    }

    public void setBudgetEstime(double budgetEstime) {
        this.budgetEstime = budgetEstime;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public User getResponsableProjet() {
        return responsableProjet;
    }

    public void setResponsableProjet(User responsableProjet) {
        this.responsableProjet = responsableProjet;
    }

    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }

    public Integer getNiveauAvancement() {
        return niveauAvancement;
    }

    public void setNiveauAvancement(Integer niveauAvancement) {
        this.niveauAvancement = niveauAvancement;
    }
}
