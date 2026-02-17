package com.esmt.cartographiegestionprojet.dto;

import com.esmt.cartographiegestionprojet.utils.StatutProjet;
import java.time.LocalDate;
import java.util.UUID;

public class ProjetDTO {
    private UUID projectId;
    private String titreProjet;
    private String domaineNom;
    private UUID domaineId;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutProjet statutProjet;
    private double budgetEstime;
    private String institution;
    private String responsableNom;
    private UUID responsableId;
    private Integer niveauAvancement;
    private int nombreParticipants;

    // Constructeurs
    public ProjetDTO() {}

    // Getters et Setters (tous les champs)
    public UUID getProjectId() { return projectId; }
    public void setProjectId(UUID projectId) { this.projectId = projectId; }
    public String getTitreProjet() { return titreProjet; }
    public void setTitreProjet(String titreProjet) { this.titreProjet = titreProjet; }
    public String getDomaineNom() { return domaineNom; }
    public void setDomaineNom(String domaineNom) { this.domaineNom = domaineNom; }
    public UUID getDomaineId() { return domaineId; }
    public void setDomaineId(UUID domaineId) { this.domaineId = domaineId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public StatutProjet getStatutProjet() { return statutProjet; }
    public void setStatutProjet(StatutProjet statutProjet) { this.statutProjet = statutProjet; }
    public double getBudgetEstime() { return budgetEstime; }
    public void setBudgetEstime(double budgetEstime) { this.budgetEstime = budgetEstime; }
    public String getInstitution() { return institution; }
    public void setInstitution(String institution) { this.institution = institution; }
    public String getResponsableNom() { return responsableNom; }
    public void setResponsableNom(String responsableNom) { this.responsableNom = responsableNom; }
    public UUID getResponsableId() { return responsableId; }
    public void setResponsableId(UUID responsableId) { this.responsableId = responsableId; }
    public Integer getNiveauAvancement() { return niveauAvancement; }
    public void setNiveauAvancement(Integer niveauAvancement) { this.niveauAvancement = niveauAvancement; }
    public int getNombreParticipants() { return nombreParticipants; }
    public void setNombreParticipants(int nombreParticipants) { this.nombreParticipants = nombreParticipants; }
}