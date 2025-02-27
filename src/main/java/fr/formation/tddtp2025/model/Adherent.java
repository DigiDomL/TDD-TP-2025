package fr.formation.tddtp2025.model;

import java.time.LocalDate;

public class Adherent {
    private String codeAdherent;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String civilite;

    public Adherent(String codeAdherent, String nom, String prenom, LocalDate dateNaissance, String civilite) {
        this.codeAdherent = codeAdherent;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.civilite = civilite;
    }

    public String getCodeAdherent() {
        return codeAdherent;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public String getCivilite() {
        return civilite;
    }
}