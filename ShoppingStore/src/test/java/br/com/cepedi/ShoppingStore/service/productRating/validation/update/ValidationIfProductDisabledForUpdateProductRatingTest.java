package br.com.cepedi.ShoppingStore.service.productRating.validation.update;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.entitys.ProductRating;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.repository.ProductRatingRepository;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class ValidationIfProductDisabledForUpdateProductRatingTest {

    @Mock
    private ProductRatingRepository productRatingRepository;

    @InjectMocks
    private ValidationIfProductRatingIsDisabledForUpdate validation;
    
    @InjectMocks
    private ValidationIfProductDisabledForUpdateProductRating validator;

    @Mock
    private ProductRepository productRepository;


  

    @Test
    public void testValidationWithEnabledProduct() {
        long productId = 1L;
        Product product = new Product();
        product.setDisabled(false);
        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.getReferenceById(productId)).thenReturn(product);

        assertDoesNotThrow(() -> validator.validation(new DataUpdateProductRating(1L, productId, BigDecimal.valueOf(4.5), "Great product", 1001L)));
    }

    @Test
    public void testValidationWithDisabledProduct() {
        long productId = 2L;
        Product product = new Product();
        product.setDisabled(true);
        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.getReferenceById(productId)).thenReturn(product);

        assertThrows(ValidationException.class, () -> validator.validation(new DataUpdateProductRating(2L, productId, BigDecimal.valueOf(3.0), "Average product", 1002L)));
    }

    @Test
    public void testValidationWithNonExistingProduct() {
        long productId = 3L;
        when(productRepository.existsById(productId)).thenReturn(false);

        assertDoesNotThrow(() -> validator.validation(new DataUpdateProductRating(3L, productId, BigDecimal.valueOf(4.0), "Excellent product", 1003L)));
    }

    @Test
    public void testValidation_ProductRatingNotFound() {
        // Given
        long productRatingId = 3L;
        DataUpdateProductRating data = new DataUpdateProductRating(productRatingId, productRatingId, null, null, productRatingId);
        when(productRatingRepository.existsById(productRatingId)).thenReturn(false);

        // When/Then
        validation.validation(data); // No exception should be thrown since the ProductRating doesn't exist
        verify(productRatingRepository, times(1)).existsById(productRatingId);
        verify(productRatingRepository, never()).getReferenceById(productRatingId);
    }
}

