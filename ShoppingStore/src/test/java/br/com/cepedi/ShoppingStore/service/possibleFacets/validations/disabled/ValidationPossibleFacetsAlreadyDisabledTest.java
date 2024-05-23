package br.com.cepedi.ShoppingStore.service.possibleFacets.validations.disabled;


import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;

import br.com.cepedi.ShoppingStore.repository.PossibleFacetsRepository;
import com.github.javafaker.Faker;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValidationPossibleFacetsAlreadyDisabledTest {

    @InjectMocks
    private ValidationPossibleFacetsAlreadyDisabled validator;

    @Mock
    private PossibleFacetsRepository possibleFacetsRepository;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testValidationWithNonDisabledFacets() {
        Long validId = faker.number().randomNumber();
        PossibleFacets possibleFacets = mock(PossibleFacets.class);
        when(possibleFacetsRepository.existsById(validId)).thenReturn(true);
        when(possibleFacetsRepository.getReferenceById(validId)).thenReturn(possibleFacets);
        when(possibleFacets.getDisabled()).thenReturn(false);

        assertDoesNotThrow(() -> validator.validation(validId));
    }

    @Test
    public void testValidationWithDisabledFacets() {
        Long validId = faker.number().randomNumber();
        PossibleFacets possibleFacets = mock(PossibleFacets.class);
        when(possibleFacetsRepository.existsById(validId)).thenReturn(true);
        when(possibleFacetsRepository.getReferenceById(validId)).thenReturn(possibleFacets);
        when(possibleFacets.getDisabled()).thenReturn(true);

        assertThrows(ValidationException.class, () -> validator.validation(validId));
    }

    @Test
    public void testValidationWithNonExistentId() {
        Long invalidId = faker.number().randomNumber();
        when(possibleFacetsRepository.existsById(invalidId)).thenReturn(false);

        assertDoesNotThrow(() -> validator.validation(invalidId));
    }
}

