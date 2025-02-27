package fr.formation.tddtp2025.model;

import java.time.LocalDate;

public class Reservation {
    private Adherent adherent;
    private Livre livre;
    private LocalDate dateReservation;
    private LocalDate dateLimite;

    public Reservation(Adherent adherent, Livre livre, LocalDate dateReservation, LocalDate dateLimite) {
        this.adherent = adherent;
        this.livre = livre;
        this.dateReservation = dateReservation;
        this.dateLimite = dateLimite;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public Livre getLivre() {
        return livre;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public LocalDate getDateLimite() {
        return dateLimite;
    }
}
