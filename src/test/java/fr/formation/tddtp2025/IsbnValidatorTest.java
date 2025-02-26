package fr.formation.tddtp2025;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
