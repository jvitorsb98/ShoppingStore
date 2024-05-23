package br.com.cepedi.ShoppingStore.service.possibleFacets.validations.update;

import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataUpdatePossibleFacets;
import br.com.cepedi.ShoppingStore.repository.PossibleFacetsRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidationPossibleFacetsIsDisabledTest {

    @InjectMocks
    private ValidationPossibleFacetsIsDisabled validator;

    @Mock
    private PossibleFacetsRepository possibleFacetsRepository;

    @Test
    public void testValidationWithNonDisabledPossibleFacets() {
        long possibleFacetsId = 1L;
        PossibleFacets possibleFacets = new PossibleFacets();
        possibleFacets.setDisabled(false);
        when(possibleFacetsRepository.existsById(possibleFacetsId)).thenReturn(true);
        when(possibleFacetsRepository.getReferenceById(possibleFacetsId)).thenReturn(possibleFacets);

        DataUpdatePossibleFacets data = new DataUpdatePossibleFacets(possibleFacetsId, "Updated", null);
        validator.validation(data);
    }

    @Test
    public void testValidationWithDisabledPossibleFacets() {
        long possibleFacetsId = 1L;
        PossibleFacets possibleFacets = new PossibleFacets();
        possibleFacets.setDisabled(true);
        when(possibleFacetsRepository.existsById(possibleFacetsId)).thenReturn(true);
        when(possibleFacetsRepository.getReferenceById(possibleFacetsId)).thenReturn(possibleFacets);

     
        DataUpdatePossibleFacets data = new DataUpdatePossibleFacets(possibleFacetsId, "Updated", null);
        assertThrows(ValidationException.class, () -> validator.validation(data));
    }

    //failure
    @Test
    public void testValidationWithNonExistentPossibleFacets() {
        long possibleFacetsId = 2L;
        when(possibleFacetsRepository.existsById(possibleFacetsId)).thenReturn(false);

        DataUpdatePossibleFacets data = new DataUpdatePossibleFacets(possibleFacetsId, "Updated", null);
        
        assertThrows(ValidationException.class, () -> validator.validation(data));
        
        verify(possibleFacetsRepository).existsById(possibleFacetsId);
        
        verify(possibleFacetsRepository, never()).getReferenceById(anyLong());
    }


}

