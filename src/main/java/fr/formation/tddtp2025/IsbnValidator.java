package fr.formation.tddtp2025;

import java.util.Arrays;
import java.util.List;

public class IsbnValidator {

    private IsbnValidator() {
        // Empêche l'instanciation
    }

    public static boolean validateIsbn(String number) {
        if (number == null || (number.length() != 10 && number.length() != 13)) {
            throw new IllegalArgumentException("L'ISBN doit contenir exactement 10 ou 13 chiffres.");
        }

        return number.length() == 10 ? validateIsbn10(number) : validateIsbn13(number);
    }

    private static boolean validateIsbn10(String number) {
        List<Integer> numbers = Arrays.stream(number.split(""))
                .map(Integer::parseInt)
                .toList();

        int sum = 0;
        int multiplier = 10;
        for (Integer i : numbers) {
            sum += (i * multiplier);
            multiplier--;
        }

        return (sum % 11 == 0);
    }

    private static boolean validateIsbn13(String number) {
        List<Integer> numbers = Arrays.stream(number.split(""))
                .map(Integer::parseInt)
                .toList();

        int sum = 0;
        for (int i = 0; i < numbers.size(); i++) {
            sum += (i % 2 == 0) ? numbers.get(i) : numbers.get(i) * 3;
        }

        return (sum % 10 == 0);
    }
}
