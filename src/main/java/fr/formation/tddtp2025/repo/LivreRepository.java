package fr.formation.tddtp2025.repo;

import fr.formation.tddtp2025.model.Livre;

import java.util.List;
import java.util.Optional;

public interface LivreRepository {
    Livre save(Livre livre);
    void deleteByIsbn(String isbn);
    Optional<Livre> findByIsbn(String isbn);
    List<Livre> findByTitreContainingIgnoreCase(String titre);
    List<Livre> findByAuteurContainingIgnoreCase(String auteur);
}

