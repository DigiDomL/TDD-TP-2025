package fr.formation.tddtp2025.service;

import fr.formation.tddtp2025.model.Adherent;
import fr.formation.tddtp2025.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {
    public EmailService() {
    }

    public void sendReminder(Adherent adherent, List<Reservation> expiredReservations) {
        String message = "Cher " + adherent.getPrenom() + " " + adherent.getNom() + ",\n\n"
                + "Voici la liste de vos réservations expirées :\n";

        for (Reservation reservation : expiredReservations) {
            message += "Livre: " + reservation.getLivre().getTitre() + " (Date Limite: " + reservation.getDateLimite() + ")\n";
        }

        message += "\nMerci de bien vouloir prendre les mesures nécessaires.";

        System.out.println("Envoi de l'e-mail à: " + adherent.getCodeAdherent());
        System.out.println("Message: " + message);
    }
}
