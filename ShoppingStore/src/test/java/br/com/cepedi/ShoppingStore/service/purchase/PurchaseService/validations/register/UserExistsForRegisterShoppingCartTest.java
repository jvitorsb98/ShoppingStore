package br.com.cepedi.ShoppingStore.service.purchase.PurchaseService.validations.register;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import br.com.cepedi.ShoppingStore.service.purchase.PurharseService.validations.register.shoppingCart.UserExistsForRegisterShoppingCart;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.cepedi.ShoppingStore.model.records.shoppingCart.input.DataRegisterShoppingCart;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;

public class UserExistsForRegisterShoppingCartTest {

    @InjectMocks
    private UserExistsForRegisterShoppingCart userExistsForRegisterShoppingCart;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidation_UserExists() {
        // Dados de teste
        DataRegisterShoppingCart data = new DataRegisterShoppingCart(1L);

        // Comportamento simulado do UserRepository
        when(userRepository.existsById(1L)).thenReturn(true);

        // Execução do teste
        userExistsForRegisterShoppingCart.validation(data);

        // Verificação do comportamento
        verify(userRepository).existsById(1L);
    }

    @Test
    public void testValidation_UserNotExists() {
        // Dados de teste
        DataRegisterShoppingCart data = new DataRegisterShoppingCart(2L);

        // Comportamento simulado do UserRepository
        when(userRepository.existsById(2L)).thenReturn(false); // Simula que o usuário não existe

        // Execução do teste e verificação da exceção
        assertThrows(ValidationException.class, () -> userExistsForRegisterShoppingCart.validation(data));
    }

}
