package fr.formation.tddtp2025.controller;

import fr.formation.tddtp2025.model.Livre;
import fr.formation.tddtp2025.service.LivreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/livres")
public class LivreController {
    private final LivreService livreService;

    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    @PostMapping
    public ResponseEntity<Livre> ajouterLivre(@RequestBody Livre livre) {
        return ResponseEntity.ok(livreService.ajouterLivre(livre));
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Livre> modifierLivre(@PathVariable String isbn, @RequestBody Livre livre) {
        return ResponseEntity.ok(livreService.modifierLivre(isbn, livre));
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> supprimerLivre(@PathVariable String isbn) {
        livreService.supprimerLivre(isbn);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Livre> rechercherParIsbn(@PathVariable String isbn) {
        Optional<Livre> livre = livreService.rechercherParIsbn(isbn);
        return livre.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/titre/{titre}")
    public ResponseEntity<List<Livre>> rechercherParTitre(@PathVariable String titre) {
        return ResponseEntity.ok(livreService.rechercherParTitre(titre));
    }

    @GetMapping("/auteur/{auteur}")
    public ResponseEntity<List<Livre>> rechercherParAuteur(@PathVariable String auteur) {
        return ResponseEntity.ok(livreService.rechercherParAuteur(auteur));
    }
}
