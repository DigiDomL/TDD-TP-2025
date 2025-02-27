package fr.formation.tddtp2025.service;

import fr.formation.tddtp2025.model.Adherent;
import fr.formation.tddtp2025.repo.AdherentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdherentService {

    private final AdherentRepository adherentRepository;

    public AdherentService(AdherentRepository adherentRepository) {
        this.adherentRepository = adherentRepository;
    }

    public Adherent ajouterAdherent(Adherent adherent) {
        if (adherent.getCodeAdherent() == null || adherent.getNom() == null || adherent.getPrenom() == null ||
                adherent.getDateNaissance() == null || adherent.getCivilite() == null) {
            throw new IllegalArgumentException("Tous les champs doivent être renseignés.");
        }
        return adherentRepository.save(adherent);
    }

    public Adherent modifierAdherent(String codeAdherent, Adherent adherentModifie) {
        Optional<Adherent> adherentExistant = adherentRepository.findByCodeAdherent(codeAdherent);
        if (adherentExistant.isEmpty()) {
            throw new IllegalArgumentException("Adhérent non trouvé.");
        }
        return adherentRepository.save(adherentModifie);
    }

    public void supprimerAdherent(String codeAdherent) {
        Optional<Adherent> adherentExistant = adherentRepository.findByCodeAdherent(codeAdherent);
        if (adherentExistant.isPresent()) {
            System.out.println("Adhérent supprimé : " + codeAdherent);
        }
    }

    public Optional<Adherent> rechercherParCodeAdherent(String codeAdherent) {
        return adherentRepository.findByCodeAdherent(codeAdherent);
    }
}
