package br.com.cepedi.ShoppingStore.service.purchase.PurharseService.validations.register.shoppingCart;

import br.com.cepedi.ShoppingStore.model.records.shoppingCart.input.DataRegisterShoppingCart;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserExistsForRegisterShoppingCartTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserExistsForRegisterShoppingCart userExistsForRegisterShoppingCart;

    private Faker faker;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testUserExists() {
        Long userId = faker.number().randomNumber();
        DataRegisterShoppingCart data = new DataRegisterShoppingCart(userId);

        when(userRepository.existsById(userId)).thenReturn(true);

        userExistsForRegisterShoppingCart.validation(data);

        verify(userRepository).existsById(userId);
    }

    @Test
    public void testUserDoesNotExist() {
        Long userId = faker.number().randomNumber();
        DataRegisterShoppingCart data = new DataRegisterShoppingCart(userId);

        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(ValidationException.class, () -> {
            userExistsForRegisterShoppingCart.validation(data);
        });

        verify(userRepository).existsById(userId);
    }
}
