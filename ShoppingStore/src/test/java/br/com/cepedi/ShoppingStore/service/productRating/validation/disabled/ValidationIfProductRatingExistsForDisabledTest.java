package br.com.cepedi.ShoppingStore.service.productRating.validation.disabled;

import br.com.cepedi.ShoppingStore.repository.ProductRatingRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidationIfProductRatingExistsForDisabledTest {

    @InjectMocks
    private ValidationIfProductRatingExistsForDisabled validationIfProductRatingExistsForDisabled;

    @Mock
    private ProductRatingRepository productRatingRepository;

    @Test
    public void testValidationWithExistingProductRating() {
        long id = 1L;
        when(productRatingRepository.existsById(id)).thenReturn(true);
      
        validationIfProductRatingExistsForDisabled.validation(id); 
    }

    @Test
    public void testValidationWithNonExistingProductRating() {
       
        long id = 2L;
        when(productRatingRepository.existsById(id)).thenReturn(false);


        assertThrows(ValidationException.class, () -> validationIfProductRatingExistsForDisabled.validation(id));
    }
}
