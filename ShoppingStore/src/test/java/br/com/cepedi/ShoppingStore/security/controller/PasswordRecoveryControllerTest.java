package br.com.cepedi.ShoppingStore.security.controller;

import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.model.records.input.DataRequestResetPassword;
import br.com.cepedi.ShoppingStore.security.model.records.input.DataResetPassword;
import br.com.cepedi.ShoppingStore.security.service.EmailService;
import br.com.cepedi.ShoppingStore.security.service.TokenService;
import br.com.cepedi.ShoppingStore.security.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("PasswordRecoveryController Tests")
@TestMethodOrder(MethodOrderer.Random.class)
class PasswordRecoveryControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private TokenService tokenService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private PasswordRecoveryController passwordRecoveryController;

    @BeforeEach
    @DisplayName("Set up")
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test resetPasswordRequest with user found")
    void testResetPasswordRequest_UserFound() {
        // Arrange
        DataRequestResetPassword dataResetPassword = new DataRequestResetPassword("test@example.com");
        User user = new User();
        when(userService.getUserActivatedByEmail(dataResetPassword.email())).thenReturn(user);
        when(tokenService.generateTokenRecoverPassword(user)).thenReturn("testToken");

        // Act
        ResponseEntity<String> response = passwordRecoveryController.resetPasswordRequest(dataResetPassword);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("A password reset email has been sent to test@example.com", response.getBody());
        verify(userService, times(1)).getUserActivatedByEmail(dataResetPassword.email());
        verify(tokenService, times(1)).generateTokenRecoverPassword(user);
    }

    @Test
    @DisplayName("Test resetPasswordRequest with user not found")
    void testResetPasswordRequest_UserNotFound() {
        // Arrange
        DataRequestResetPassword dataResetPassword = new DataRequestResetPassword("test@example.com");
        when(userService.getUserActivatedByEmail(dataResetPassword.email())).thenReturn(null);

        // Act
        ResponseEntity<String> response = passwordRecoveryController.resetPasswordRequest(dataResetPassword);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("E-mail not found", response.getBody());
        verify(userService, times(1)).getUserActivatedByEmail(dataResetPassword.email());
        verify(tokenService, never()).generateTokenRecoverPassword(any(User.class));
    }

    @Test
    @DisplayName("Test resetPassword with valid token")
    void testResetPassword_ValidToken() {
        // Arrange
        DataResetPassword dataResetPassword = new DataResetPassword("testToken", "newPassword");
        when(tokenService.isValidToken(dataResetPassword.token())).thenReturn(true);
        when(tokenService.getEmailByToken(dataResetPassword.token())).thenReturn("test@example.com");
        doNothing().when(userService).updatePassword(anyString(), anyString());
        doNothing().when(tokenService).revokeToken(anyString());

        // Act
        ResponseEntity<String> response = passwordRecoveryController.resetPassword(dataResetPassword);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password updated successfully", response.getBody());
        verify(tokenService, times(1)).isValidToken(dataResetPassword.token());
        verify(tokenService, times(1)).getEmailByToken(dataResetPassword.token());
        verify(userService, times(1)).updatePassword(anyString(), anyString());
        verify(tokenService, times(1)).revokeToken(anyString());
    }

    @Test
    @DisplayName("Test resetPassword with invalid token")
    void testResetPassword_InvalidToken() {
        // Arrange
        DataResetPassword dataResetPassword = new DataResetPassword("invalidToken", "newPassword");
        when(tokenService.isValidToken(dataResetPassword.token())).thenReturn(false);

        // Act
        ResponseEntity<String> response = passwordRecoveryController.resetPassword(dataResetPassword);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid or expired token", response.getBody());
        verify(tokenService, times(1)).isValidToken(dataResetPassword.token());
        verify(tokenService, never()).getEmailByToken(anyString());
        verify(userService, never()).updatePassword(anyString(), anyString());
        verify(tokenService, never()).revokeToken(anyString());
    }
}