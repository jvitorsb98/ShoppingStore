package br.com.cepedi.ShoppingStore.service.possibleFacets.validations.update;

import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataUpdatePossibleFacets;
import br.com.cepedi.ShoppingStore.repository.PossibleFacetsRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidationPossibleFacetsExistsForUpdateTest {

    @InjectMocks
    private ValidationPossibleFacetsExistsForUpdate validator;

    @Mock
    private PossibleFacetsRepository possibleFacetsRepository;

    private DataUpdatePossibleFacets validData;

    @BeforeEach
    public void setUp() {
        validData = new DataUpdatePossibleFacets(1L, "Updated", 1L);
    }

    @Test
    public void testValidationWithExistingPossibleFacets() {
        when(possibleFacetsRepository.existsById(1L)).thenReturn(true);

        validator.validation(validData);
    }

    @Test
    public void testValidationWithNonExistingPossibleFacets() {
        when(possibleFacetsRepository.existsById(2L)).thenReturn(false);

        assertThrows(ValidationException.class, () -> validator.validation(new DataUpdatePossibleFacets(2L, "Updated", 2L)));
    }

}

