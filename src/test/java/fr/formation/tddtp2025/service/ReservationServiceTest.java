package fr.formation.tddtp2025.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import fr.formation.tddtp2025.model.*;
import fr.formation.tddtp2025.repo.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ReservationService reservationService;

    private Adherent adherent;
    private Livre livre;

    @BeforeEach
    void setUp() {
        adherent = new Adherent("A001", "Macron", "Ben", LocalDate.of(1980, 1, 1), "M.");
        livre = new Livre("2253009687", "Titre Livre", "Auteur", "Editeur", Format.BROCHE);
    }

    @Test
    void whenReservationDateIsValid_shouldCreateReservation() {
        LocalDate dateReservation = LocalDate.now();
        LocalDate dateLimite = dateReservation.plusMonths(4);
        Reservation reservation = new Reservation(adherent, livre, dateReservation, dateLimite);

        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation resultat = reservationService.createReservation(reservation);

        assertNotNull(resultat);
        assertEquals(adherent, resultat.getAdherent());
        assertEquals(livre, resultat.getLivre());
        assertEquals(dateLimite, resultat.getDateLimite());
    }

    @Test
    void whenReservationDateExceeds4Months_shouldThrowException() {
        LocalDate dateReservation = LocalDate.now();
        LocalDate dateLimite = dateReservation.plusMonths(5);
        Reservation reservation = new Reservation(adherent, livre, dateReservation, dateLimite);

        assertThrows(IllegalArgumentException.class, () ->
                        reservationService.createReservation(reservation),
                "La date limite ne peut pas excéder 4 mois.");
    }

    @Test
    void whenAdherentHasMoreThan3OpenReservations_shouldThrowException() {
        List<Reservation> reservations = Arrays.asList(
                new Reservation(adherent, livre, LocalDate.now(), LocalDate.now().plusMonths(4)),
                new Reservation(adherent, livre, LocalDate.now(), LocalDate.now().plusMonths(4)),
                new Reservation(adherent, livre, LocalDate.now(), LocalDate.now().plusMonths(4))
        );

        when(reservationRepository.countByAdherentAndDateLimiteAfter(adherent, LocalDate.now())).thenReturn(3L);

        Reservation reservation = new Reservation(adherent, livre, LocalDate.now(), LocalDate.now().plusMonths(4));

        assertThrows(IllegalStateException.class, () ->
                        reservationService.createReservation(reservation),
                "Un adhérent ne peut pas avoir plus de 3 réservations ouvertes simultanées.");
    }

    @Test
    void whenGetOpenReservations_shouldReturnOpenReservations() {
        List<Reservation> openReservations = Arrays.asList(
                new Reservation(adherent, livre, LocalDate.now(), LocalDate.now().plusMonths(4)),
                new Reservation(adherent, livre, LocalDate.now(), LocalDate.now().plusMonths(4))
        );

        when(reservationRepository.findByAdherentAndDateLimiteAfter(adherent, LocalDate.now())).thenReturn(openReservations);

        List<Reservation> result = reservationService.getOpenReservations(adherent);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void whenGetReservationHistory_shouldReturnReservationHistory() {
        List<Reservation> history = Arrays.asList(
                new Reservation(adherent, livre, LocalDate.now().minusMonths(6), LocalDate.now().minusMonths(2))
        );

        when(reservationRepository.findByAdherent(adherent)).thenReturn(history);

        List<Reservation> result = reservationService.getReservationHistory(adherent);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void whenReservationExpired_shouldSendEmailReminder() {
        Reservation reservation = new Reservation(adherent, livre, LocalDate.now().minusMonths(5), LocalDate.now().minusMonths(1));
        List<Reservation> expiredReservations = List.of(reservation);

        when(reservationRepository.findExpiredReservations()).thenReturn(expiredReservations);

        reservationService.sendReminderForExpiredReservations();

        verify(emailService, times(1)).sendReminder(adherent, expiredReservations);
    }

    @Test
    void whenMultipleReservationsExpired_shouldSendOnlyOneReminderEmail() {
        Reservation reservation1 = new Reservation(adherent, livre, LocalDate.now().minusMonths(5), LocalDate.now().minusMonths(1));
        Reservation reservation2 = new Reservation(adherent, livre, LocalDate.now().minusMonths(5), LocalDate.now().minusMonths(1));
        List<Reservation> expiredReservations = Arrays.asList(reservation1, reservation2);

        when(reservationRepository.findExpiredReservations()).thenReturn(expiredReservations);

        reservationService.sendReminderForExpiredReservations();

        verify(emailService, times(1)).sendReminder(adherent, expiredReservations);
    }

    @Test
    void whenCancelReservation_shouldDeleteReservationFromRepository() {
        Reservation reservation = new Reservation(adherent, livre, LocalDate.now(), LocalDate.now().plusMonths(4));

        reservationService.cancelReservation(reservation);

        verify(reservationRepository, times(1)).delete(reservation);
    }
}