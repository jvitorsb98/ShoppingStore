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

import br.com.cepedi.ShoppingStore.model.records.payments.input.DataRegisterPayment;
import br.com.cepedi.ShoppingStore.model.enums.PaymentType;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import jakarta.validation.ValidationException;

@ExtendWith(MockitoExtension.class)
class ValidationShoppingCartExistsForRegisterPaymentTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @InjectMocks
    private ValidationShoppingCartExistsForRegisterPayment validation;

    private Faker faker;
    private long shoppingCartId;
    private DataRegisterPayment dataRegisterPayment;

    @BeforeEach
    void setup() {
        faker = new Faker();
        shoppingCartId = faker.number().randomNumber();
        dataRegisterPayment = new DataRegisterPayment(shoppingCartId, PaymentType.CREDIT_CARD);
    }

    @Test
    void testValidation_ShoppingCartDoesNotExist() {
        // Arrange
        when(shoppingCartRepository.existsById(shoppingCartId)).thenReturn(false);

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            validation.validation(dataRegisterPayment);
        });

        assertEquals("The required Shopping Cart Not Exists", exception.getMessage());
    }

    @Test
    void testValidation_ShoppingCartExists() {
        // Arrange
        when(shoppingCartRepository.existsById(shoppingCartId)).thenReturn(true);

        // Act & Assert
        assertDoesNotThrow(() -> validation.validation(dataRegisterPayment));
    }
}

