package br.com.cepedi.ShoppingStore.service.product.validations.cancel;

import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidationProductExistsForDisabledTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ValidationProductExistsForDisabled validationProductExistsForDisabled;

    public ValidationProductExistsForDisabledTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidationWithExistingProduct() {
        // Setup
        long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(true);

        // Validation
        assertThrows(ValidationException.class, () -> {
            validationProductExistsForDisabled.validation(productId);
        });
    }

    @Test
    void testValidationWithNonExistingProduct() {
        // Setup
        long productId = 2L;

        when(productRepository.existsById(productId)).thenReturn(false);

        // Validation
        assertDoesNotThrow(() -> {
            validationProductExistsForDisabled.validation(productId);
        });
    }
}
