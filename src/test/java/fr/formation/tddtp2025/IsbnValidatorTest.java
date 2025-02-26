package fr.formation.tddtp2025;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IsbnValidatorTest {
    @Test
    public void whenIsbnIsValid_shouldReturnTrue() {

        IsbnValidator validator = new IsbnValidator();

        boolean result = validator.validateIsbn("2253009687");

        assertTrue(result);

    }

    @Test
    public void whenIsbnIsInvalid_shouldReturnFalse() {

        IsbnValidator validator = new IsbnValidator();

        boolean result = validator.validateIsbn("2253009684");

        assertFalse(result);

    }

    @Test
    public void whenIsbnIsTooShort_shouldReturnException() {

        IsbnValidator validator = new IsbnValidator();

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validateIsbn("225300967"));

        assertEquals("Le nombre doit contenir exactement 10 chiffres.",
                exception.getMessage());

    }

    @Test
    public void whenIsbnIsTooLong_shouldReturnException() {

        IsbnValidator validator = new IsbnValidator();

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validateIsbn("22530096877"));

        assertEquals("Le nombre doit contenir exactement 10 chiffres.",
                exception.getMessage());

    }
}
