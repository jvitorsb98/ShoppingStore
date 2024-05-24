package br.com.cepedi.ShoppingStore.service.payment.validations.register;

import com.github.javafaker.Faker;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.model.enums.PaymentType;
import br.com.cepedi.ShoppingStore.model.records.payments.input.DataRegisterPayment;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import jakarta.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ValidationShoppingCartNotDisabledForRegisterPaymentTest {

    @Autowired
    private ValidationShoppingCartNotDisabledForRegisterPayment validation;

    @MockBean
    private ShoppingCartRepository shoppingCartRepository;

    private final Faker faker = new Faker();

    @Test
    public void testValidation_ShoppingCartIsDisabled() {
        long shoppingCartId = faker.number().randomNumber();

        when(shoppingCartRepository.existsById(shoppingCartId)).thenReturn(true);
        ShoppingCart disabledShoppingCart = new ShoppingCart();
        disabledShoppingCart.setDisabled(true);
        when(shoppingCartRepository.getReferenceById(shoppingCartId)).thenReturn(disabledShoppingCart);

        assertThrows(ValidationException.class, () -> validation.validation(new DataRegisterPayment(shoppingCartId, PaymentType.DEBIT_CARD)));
    }
}
