package br.com.cepedi.ShoppingStore.security.controller;

import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.model.records.details.DadosTokenJWT;
import br.com.cepedi.ShoppingStore.security.model.records.input.DataAuth;
import br.com.cepedi.ShoppingStore.security.service.TokenService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("LoginController Tests")
@TestMethodOrder(MethodOrderer.Random.class)
public class LoginControllerTest {

    @Mock
    private AuthenticationManager manager;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    @DisplayName("Set up")
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test efetuarLogin with successful authentication")
    void testEfetuarLogin_SuccessfulAuthentication() {
        // Mocking the expected behavior of Authentication and User
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("testUser");
        when(user.getActivated()).thenReturn(true);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);

        when(manager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(tokenService.generateToken(user)).thenReturn("testToken");

        // Calling the method directly
        ResponseEntity<Object> response = loginController.efetuarLogin(new DataAuth("testUser", "testPassword"));

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifying the interactions with AuthenticationManager and TokenService
        verify(manager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService, times(1)).generateToken(user);
    }

    @Test
    @DisplayName("Test efetuarLogin with user not activated")
    void testEfetuarLogin_UserNotActivated() {
        // Mocking the expected behavior of Authentication and User
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("testUser");
        when(user.getActivated()).thenReturn(false);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);

        when(manager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        // Calling the method directly
        ResponseEntity<Object> response = loginController.efetuarLogin(new DataAuth("testUser", "testPassword"));

        // Verifying the response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User is not activated", response.getBody());

        // Verifying the interactions with AuthenticationManager and ensuring TokenService is not called
        verify(manager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService, times(0)).generateToken(user);
    }
}
