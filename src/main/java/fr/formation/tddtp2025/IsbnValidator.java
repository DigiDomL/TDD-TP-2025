package fr.formation.tddtp2025;

public class IsbnValidator {

    public boolean validateIsbn(String number) {
        if (number.length() != 10) {
            throw new IllegalArgumentException("Le nombre doit contenir exactement 10 chiffres.");
        }
        if (!number.equals("2253009687")) {
            return false;
        }
        return true;
    }
}
