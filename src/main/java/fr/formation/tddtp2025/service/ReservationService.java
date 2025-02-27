package fr.formation.tddtp2025.service;

import fr.formation.tddtp2025.model.Adherent;
import fr.formation.tddtp2025.model.Livre;
import fr.formation.tddtp2025.model.Reservation;
import fr.formation.tddtp2025.repo.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final EmailService emailService;

    public ReservationService(ReservationRepository reservationRepository, EmailService emailService) {
        this.reservationRepository = reservationRepository;
        this.emailService = emailService;
    }

    public Reservation createReservation(Reservation reservation) {
        LocalDate dateReservation = reservation.getDateReservation();
        LocalDate dateLimite = reservation.getDateLimite();

        if (dateLimite.isAfter(dateReservation.plusMonths(4))) {
            throw new IllegalArgumentException("La date limite ne peut pas excéder 4 mois.");
        }

        long openReservationsCount = reservationRepository.countByAdherentAndDateLimiteAfter(reservation.getAdherent(), LocalDate.now());
        if (openReservationsCount >= 3) {
            throw new IllegalStateException("Un adhérent ne peut pas avoir plus de 3 réservations ouvertes simultanément.");
        }

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getOpenReservations(Adherent adherent) {
        return reservationRepository.findByAdherentAndDateLimiteAfter(adherent, LocalDate.now());
    }

    public List<Reservation> getReservationHistory(Adherent adherent) {
        return reservationRepository.findByAdherent(adherent);
    }

    public void cancelReservation(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    public void sendReminderForExpiredReservations() {
        List<Reservation> expiredReservations = reservationRepository.findExpiredReservations();
        Map<Adherent, List<Reservation>> reservationsByAdherent = expiredReservations.stream()
                .collect(Collectors.groupingBy(Reservation::getAdherent));
    
        for (Map.Entry<Adherent, List<Reservation>> entry : reservationsByAdherent.entrySet()) {
            Adherent adherent = entry.getKey();
            List<Reservation> adherentReservations = entry.getValue();
            emailService.sendReminder(adherent, adherentReservations);
        }
    }
}
