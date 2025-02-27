package fr.formation.tddtp2025.service;

import fr.formation.tddtp2025.model.Livre;
import fr.formation.tddtp2025.repo.LivreRepository;
import fr.formation.tddtp2025.IsbnValidator;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LivreService {
    private final LivreRepository livreRepository;

    public LivreService(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    public Livre ajouterLivre(Livre livre) {
        if (livre.getIsbn() == null || livre.getTitre() == null || livre.getAuteur() == null || livre.getEditeur() == null || livre.getFormat() == null) {
            throw new IllegalArgumentException("Tous les champs doivent être renseignés.");
        }

        IsbnValidator.validateIsbn(livre.getIsbn());

        Optional<Livre> livreExistant = livreRepository.findByIsbn(livre.getIsbn());
        if (livreExistant.isPresent()) {
            throw new IllegalArgumentException("Le livre existe déjà dans la bibliothèque.");
        }

        return livreRepository.save(livre);
    }

    public Livre modifierLivre(String isbn, Livre livreModifie) {
        if (livreModifie.getIsbn() == null || livreModifie.getTitre() == null || livreModifie.getAuteur() == null || livreModifie.getEditeur() == null || livreModifie.getFormat() == null) {
            throw new IllegalArgumentException("Tous les champs doivent être renseignés.");
        }
        
        IsbnValidator.validateIsbn(isbn);

        Optional<Livre> livreExistant = livreRepository.findByIsbn(isbn);
        if (livreExistant.isEmpty()) {
            throw new IllegalArgumentException("Livre non trouvé.");
        }
        return livreRepository.save(livreModifie);
    }

    public void supprimerLivre(String isbn) {
        IsbnValidator.validateIsbn(isbn);

        Optional<Livre> livreExistant = livreRepository.findByIsbn(isbn);
        if (livreExistant.isEmpty()) {
            throw new IllegalArgumentException("Livre non trouvé.");
        }
        livreRepository.deleteByIsbn(isbn);
    }

    public Optional<Livre> rechercherParIsbn(String isbn) {
        return livreRepository.findByIsbn(isbn);
    }

    public List<Livre> rechercherParTitre(String titre) {
        return livreRepository.findByTitreContainingIgnoreCase(titre);
    }

    public List<Livre> rechercherParAuteur(String auteur) {
        return livreRepository.findByAuteurContainingIgnoreCase(auteur);
    }
}
