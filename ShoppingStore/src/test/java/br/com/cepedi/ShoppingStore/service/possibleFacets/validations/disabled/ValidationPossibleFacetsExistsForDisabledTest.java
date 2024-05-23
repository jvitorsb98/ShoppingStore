package br.com.cepedi.ShoppingStore.service.possibleFacets.validations.disabled;



import br.com.cepedi.ShoppingStore.repository.PossibleFacetsRepository;
import com.github.javafaker.Faker;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ValidationPossibleFacetsExistsForDisabledTest {

    @InjectMocks
    private ValidationPossibleFacetsExistsForDisabled validator;

    @Mock
    private PossibleFacetsRepository possibleFacetsRepository;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testValidationWithExistingId() {
        Long validId = faker.number().randomNumber();
        when(possibleFacetsRepository.existsById(validId)).thenReturn(true);

        assertDoesNotThrow(() -> validator.validation(validId));
    }

    @Test
    public void testValidationWithNonExistentId() {
        Long invalidId = faker.number().randomNumber();
        when(possibleFacetsRepository.existsById(invalidId)).thenReturn(false);

        assertThrows(ValidationException.class, () -> validator.validation(invalidId));
    }
}
