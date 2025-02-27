package fr.formation.tddtp2025.controller;

import fr.formation.tddtp2025.model.Adherent;
import fr.formation.tddtp2025.model.Reservation;
import fr.formation.tddtp2025.repo.AdherentRepository;
import fr.formation.tddtp2025.repo.ReservationRepository;
import fr.formation.tddtp2025.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final AdherentRepository adherentRepository;
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationService reservationService, AdherentRepository adherentRepository, ReservationRepository reservationRepository) {
        this.reservationService = reservationService;
        this.adherentRepository = adherentRepository;
        this.reservationRepository = reservationRepository;
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation createdReservation = reservationService.createReservation(reservation);
        return ResponseEntity.ok(createdReservation);
    }

    @GetMapping("/open/{adherentId}")
    public ResponseEntity<List<Reservation>> getOpenReservations(@PathVariable String adherentId) {
        Optional<Adherent> adherent = adherentRepository.findByCodeAdherent(adherentId);
        if (adherent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Reservation> openReservations = reservationService.getOpenReservations(adherent.get());
        return ResponseEntity.ok(openReservations);
    }

    @GetMapping("/history/{adherentId}")
    public ResponseEntity<List<Reservation>> getReservationHistory(@PathVariable String adherentId) {
        Optional<Adherent> adherent = adherentRepository.findByCodeAdherent(adherentId);
        if (adherent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Reservation> reservationHistory = reservationService.getReservationHistory(adherent.get());
        return ResponseEntity.ok(reservationHistory);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        reservationService.cancelReservation(reservation.get());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/send-reminders")
    public ResponseEntity<Void> sendReminderForExpiredReservations() {
        reservationService.sendReminderForExpiredReservations();
        return ResponseEntity.noContent().build();
    }
}