package br.com.cepedi.ShoppingStore.security.controller;

import br.com.cepedi.ShoppingStore.security.service.AuthService;
import br.com.cepedi.ShoppingStore.security.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ActivatedUserControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private ActivatedUserController activatedUserController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testActivateAccount_ValidToken() {
        // Arrange
        String validToken = "validToken";
        when(tokenService.isValidToken(validToken)).thenReturn(true);
        doNothing().when(authService).activateUser(validToken);

        // Act
        ResponseEntity<String> response = activatedUserController.activateAccount(validToken);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User account activated successfully.", response.getBody());
        verify(tokenService, times(1)).isValidToken(validToken);
        verify(authService, times(1)).activateUser(validToken);
    }

    @Test
    void testActivateAccount_InvalidToken() {
        // Arrange
        String invalidToken = "invalidToken";
        when(tokenService.isValidToken(invalidToken)).thenReturn(false);

        // Act
        ResponseEntity<String> response = activatedUserController.activateAccount(invalidToken);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Token invalid", response.getBody());
        verify(tokenService, times(1)).isValidToken(invalidToken);
        verify(authService, never()).activateUser(anyString());
    }

    @Test
    void testActivateAccount_Exception() {
        // Arrange
        String token = "exceptionToken";
        when(tokenService.isValidToken(token)).thenReturn(true);
        doThrow(new RuntimeException("Failed to activate user")).when(authService).activateUser(token);

        // Act
        ResponseEntity<String> response = activatedUserController.activateAccount(token);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to activate user account.", response.getBody());
        verify(tokenService, times(1)).isValidToken(token);
        verify(authService, times(1)).activateUser(token);
    }
}
