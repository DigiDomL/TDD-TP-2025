package fr.formation.tddtp2025.service;

import fr.formation.tddtp2025.model.Livre;
import fr.formation.tddtp2025.repo.LivreRepository;

public class LivreService {

    private LivreRepository livreRepository;

    // Constructeur permettant l'injection du mock lors des tests
    public LivreService(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    public Livre ajouterLivre(Livre livre) {
        // Ici, on fait le strict minimum : appeler le repository pour sauvegarder le livre.
        return livreRepository.save(livre);
    }
}
