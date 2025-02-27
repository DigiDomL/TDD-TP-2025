package fr.formation.tddtp2025.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import fr.formation.tddtp2025.IsbnValidator;
import fr.formation.tddtp2025.model.Format;
import fr.formation.tddtp2025.model.Livre;
import fr.formation.tddtp2025.repo.LivreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LivreServiceTest {

    @Mock
    private LivreRepository livreRepository;

    @InjectMocks
    private LivreService livreService;

    @Mock
    private IsbnValidator isbnValidator;

    // Ajout Livre
    @Test
    void whenAdd_IsbnIsValidAndAllFieldsProvided_shouldAddLivreSuccessfully() {
        Livre livre = new Livre("2253009687", "Titre", "Auteur", "Editeur", Format.POCHE);

        when(livreRepository.save(any(Livre.class))).thenReturn(livre);

        Livre resultat = livreService.ajouterLivre(livre);

        verify(livreRepository, times(1)).save(livre);
        assertNotNull(resultat);
        assertEquals("2253009687", resultat.getIsbn());
    }

    @Test
    void whenAdd_IsbnIsInvalid_shouldThrowException() {
        Livre livre = new Livre("123456789", "Titre", "Auteur", "Editeur", Format.BROCHE);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            livreService.ajouterLivre(livre);
        });

        assertEquals("L'ISBN doit contenir exactement 10 ou 13 chiffres.", exception.getMessage());
        verify(livreRepository, never()).save(any(Livre.class));
    }

    @Test
    void whenAdd_LivreHasMissingFields_shouldThrowException() {
        Livre livreIncomplet = new Livre("2253009687", null, null, null, null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            livreService.ajouterLivre(livreIncomplet);
        });

        assertEquals("Tous les champs doivent être renseignés.", exception.getMessage());
        verify(livreRepository, never()).save(any(Livre.class));
    }

    @Test
    void whenAdd_LivreAlreadyExists_shouldThrowException() {
        Livre livre = new Livre("2253009687", "Titre", "Auteur", "Editeur", Format.POCHE);

        when(livreRepository.findByIsbn(livre.getIsbn())).thenReturn(Optional.of(livre));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            livreService.ajouterLivre(livre);
        });

        assertEquals("Le livre existe déjà dans la bibliothèque.", exception.getMessage());
        verify(livreRepository, never()).save(any(Livre.class));
    }

    // Modifier Livre
    @Test
    void whenModify_IsbnIsValidAndAllFieldsProvided_shouldModifyBookSuccessfully() {
        Livre livreExistant = new Livre("2253009687", "Titre", "Auteur", "Editeur", Format.POCHE);
        Livre livreModifie = new Livre("2253009687", "Nouveau Titre", "Nouvel Auteur", "Nouveau Editeur", Format.BROCHE);

        when(livreRepository.findByIsbn(livreExistant.getIsbn())).thenReturn(Optional.of(livreExistant));
        when(livreRepository.save(livreModifie)).thenReturn(livreModifie);

        Livre resultat = livreService.modifierLivre(livreExistant.getIsbn(), livreModifie);

        verify(livreRepository, times(1)).save(livreModifie);
        assertNotNull(resultat);
        assertEquals("Nouveau Titre", resultat.getTitre());
        assertEquals("2253009687", resultat.getIsbn());
    }


    @Test
    void whenModify_IsbnIsInvalid_shouldThrowException() {
        Livre livreExistant = new Livre("123456789", "Titre", "Auteur", "Editeur", Format.BROCHE);
        Livre livreModifie = new Livre("123456789", "Nouveau Titre", "Nouvel Auteur", "Nouveau Editeur", Format.BROCHE);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            livreService.modifierLivre(livreExistant.getIsbn(), livreModifie);
        });

        assertEquals("L'ISBN doit contenir exactement 10 ou 13 chiffres.", exception.getMessage());

        verify(livreRepository, never()).save(any(Livre.class));
    }

    @Test
    void whenModify_BookDoesNotExist_shouldThrowException() {
        Livre livreModifie = new Livre("2253009687", "Nouveau Titre", "Nouvel Auteur", "Nouveau Editeur", Format.BROCHE);

        when(livreRepository.findByIsbn(livreModifie.getIsbn())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            livreService.modifierLivre(livreModifie.getIsbn(), livreModifie);
        });

        assertEquals("Livre non trouvé.", exception.getMessage());
        verify(livreRepository, never()).save(any(Livre.class));
    }

    @Test
    void whenModify_BookHasMissingFields_shouldThrowException() {
        Livre livreExistant = new Livre("2253009687", "Titre", "Auteur", "Editeur", Format.POCHE);
        Livre livreModifie = new Livre("2253009687", null, null, null, null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            livreService.modifierLivre(livreExistant.getIsbn(), livreModifie);
        });

        assertEquals("Tous les champs doivent être renseignés.", exception.getMessage());
        verify(livreRepository, never()).save(any(Livre.class));
    }

    // Supprimer Livre
    @Test
    void whenDelete_BookExists_shouldDeleteBookSuccessfully() {
        Livre livreExistant = new Livre("2253009687", "Titre", "Auteur", "Editeur", Format.POCHE);

        when(livreRepository.findByIsbn(livreExistant.getIsbn())).thenReturn(Optional.of(livreExistant));

        livreService.supprimerLivre(livreExistant.getIsbn());

        verify(livreRepository, times(1)).deleteByIsbn(livreExistant.getIsbn());
    }

    @Test
    void whenDelete_BookDoesNotExist_shouldThrowException() {
        String isbnNonExistant = "2253009687";

        when(livreRepository.findByIsbn(isbnNonExistant)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            livreService.supprimerLivre(isbnNonExistant);
        });

        assertEquals("Livre non trouvé.", exception.getMessage());
        verify(livreRepository, never()).deleteByIsbn(anyString());
    }

    @Test
    void whenDelete_IsbnIsInvalid_shouldThrowException() {
        Livre livreExistant = new Livre("123456789", "Titre", "Auteur", "Editeur", Format.BROCHE);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            livreService.supprimerLivre(livreExistant.getIsbn());
        });

        assertEquals("L'ISBN doit contenir exactement 10 ou 13 chiffres.", exception.getMessage());
        verify(livreRepository, never()).deleteByIsbn(anyString());
    }

    // Recherche
    @Test
    void whenIsbnExists_shouldReturnLivre() {
        Livre livre = new Livre("1234567890", "Titre", "Auteur", "Editeur", Format.POCHE);
        when(livreRepository.findByIsbn("1234567890")).thenReturn(Optional.of(livre));

        Optional<Livre> resultat = livreService.rechercherParIsbn("1234567890");

        assertTrue(resultat.isPresent());
        assertEquals("1234567890", resultat.get().getIsbn());
    }

    @Test
    void whenIsbnDoesNotExist_shouldReturnEmpty() {
        when(livreRepository.findByIsbn("0000000000")).thenReturn(Optional.empty());

        Optional<Livre> resultat = livreService.rechercherParIsbn("0000000000");

        assertFalse(resultat.isPresent());
    }

    @Test
    void whenTitreMatches_shouldReturnListOfLivres() {
        Livre livre1 = new Livre("1234567890", "Java Basics", "Auteur1", "Editeur1", Format.BROCHE);
        Livre livre2 = new Livre("0987654321", "Advanced Java", "Auteur2", "Editeur2", Format.POCHE);

        when(livreRepository.findByTitreContainingIgnoreCase("Java")).thenReturn(List.of(livre1, livre2));

        List<Livre> resultats = livreService.rechercherParTitre("Java");

        assertEquals(2, resultats.size());
        assertEquals("Java Basics", resultats.get(0).getTitre());
        assertEquals("Advanced Java", resultats.get(1).getTitre());
    }

    @Test
    void whenAuteurMatches_shouldReturnListOfLivres() {
        Livre livre = new Livre("1234567890", "Titre", "Auteur Spécial", "Editeur", Format.GRAND_FORMAT);

        when(livreRepository.findByAuteurContainingIgnoreCase("Spécial")).thenReturn(List.of(livre));

        List<Livre> resultats = livreService.rechercherParAuteur("Spécial");

        assertEquals(1, resultats.size());
        assertEquals("Auteur Spécial", resultats.get(0).getAuteur());
    }
}
