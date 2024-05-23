package br.com.cepedi.ShoppingStore.service.productRating.validation.update;

import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidationIfProductExistsForUpdateProductRatingTest {

    @InjectMocks
    private ValidationIfProductExistsForUpdateProductRating validator;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void testValidationWithExistingProduct() {
        long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);

       
        assertDoesNotThrow(() -> validator.validation(new DataUpdateProductRating(productId, null, null, null)));
    }

    @Test
    public void testValidationWithNonExistingProduct() {
        long productId = 2L;
        when(productRepository.existsById(productId)).thenReturn(false);

       
        assertThrows(ValidationException.class, () -> validator.validation(new DataUpdateProductRating(productId, null, null, null)));
    }
}

