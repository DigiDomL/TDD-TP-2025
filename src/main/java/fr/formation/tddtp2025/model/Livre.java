package fr.formation.tddtp2025.model;

public class Livre {

    private String isbn;
    private String titre;
    private String auteur;
    private String editeur;
    private Format format;

    public Livre(String isbn, String titre, String auteur, String editeur, Format format) {
        this.isbn = isbn;
        this.titre = titre;
        this.auteur = auteur;
        this.editeur = editeur;
        this.format = format;
    }

    // Getters et setters nécessaires pour le test
    public String getIsbn() {
        return isbn;
    }
}
