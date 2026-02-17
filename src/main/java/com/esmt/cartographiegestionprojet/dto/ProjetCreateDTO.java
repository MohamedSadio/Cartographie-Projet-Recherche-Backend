package com.esmt.cartographiegestionprojet.dto;

import java.time.LocalDate;
import java.util.UUID;

public class ProjetCreateDTO {
    private String titreProjet;
    private UUID domaineId;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private double budgetEstime;
    private String institution;

    // Constructeurs
    public ProjetCreateDTO() {}

    // Getters et Setters
    public String getTitreProjet() { return titreProjet; }
    public void setTitreProjet(String titreProjet) { this.titreProjet = titreProjet; }
    public UUID getDomaineId() { return domaineId; }
    public void setDomaineId(UUID domaineId) { this.domaineId = domaineId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public double getBudgetEstime() { return budgetEstime; }
    public void setBudgetEstime(double budgetEstime) { this.budgetEstime = budgetEstime; }
    public String getInstitution() { return institution; }
    public void setInstitution(String institution) { this.institution = institution; }
}