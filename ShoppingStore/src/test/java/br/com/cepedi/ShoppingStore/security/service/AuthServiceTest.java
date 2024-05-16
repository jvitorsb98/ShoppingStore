package br.com.cepedi.ShoppingStore.security.service;

import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.model.records.details.DataDetailsRegisterUser;
import br.com.cepedi.ShoppingStore.security.model.records.input.DataRegisterUser;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.mockito.Mockito.eq;



import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;



    @InjectMocks
    private AuthService authService;

    @BeforeEach
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
        DataRegisterUser dataRegisterUser = new DataRegisterUser("john_doe", "john.doe@example.com","jhon doe", "Password123*");
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
        user.setEmail(email); // Definindo o e-mail do usuário

        String secret = "your_secret_key";
        String token = JWT.create()
                .withSubject(email)
                .withExpiresAt(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .sign(Algorithm.HMAC256(secret));



        // Configurando o mock para retornar o usuário quando o e-mail correto for passado como parâmetro
        when(userRepository.findUserByEmail(eq(email))).thenReturn(user);
        when(userRepository.findUserByEmail(isNull())).thenReturn(user);

        authService.activateUser(token);

        assertTrue(user.getActivated());
        verify(userRepository).save(user);
    }
}
