package fr.formation.tddtp2025.repo;

import fr.formation.tddtp2025.model.Adherent;
import java.util.Optional;

public interface AdherentRepository {
    Adherent save(Adherent adherent);
    Optional<Adherent> findByCodeAdherent(String codeAdherent);
}
