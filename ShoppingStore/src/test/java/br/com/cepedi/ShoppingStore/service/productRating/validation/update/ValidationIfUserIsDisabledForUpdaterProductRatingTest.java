package br.com.cepedi.ShoppingStore.service.productRating.validation.update;

import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidationIfUserIsDisabledForUpdaterProductRatingTest {

    @InjectMocks
    private ValidationIfUserIsDisabledForUpdaterProductRating validator;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testValidationWithActivatedUser() {
        long userId = 1L;
        User user = new User();
        user.setActivated(true);
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.getReferenceById(userId)).thenReturn(user);

       
        assertDoesNotThrow(() -> validator.validation(new DataUpdateProductRating(1L,null, null, null, userId)));
    }

    @Test
    public void testValidationWithDisabledUser() {
        long userId = 2L;
        User user = new User();
        user.setActivated(false);
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.getReferenceById(userId)).thenReturn(user);

 
        assertThrows(ValidationException.class, () -> validator.validation(new DataUpdateProductRating(1L,null, null, null, userId)));
    }

    @Test
    public void testValidationWithNonExistingUser() {
        long userId = 3L;
        when(userRepository.existsById(userId)).thenReturn(false);

     
        assertDoesNotThrow(() -> validator.validation(new DataUpdateProductRating(1L,null, null, null, userId)));
    }
}
