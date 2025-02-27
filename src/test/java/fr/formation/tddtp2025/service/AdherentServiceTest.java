package fr.formation.tddtp2025.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import fr.formation.tddtp2025.model.Adherent;
import fr.formation.tddtp2025.repo.AdherentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AdherentServiceTest {

    @Mock
    private AdherentRepository adherentRepository;

    @InjectMocks
    private AdherentService adherentService;

    @Test
    void whenAdd_AdherentIsValid_shouldAddSuccessfully() {
        Adherent adherent = new Adherent("A123", "Macron", "Ben", LocalDate.of(1990, 5, 14), "Monsieur");

        when(adherentRepository.save(any(Adherent.class))).thenReturn(adherent);

        Adherent resultat = adherentService.ajouterAdherent(adherent);

        verify(adherentRepository, times(1)).save(adherent);

        assertNotNull(resultat);
        assertEquals("A123", resultat.getCodeAdherent());
        assertEquals("Macron", resultat.getNom());
        assertEquals("Ben", resultat.getPrenom());
        assertEquals(LocalDate.of(1990, 5, 14), resultat.getDateNaissance());
        assertEquals("Monsieur", resultat.getCivilite());
    }

    @Test
    void whenAdd_AdherentWithMissingFields_shouldThrowException() {
        Adherent adherentIncomplet = new Adherent(null, "Macron", "Ben", LocalDate.of(1990, 5, 14), "Monsieur");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            adherentService.ajouterAdherent(adherentIncomplet);
        });

        assertEquals("Tous les champs doivent être renseignés.", exception.getMessage());

        verify(adherentRepository, never()).save(any(Adherent.class));
    }

    @Test
    void whenModify_AdherentExists_shouldModifySuccessfully() {
        Adherent adherentExistant = new Adherent("A123", "Macron", "Ben", LocalDate.of(1990, 5, 14), "Monsieur");
        Adherent adherentModifie = new Adherent("A123", "Macron", "Ben", LocalDate.of(1990, 5, 14), "Monsieur");

        when(adherentRepository.findByCodeAdherent(adherentExistant.getCodeAdherent())).thenReturn(Optional.of(adherentExistant));
        when(adherentRepository.save(adherentModifie)).thenReturn(adherentModifie);

        Adherent resultat = adherentService.modifierAdherent(adherentExistant.getCodeAdherent(), adherentModifie);

        verify(adherentRepository, times(1)).save(adherentModifie);
        assertNotNull(resultat);
        assertEquals("A123", resultat.getCodeAdherent());
        assertEquals("Ben", resultat.getPrenom());
    }

    @Test
    void whenModify_AdherentNotFound_shouldThrowException() {
        Adherent adherentModifie = new Adherent("A124", "Durand", "Marie", LocalDate.of(1985, 3, 22), "Madame");

        when(adherentRepository.findByCodeAdherent("A124")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            adherentService.modifierAdherent("A124", adherentModifie);
        });

        assertEquals("Adhérent non trouvé.", exception.getMessage());
        verify(adherentRepository, never()).save(any(Adherent.class));
    }

    @Test
    void whenDelete_AdherentExists_shouldDeleteSuccessfully() {
        Adherent adherentExistant = new Adherent("A123", "Macron", "Ben", LocalDate.of(1990, 5, 14), "Monsieur");

        when(adherentRepository.findByCodeAdherent(adherentExistant.getCodeAdherent())).thenReturn(Optional.of(adherentExistant));

        adherentService.supprimerAdherent(adherentExistant.getCodeAdherent());

        verify(adherentRepository, times(1)).findByCodeAdherent(adherentExistant.getCodeAdherent());
        // On peut aussi tester ici la suppression si nécessaire
    }

    @Test
    void whenDelete_AdherentNotFound_shouldNotDelete() {
        when(adherentRepository.findByCodeAdherent("A123")).thenReturn(Optional.empty());

        adherentService.supprimerAdherent("A123");

        verify(adherentRepository, times(1)).findByCodeAdherent("A123");
    }

    @Test
    void whenSearch_AdherentExists_shouldReturnAdherent() {
        Adherent adherentExistant = new Adherent("A123", "Macron", "Ben", LocalDate.of(1990, 5, 14), "Monsieur");

        when(adherentRepository.findByCodeAdherent("A123")).thenReturn(Optional.of(adherentExistant));

        Optional<Adherent> resultat = adherentService.rechercherParCodeAdherent("A123");

        assertTrue(resultat.isPresent());
        assertEquals("A123", resultat.get().getCodeAdherent());
        assertEquals("Ben", resultat.get().getPrenom());
    }

    @Test
    void whenSearch_AdherentNotFound_shouldReturnEmpty() {
        when(adherentRepository.findByCodeAdherent("A124")).thenReturn(Optional.empty());

        Optional<Adherent> resultat = adherentService.rechercherParCodeAdherent("A124");

        assertFalse(resultat.isPresent());
    }
}
