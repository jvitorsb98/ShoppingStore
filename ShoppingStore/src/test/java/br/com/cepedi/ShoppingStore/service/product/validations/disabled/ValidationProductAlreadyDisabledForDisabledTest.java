package br.com.cepedi.ShoppingStore.service.product.validations.disabled;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import br.com.cepedi.ShoppingStore.service.product.validations.cancel.ValidationProductAlreadyDisabledForDisabled;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidationProductAlreadyDisabledForDisabledTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ValidationProductAlreadyDisabledForDisabled validationProductAlreadyDisabledForDisabled;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidationWithEnabledProduct() {
        // Setup
        long productId = 1L;

        Product enabledProduct = new Product();
        enabledProduct.setId(productId);
        enabledProduct.setDisabled(false);

        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.getReferenceById(productId)).thenReturn(enabledProduct);

        // Validation
        assertDoesNotThrow(() -> {
            validationProductAlreadyDisabledForDisabled.validation(productId);
        });

        // Verify interactions
        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).getReferenceById(productId);
    }

    @Test
    void testValidationWithDisabledProduct() {
        // Setup
        long productId = 2L;

        Product disabledProduct = new Product();
        disabledProduct.setId(productId);
        disabledProduct.setDisabled(true);

        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.getReferenceById(productId)).thenReturn(disabledProduct);

        // Validation
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            validationProductAlreadyDisabledForDisabled.validation(productId);
        });

        assertEquals("The required product already disabled", exception.getMessage());

        // Verify interactions
        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).getReferenceById(productId);
    }

    @Test
    void testValidationWithNonExistingProduct() {
        // Setup
        long productId = 3L;

        when(productRepository.existsById(productId)).thenReturn(false);

        // Validation
        assertDoesNotThrow(() -> {
            validationProductAlreadyDisabledForDisabled.validation(productId);
        });

        // Verify interactions
        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, never()).getReferenceById(productId);
    }

}
