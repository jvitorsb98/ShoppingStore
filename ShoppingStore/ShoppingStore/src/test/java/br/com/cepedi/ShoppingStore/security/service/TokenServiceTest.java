package br.com.cepedi.ShoppingStore.security.service;

import br.com.cepedi.ShoppingStore.security.model.entitys.Token;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.TokenRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.Random.class)
@DisplayName("TokenService Tests")
public class TokenServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    @DisplayName("Set up")
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(tokenService, "secret", "testSecret123");
        tokenRepository.deleteAll();
    }





    @Test
    @DisplayName("Test generateTokenRecoverPassword")
    void testGenerateTokenRecoverPassword() {
        User user = new User();
        user.setLogin("john_doe");
        user.setId(1L);
        user.setEmail("john.doe@example.com");

        String token = tokenService.generateTokenRecoverPassword(user);

        // Mocking the repository to return a valid token
        Token mockToken = new Token();
        mockToken.setDisabled(false); // Ensure that the token is not disabled
        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(mockToken));

        assertNotNull(token);
        assertTrue(tokenService.isValidToken(token));

        String email = tokenService.getEmailByToken(token);
        assertEquals(user.getEmail(), email);

        String subject = tokenService.getSubject(token);
        assertEquals(user.getLogin(), subject);
    }

    @Test
    @DisplayName("Test isValidToken")
    void isValidToken_ValidToken_ReturnsTrue() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setLogin("testuser");
        user.setEmail("test@example.com");
        String token = tokenService.generateToken(user);

        // Mocking the repository to return a valid token
        Token mockToken = new Token();
        mockToken.setDisabled(false); // Ensure that the token is not disabled
        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(mockToken));

        // Act
        boolean isValid = tokenService.isValidToken(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Test revokeToken")
    void testRevokeToken() {
        // Given
        String token = "token_to_revoke";
        Token mockToken = new Token(); // Mock Token object
        mockToken.setDisabled(false); // Ensure that the token is not disabled initially

        // Stubbing the behavior of findByToken method to return a mockToken when called with token
        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(mockToken));

        // When
        tokenService.revokeToken(token);

        // Then
        assertTrue(mockToken.getDisabled()); // Ensure that the token is disabled after revocation
        verify(tokenRepository, times(1)).findByToken(token); // Verify that findByToken method is called once with token
        verify(tokenRepository, times(1)).save(mockToken); // Verify that save method is called once with the mockToken
    }

    @Test
    @DisplayName("Test getEmailByToken")
    void testGetEmailByToken() {
        String email = "john.doe@example.com";
        String token = JWT.create()
                .withClaim("email", email)
                .sign(Algorithm.HMAC256("testSecret123"));

        String retrievedEmail = tokenService.getEmailByToken(token);
        assertEquals(email, retrievedEmail);
    }

    @Test
    @DisplayName("Test getSubject")
    void getSubject_ValidToken_ReturnsSubject() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setLogin("testuser");
        user.setEmail("test@example.com");
        String token = tokenService.generateToken(user);

        // Act
        String subject = tokenService.getSubject(token);

        // Assert
        assertEquals("testuser", subject);
    }

    @Test
    @DisplayName("Test getSubject with Invalid Token")
    void getSubject_InvalidToken_ThrowsException() {
        // Arrange
        String invalidToken = "invalidToken";

        // Act & Assert
        assertThrows(JWTVerificationException.class, () -> {
            tokenService.getSubject(invalidToken);
        });
    }



}
