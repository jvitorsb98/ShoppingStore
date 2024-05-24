package br.com.cepedi.ShoppingStore.service.payment.validations.register;

import br.com.cepedi.ShoppingStore.model.records.payments.input.DataRegisterPayment;
import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.model.enums.PaymentType;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationShoppingCartNotDisabledForRegisterPaymentTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @InjectMocks
    private ValidationShoppingCartNotDisabledForRegisterPayment validation;

    @Test
    void testValidation_ShoppingCartIsDisabled() {
        // Configurar o cenário do teste
        long shoppingCartId = 1L;
        DataRegisterPayment dataRegisterPayment = new DataRegisterPayment(shoppingCartId, PaymentType.CREDIT_CARD);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(shoppingCartId);
        shoppingCart.setDisabled(true); // Carrinho de compras desativado

        when(shoppingCartRepository.existsById(shoppingCartId)).thenReturn(true);
        when(shoppingCartRepository.getReferenceById(shoppingCartId)).thenReturn(shoppingCart);

        // Verificar se uma exceção é lançada
        assertThrows(ValidationException.class, () -> validation.validation(dataRegisterPayment));
    }

    @Test
    void testValidation_ShoppingCartIsNotDisabled() {
        // Configurar o cenário do teste
        long shoppingCartId = 1L;
        DataRegisterPayment dataRegisterPayment = new DataRegisterPayment(shoppingCartId, PaymentType.CREDIT_CARD);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(shoppingCartId);
        shoppingCart.setDisabled(false); // Carrinho de compras não está desativado

        // Configurar mock do shoppingCartRepository para retornar o shoppingCart válido
        when(shoppingCartRepository.existsById(shoppingCartId)).thenReturn(true);
        when(shoppingCartRepository.getReferenceById(shoppingCartId)).thenReturn(shoppingCart);

        // Verificar se nenhuma exceção é lançada
        assertDoesNotThrow(() -> validation.validation(dataRegisterPayment)); // Não deve lançar exceção
    }



}
