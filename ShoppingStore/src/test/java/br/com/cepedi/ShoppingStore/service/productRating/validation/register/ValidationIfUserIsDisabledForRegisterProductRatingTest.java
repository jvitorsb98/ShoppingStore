package br.com.cepedi.ShoppingStore.service.productRating.validation.register;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValidationIfUserIsDisabledForRegisterProductRatingTest {

    @InjectMocks
    private ValidationIfUserIsDisabledForRegisterProductRating validator;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testValidationWithEnabledUser() {
        long userId = 1L;
        User user = new User();
        user.setActivated(true);
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.getReferenceById(userId)).thenReturn(user);

        // Verifica se nenhuma exceção é lançada quando um usuário ativado é fornecido
        assertDoesNotThrow(() -> validator.validation(new DataRegisterProductRating(1L, null, null, userId)));
    }

    @Test
    public void testValidationWithDisabledUser() {
        long userId = 2L;
        User user = new User();
        user.setActivated(false);
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.getReferenceById(userId)).thenReturn(user);

        // Verifica se a exceção é lançada quando um usuário desativado é fornecido
        assertThrows(ValidationException.class, () -> validator.validation(new DataRegisterProductRating(1L, null, null, userId)));
    }

    @Test
    public void testValidationWithNonExistingUser() {
        long userId = 3L;
        when(userRepository.existsById(userId)).thenReturn(false);

        // Verifica se nenhuma exceção é lançada quando um usuário inexistente é fornecido
        assertDoesNotThrow(() -> validator.validation(new DataRegisterProductRating(1L, null, null, userId)));
    }
}

