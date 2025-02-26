package fr.formation.tddtp2025;

import java.util.Arrays;
import java.util.List;

public class IsbnValidator {

    public boolean validateIsbn(String number) {
        if (number.length() != 10) {
            throw new IllegalArgumentException("Le nombre doit contenir exactement 10 chiffres.");
        }

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
}
