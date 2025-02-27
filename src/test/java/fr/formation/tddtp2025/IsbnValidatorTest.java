package fr.formation.tddtp2025;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IsbnValidatorTest {
    @Test
    public void whenIsbnIsValid_shouldReturnTrue() {
        boolean result = IsbnValidator.validateIsbn("2253009687");
        assertTrue(result);
    }

    @Test
    public void whenIsbnIsInvalid_shouldReturnFalse() {
        boolean result = IsbnValidator.validateIsbn("2253009684");
        assertFalse(result);
    }

    @Test
    public void whenMultipleIsbnIsValid_shouldReturnTrue() {
        String[] validIsbns = {"2253009687", "0471958697"};
        for (String isbn : validIsbns) {
            boolean result = IsbnValidator.validateIsbn(isbn);
            assertTrue(result);
        }
    }

    @Test
    public void whenIsbnIsTooShort_shouldReturnException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> IsbnValidator.validateIsbn("225300967"));
        assertEquals("L'ISBN doit contenir exactement 10 ou 13 chiffres.", exception.getMessage());
    }

    @Test
    public void whenIsbnIsTooLong_shouldReturnException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> IsbnValidator.validateIsbn("22530096877"));
        assertEquals("L'ISBN doit contenir exactement 10 ou 13 chiffres.", exception.getMessage());
    }

    @Test
    public void whenIsbn13IsValid_shouldReturnTrue() {
        boolean result = IsbnValidator.validateIsbn("9780306406157");
        assertTrue(result);
    }

    @Test
    public void whenIsbn13IsInvalid_shouldReturnFalse() {
        boolean result = IsbnValidator.validateIsbn("9780306406158");
        assertFalse(result);
    }

    @Test
    public void whenMultipleIsbn13IsValid_shouldReturnTrue() {
        String[] validIsbns = {"9780306406157", "9781861972712"};
        for (String isbn : validIsbns) {
            boolean result = IsbnValidator.validateIsbn(isbn);
            assertTrue(result);
        }
    }
}
