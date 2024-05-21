package br.com.cepedi.ShoppingStore.security.service;

import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.model.records.details.DataDetailsRegisterUser;
import br.com.cepedi.ShoppingStore.security.model.records.input.DataRegisterUser;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.eq;

import static org.mockito.Mockito.*;

@DisplayName("AuthService Tests")
@TestMethodOrder(MethodOrderer.Random.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    @DisplayName("Set up")
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test loadUserByUsername")
    void testLoadUserByUsername() {
        String login = "john_doe";
        User user = new User();
        user.setLogin(login);

        when(userRepository.findByLogin(login)).thenReturn(user);

        UserDetails userDetails = authService.loadUserByUsername(login);

        assertEquals(user, userDetails);
    }

    @Test
    @DisplayName("Test register")
    void testRegister() {
        DataRegisterUser dataRegisterUser = new DataRegisterUser("john_doe", "john.doe@example.com", "jhon doe", "Password123*");
        User user = new User(dataRegisterUser, passwordEncoder);

        when(userRepository.save(user)).thenReturn(user);

        DataDetailsRegisterUser dataDetailsRegisterUser = authService.register(dataRegisterUser);

        assertNotNull(dataDetailsRegisterUser);
        assertEquals(user.getLogin(), dataDetailsRegisterUser.login());
        assertEquals(user.getEmail(), dataDetailsRegisterUser.email());
    }

    @Test
    @DisplayName("Test activateUser")
    void testActivateUser() {
        String email = "john.doe@example.com";
        DataRegisterUser dataRegisterUser = new DataRegisterUser("john_doe", email, "jhon doe", "Password123*");
        User user = new User(dataRegisterUser, passwordEncoder);
        user.setEmail(email); // Setting the user's email

        String secret = "your_secret_key";
        String token = JWT.create()
                .withSubject(email)
                .withExpiresAt(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .sign(Algorithm.HMAC256(secret));

        // Configuring the mock to return the user when the correct email is passed as a parameter
        when(userRepository.findUserByEmail(eq(email))).thenReturn(user);
        when(userRepository.findUserByEmail(isNull())).thenReturn(user);

        authService.activateUser(token);

        assertTrue(user.getActivated());
        verify(userRepository).save(user);
    }
}