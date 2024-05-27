package br.com.cepedi.ShoppingStore.service.payment.validations.register;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.model.records.payments.input.DataRegisterPayment;
import br.com.cepedi.ShoppingStore.model.enums.PaymentType;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import jakarta.validation.ValidationException;

@ExtendWith(MockitoExtension.class)
class ValidationShoppingCartNotDisabledForRegisterPaymentTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @InjectMocks
    private ValidationShoppingCartNotDisabledForRegisterPayment validation;

    private Faker faker;
    private long shoppingCartId;
    private DataRegisterPayment dataRegisterPayment;
    private ShoppingCart shoppingCart;

    @BeforeEach
    void setup() {
        faker = new Faker();
        shoppingCartId = faker.number().randomNumber();
        dataRegisterPayment = new DataRegisterPayment(shoppingCartId, PaymentType.CREDIT_CARD);
        shoppingCart = new ShoppingCart();
        shoppingCart.setId(shoppingCartId);
    }

    @Test
    void testValidation_ShoppingCartIsDisabled() {
        // Arrange
        shoppingCart.setDisabled(true); // Carrinho de compras desativado
        when(shoppingCartRepository.existsById(shoppingCartId)).thenReturn(true);
        when(shoppingCartRepository.getReferenceById(shoppingCartId)).thenReturn(shoppingCart);

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            validation.validation(dataRegisterPayment);
        });

        assertEquals("The required Shopping Cart is disabled", exception.getMessage());
    }

    @Test
    void testValidation_ShoppingCartIsNotDisabled() {
        // Arrange
        shoppingCart.setDisabled(false); // Carrinho de compras não está desativado
        when(shoppingCartRepository.existsById(shoppingCartId)).thenReturn(true);
        when(shoppingCartRepository.getReferenceById(shoppingCartId)).thenReturn(shoppingCart);

        // Act & Assert
        assertDoesNotThrow(() -> validation.validation(dataRegisterPayment));
    }

    @Test
    void testValidation_ShoppingCartDoesNotExist() {
        // Arrange
        when(shoppingCartRepository.existsById(shoppingCartId)).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> validation.validation(dataRegisterPayment));
    }
}

