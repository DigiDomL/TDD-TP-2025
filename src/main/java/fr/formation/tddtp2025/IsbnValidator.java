package fr.formation.tddtp2025;

public class IsbnValidator {

    public boolean validateIsbn(String number) {
        if (number.length() != 10) {
            return false;
        }
        return true;
    }
}
