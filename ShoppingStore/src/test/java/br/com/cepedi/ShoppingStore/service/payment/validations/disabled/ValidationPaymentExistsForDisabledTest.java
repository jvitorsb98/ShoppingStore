package br.com.cepedi.ShoppingStore.service.payment.validations.disabled;

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
public class ValidationPaymentExistsForDisabledTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private ValidationPaymentExistsForDisabled validationPaymentExistsForDisabled;

    private final Faker faker = new Faker();

    @Test
    public void testValidation_PaymentExists() {
        Long paymentId = faker.number().randomNumber();

        when(paymentRepository.existsById(paymentId)).thenReturn(true);

        assertDoesNotThrow(() -> {
            validationPaymentExistsForDisabled.validation(paymentId);
        });

        verify(paymentRepository, times(1)).existsById(paymentId);
    }

    @Test
    public void testValidation_PaymentDoesNotExist() {
        Long paymentId = faker.number().randomNumber();

        when(paymentRepository.existsById(paymentId)).thenReturn(false);

        assertThrows(ValidationException.class, () -> {
            validationPaymentExistsForDisabled.validation(paymentId);
        });

        verify(paymentRepository, times(1)).existsById(paymentId);
    }
}


