package br.com.cepedi.ShoppingStore.service.purchase.shoppingCart.validations.disabled;


import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ValidationShoppingCartExistsTest {

    @Mock
    private ShoppingCartRepository repository;

    @InjectMocks
    private ValidationShoppingCartExists validationShoppingCartExists;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidationShoppingCartExists() {
        // Given
        Long shoppingCartId = 123L;

        // Mock behavior
        when(repository.existsById(shoppingCartId)).thenReturn(true);

        // When/Then
        assertDoesNotThrow(() -> {
            validationShoppingCartExists.validation(shoppingCartId);
        });
    }

    @Test
    public void testValidationShoppingCartDoesNotExist() {
        // Given
        Long shoppingCartId = 456L;

        // Mock behavior
        when(repository.existsById(shoppingCartId)).thenReturn(false);

        // When/Then
        assertThrows(ValidationException.class, () -> {
            validationShoppingCartExists.validation(shoppingCartId);
        });
    }
}
