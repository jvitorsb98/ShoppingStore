package br.com.cepedi.ShoppingStore.security.service;

import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.Random.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getUserActivatedByEmail")
    void testGetUserActivatedByEmail() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findUserByEmail(email)).thenReturn(user);

        User retrievedUser = userService.getUserActivatedByEmail(email);

        assertEquals(user, retrievedUser);
    }

    @Test
    @DisplayName("Test updatePassword")
    void testUpdatePassword() {
        String email = "test@example.com";
        String newPassword = "newPassword";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findUserByEmail(email)).thenReturn(user);
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedPassword");

        userService.updatePassword(email, newPassword);

        verify(userRepository).findUserByEmail(email);
        verify(passwordEncoder).encode(newPassword);
        verify(userRepository).saveAndFlush(user);
    }

    @Test
    @DisplayName("Test updatePassword with user not found")
    void testUpdatePasswordUserNotFound() {
        String email = "nonexistent@example.com";
        String newPassword = "newPassword";

        when(userRepository.findUserByEmail(email)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> userService.updatePassword(email, newPassword));

        verify(userRepository).findUserByEmail(email);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(passwordEncoder);
    }

}
