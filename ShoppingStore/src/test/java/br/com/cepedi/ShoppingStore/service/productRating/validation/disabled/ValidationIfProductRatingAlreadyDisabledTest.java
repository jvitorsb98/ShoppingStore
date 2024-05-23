package br.com.cepedi.ShoppingStore.service.productRating.validation.disabled;

import br.com.cepedi.ShoppingStore.model.entitys.ProductRating;
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
public class ValidationIfProductRatingAlreadyDisabledTest {

    @InjectMocks
    private ValidationIfProductRatingAlreadyDisabled validationIfProductRatingAlreadyDisabled;

    @Mock
    private ProductRatingRepository productRatingRepository;

    @Test
    public void testValidationWithDisabledProductRating() {
       
        long id = 1L;
        ProductRating productRating = new ProductRating();
        productRating.setDisabled(true);
        when(productRatingRepository.existsById(id)).thenReturn(true);
        when(productRatingRepository.getReferenceById(id)).thenReturn(productRating);

    
        assertThrows(ValidationException.class, () -> validationIfProductRatingAlreadyDisabled.validation(id));
    }

    @Test
    public void testValidationWithEnabledProductRating() {
        
        long id = 2L;
        ProductRating productRating = new ProductRating();
        productRating.setDisabled(false);
        when(productRatingRepository.existsById(id)).thenReturn(true);
        when(productRatingRepository.getReferenceById(id)).thenReturn(productRating);

        
        validationIfProductRatingAlreadyDisabled.validation(id);
    }

    @Test
    public void testValidationWithNonExistingProductRating() {

        long id = 3L;
        when(productRatingRepository.existsById(id)).thenReturn(false);

 
        validationIfProductRatingAlreadyDisabled.validation(id); 
    }
}

