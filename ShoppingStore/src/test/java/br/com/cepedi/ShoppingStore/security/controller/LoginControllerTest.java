package br.com.cepedi.ShoppingStore.security.controller;

import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.model.records.input.DataAuth;
import br.com.cepedi.ShoppingStore.security.model.records.input.DataRegisterUser;
import br.com.cepedi.ShoppingStore.security.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    @Mock
    private AuthenticationManager manager;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEfetuarLogin_SuccessfulAuthentication() {
        // Mocking the expected behavior of Authentication and User
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("testUser");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);

        when(manager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(tokenService.generateToken(user)).thenReturn("testToken");

        // Calling the method directly
        ResponseEntity response = loginController.efetuarLogin(new DataAuth("testUser", "testPassword"));

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifying the interactions with AuthenticationManager and TokenService
        verify(manager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService, times(1)).generateToken(user);
    }
}
