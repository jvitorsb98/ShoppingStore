package br.com.cepedi.ShoppingStore.service.product.validations.update;

import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidationUpdateProductSomeTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ValidationUpdateProductSome validationUpdateProductSome;

    public ValidationUpdateProductSomeTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidationWithExistingProduct() {
        // Setup
        Long productId = 1L;
        DataUpdateProduct dataUpdateProduct = new DataUpdateProduct(
                productId,
                "Updated Name",
                "Updated Description",
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );

        when(productRepository.existsById(productId)).thenReturn(true);

        // Validation
        assertDoesNotThrow(() -> {
            validationUpdateProductSome.validation(dataUpdateProduct);
        });
    }

    @Test
    void testValidationWithNonExistingProduct() {
        // Setup
        Long productId = 2L;
        DataUpdateProduct dataUpdateProduct = new DataUpdateProduct(
                productId,
                "Updated Name",
                "Updated Description",
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );

        when(productRepository.existsById(productId)).thenReturn(false);

        // Validation
        assertThrows(ValidationException.class, () -> {
            validationUpdateProductSome.validation(dataUpdateProduct);
        });
    }

}
