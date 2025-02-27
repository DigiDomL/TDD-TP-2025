package fr.formation.tddtp2025.controller;

import fr.formation.tddtp2025.model.Adherent;
import fr.formation.tddtp2025.service.AdherentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/adherents")
public class AdherentController {
    private final AdherentService adherentService;

    public AdherentController(AdherentService adherentService) {
        this.adherentService = adherentService;
    }

    @PostMapping
    public ResponseEntity<Adherent> ajouterAdherent(@RequestBody Adherent adherent) {
        Adherent createdAdherent = adherentService.ajouterAdherent(adherent);
        return ResponseEntity.ok(createdAdherent);
    }

    @PutMapping("/{codeAdherent}")
    public ResponseEntity<Adherent> modifierAdherent(@PathVariable String codeAdherent, @RequestBody Adherent adherent) {
        Adherent updatedAdherent = adherentService.modifierAdherent(codeAdherent, adherent);
        return ResponseEntity.ok(updatedAdherent);
    }

    @DeleteMapping("/{codeAdherent}")
    public ResponseEntity<Void> supprimerAdherent(@PathVariable String codeAdherent) {
        adherentService.supprimerAdherent(codeAdherent);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{codeAdherent}")
    public ResponseEntity<Adherent> rechercherParCodeAdherent(@PathVariable String codeAdherent) {
        Optional<Adherent> adherent = adherentService.rechercherParCodeAdherent(codeAdherent);
        return adherent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}