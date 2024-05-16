package br.com.cepedi.ShoppingStore.security.service;

import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.TokenRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Value;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.Random.class)
public class TokenServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private DecodedJWT decodedJWT;

    @InjectMocks
    private TokenService tokenService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(tokenService, "secret", "testSecret123");

    }

    @Test
    @DisplayName("Test generateToken")
    void testGenerateToken() {
        User user = new User();
        user.setLogin("john_doe");
        user.setId(1L);
        user.setEmail("john.doe@example.com");

        String token = tokenService.generateToken(user);

        assertNotNull(token);
        assertTrue(tokenService.isValidToken(token));

        String email = tokenService.getEmailByToken(token);
        assertEquals(user.getEmail(), email);

        String subject = tokenService.getSubject(token);
        assertEquals(user.getLogin(), subject);
    }

    @Test
    @DisplayName("Test generateTokenRecoverPassword")
    void testGenerateTokenRecoverPassword() {
        User user = new User();
        user.setLogin("john_doe");
        user.setId(1L);
        user.setEmail("john.doe@example.com");

        String token = tokenService.generateTokenRecoverPassword(user);

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

        // Act
        boolean isValid = tokenService.isValidToken(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Test revokeToken")
    void testRevokeToken() {
        String token = "token_to_revoke";

        tokenService.revokeToken(token);
        assertFalse(tokenService.isValidToken(token));
    }

    @Test
    @DisplayName("Test getEmailByToken")
    void testGetEmailByToken() {
        String email = "john.doe@example.com";
        String token = JWT.create()
                .withClaim("email", email)
                .sign(Algorithm.HMAC256("secret"));

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
    void getSubject_InvalidToken_ThrowsException() {
        // Arrange
        String invalidToken = "invalidToken";

        // Act & Assert
        assertThrows(JWTVerificationException.class, () -> {
            tokenService.getSubject(invalidToken);
        });
    }


    @Test
    @DisplayName("Test generateTokenForActivatedEmail")
    void testGenerateTokenForActivatedEmail() {
        User user = new User();
        user.setLogin("john_doe");
        user.setId(1L);
        user.setEmail("john.doe@example.com");

        String token = tokenService.generateTokenForActivatedEmail(user);

        assertNotNull(token);
        assertTrue(tokenService.isValidToken(token));

        String email = tokenService.getEmailByToken(token);
        assertEquals(user.getEmail(), email);

        String retrievedSubject = tokenService.getSubject(token);
        assertEquals(user.getLogin(), retrievedSubject);
    }

}


