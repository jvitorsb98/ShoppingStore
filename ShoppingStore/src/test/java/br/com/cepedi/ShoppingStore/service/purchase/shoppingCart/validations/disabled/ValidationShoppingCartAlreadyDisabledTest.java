package br.com.cepedi.ShoppingStore.service.purchase.shoppingCart.validations.disabled;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ValidationShoppingCartAlreadyDisabledTest {

    @Mock
    private ShoppingCartRepository repository;

    @InjectMocks
    private ValidationShoppingCartAlreadyDisabled validationShoppingCartAlreadyDisabled;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidationShoppingCartAlreadyDisabled() {
        // Given
        Long shoppingCartId = 123L;

        // Mock behavior
        when(repository.existsById(shoppingCartId)).thenReturn(true);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setDisabled(true); // Simulate disabled shopping cart
        when(repository.getReferenceById(shoppingCartId)).thenReturn(shoppingCart);

        // When/Then
        assertThrows(ValidationException.class, () -> {
            validationShoppingCartAlreadyDisabled.validation(shoppingCartId);
        });
    }

    @Test
    public void testValidationShoppingCartNotDisabled() {
        // Given
        Long shoppingCartId = 456L;

        // Mock behavior
        when(repository.existsById(shoppingCartId)).thenReturn(true);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setDisabled(false); // Simulate shopping cart not disabled
        when(repository.getReferenceById(shoppingCartId)).thenReturn(shoppingCart);

        // When/Then
        assertDoesNotThrow(() -> {
            validationShoppingCartAlreadyDisabled.validation(shoppingCartId);
        });
    }

    @Test
    public void testValidationShoppingCartDoesNotExist() {
        // Given
        Long shoppingCartId = 789L;

        // Mock behavior
        when(repository.existsById(shoppingCartId)).thenReturn(false);

        // When/Then
        assertDoesNotThrow(() -> {
            validationShoppingCartAlreadyDisabled.validation(shoppingCartId);
        });
    }
}
