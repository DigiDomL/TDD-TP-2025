package fr.formation.tddtp2025.repo;

import fr.formation.tddtp2025.model.Adherent;
import fr.formation.tddtp2025.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation save(Reservation reservation);
    Optional<Reservation> findById(Long id);
    long countByAdherentAndDateLimiteAfter(Adherent adherent, LocalDate date);
    List<Reservation> findByAdherentAndDateLimiteAfter(Adherent adherent, LocalDate date);
    List<Reservation> findByAdherent(Adherent adherent);
    List<Reservation> findExpiredReservations();
}
