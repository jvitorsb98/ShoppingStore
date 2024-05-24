package br.com.cepedi.ShoppingStore.service.payment.validations.disabled;

import br.com.cepedi.ShoppingStore.model.entitys.Payment;
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
public class ValidationPaymentAlreadyDisabledTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private ValidationPaymentAlreadyDisabled validationPaymentAlreadyDisabled;

    private final Faker faker = new Faker();

    @Test
    public void testValidation_PaymentIsDisabled() {
        Long paymentId = faker.number().randomNumber();
        Payment disabledPayment = new Payment();
        disabledPayment.setId(paymentId);
        disabledPayment.setDisabled(true);

        when(paymentRepository.existsById(paymentId)).thenReturn(true);
        when(paymentRepository.getReferenceById(paymentId)).thenReturn(disabledPayment);

        assertThrows(ValidationException.class, () -> {
            validationPaymentAlreadyDisabled.validation(paymentId);
        });

        verify(paymentRepository, times(1)).existsById(paymentId);
        verify(paymentRepository, times(1)).getReferenceById(paymentId);
    }

    @Test
    public void testValidation_PaymentIsEnabled() {
        Long paymentId = faker.number().randomNumber();
        Payment enabledPayment = new Payment();
        enabledPayment.setId(paymentId);
        enabledPayment.setDisabled(false);

        when(paymentRepository.existsById(paymentId)).thenReturn(true);
        when(paymentRepository.getReferenceById(paymentId)).thenReturn(enabledPayment);

        assertDoesNotThrow(() -> {
            validationPaymentAlreadyDisabled.validation(paymentId);
        });

        verify(paymentRepository, times(1)).existsById(paymentId);
        verify(paymentRepository, times(1)).getReferenceById(paymentId);
    }

    @Test
    public void testValidation_PaymentDoesNotExist() {
        Long paymentId = faker.number().randomNumber();

        when(paymentRepository.existsById(paymentId)).thenReturn(false);

        assertDoesNotThrow(() -> {
            validationPaymentAlreadyDisabled.validation(paymentId);
        });

        verify(paymentRepository, times(1)).existsById(paymentId);
        verify(paymentRepository, never()).getReferenceById(paymentId);
    }
}


