package fr.formation.tddtp2025.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LivreServiceTest {

    @Mock
    private LivreRepository livreRepository;

    @InjectMocks
    private LivreService livreService;

    @Test
    void testAjouterLivreValide() {
        Livre livre = new Livre("2253009687", "Titre", "Auteur", "Editeur", Format.POCHE);

        when(livreRepository.save(any(Livre.class))).thenReturn(livre);

        Livre resultat = livreService.ajouterLivre(livre);

        verify(livreRepository, times(1)).save(livre);
        assertNotNull(resultat);
        assertEquals("2253009687", resultat.getIsbn());
    }
}
