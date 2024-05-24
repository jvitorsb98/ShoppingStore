package br.com.cepedi.ShoppingStore.service.payment.validations.register;

import br.com.cepedi.ShoppingStore.model.enums.PaymentType;
import br.com.cepedi.ShoppingStore.model.records.payments.input.DataRegisterPayment;
import br.com.cepedi.ShoppingStore.repository.PaymentRepository;
import com.github.javafaker.Faker;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValidationShoppingCartExistsForRegisterPaymentTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private ValidationIfExistsPaymentTrueForShoppingCart validationIfExistsPaymentTrueForShoppingCart;

    private final Faker faker = new Faker();

    @Test
    public void testValidation_ShoppingCartContainsActivePayment() {
        Long shoppingCartId = faker.number().randomNumber();
        PaymentType paymentType = PaymentType.CREDIT_CARD;

        when(paymentRepository.existsEnabledPaymentByShoppingCartId(shoppingCartId)).thenReturn(true);

        assertThrows(ValidationException.class, () -> {
            validationIfExistsPaymentTrueForShoppingCart.validation(new DataRegisterPayment(shoppingCartId, paymentType));
        });

        verify(paymentRepository, times(1)).existsEnabledPaymentByShoppingCartId(shoppingCartId);
    }

    @Test
    public void testValidation_ShoppingCartDoesNotContainActivePayment() {
        Long shoppingCartId = faker.number().randomNumber();
        PaymentType paymentType = PaymentType.DEBIT_CARD;

        when(paymentRepository.existsEnabledPaymentByShoppingCartId(shoppingCartId)).thenReturn(false);

        assertDoesNotThrow(() -> {
            validationIfExistsPaymentTrueForShoppingCart.validation(new DataRegisterPayment(shoppingCartId, paymentType));
        });

        verify(paymentRepository, times(1)).existsEnabledPaymentByShoppingCartId(shoppingCartId);
    }
}

