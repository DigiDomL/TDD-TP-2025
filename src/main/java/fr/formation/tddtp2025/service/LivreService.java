package fr.formation.tddtp2025.service;

import fr.formation.tddtp2025.IsbnValidator;
import fr.formation.tddtp2025.model.Livre;
import fr.formation.tddtp2025.repo.LivreRepository;
import org.springframework.stereotype.Service;

@Service
public class LivreService {

    private final LivreRepository livreRepository;

    public LivreService(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    public Livre ajouterLivre(Livre livre) {
        if (!IsbnValidator.validateIsbn(livre.getIsbn())) {
            throw new IllegalArgumentException("ISBN invalide : " + livre.getIsbn());
        }

        return livreRepository.save(livre);
    }
}