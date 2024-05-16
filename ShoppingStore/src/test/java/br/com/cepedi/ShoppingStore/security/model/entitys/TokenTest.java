package br.com.cepedi.ShoppingStore.security.model.entitys;

import br.com.cepedi.ShoppingStore.security.model.records.input.DataRegisterToken;
import org.junit.jupiter.api.*;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.Random.class)
@DisplayName("Token Test - Random Order")
class TokenTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
    }

    @Test
    @DisplayName("Test Token constructor with DataRegisterToken and User")
    void testTokenConstructor() {
        Instant expireDate = Instant.now().plusSeconds(3600); // Expira em 1 hora
        DataRegisterToken dataRegisterToken = new DataRegisterToken("test_token", user.getId(), expireDate);
        Token token = new Token(dataRegisterToken, user);

        assertEquals("test_token", token.getToken());
        assertEquals(user, token.getUser());
        assertEquals(expireDate, token.getExpireDate());
        assertFalse(token.getDisabled());
    }

    @Test
    @DisplayName("Test disabling token")
    void testDisablingToken() {
        Instant expireDate = Instant.now().plusSeconds(3600); // Expira em 1 hora
        DataRegisterToken dataRegisterToken = new DataRegisterToken("test_token", user.getId(), expireDate);
        Token token = new Token(dataRegisterToken, user);
        assertFalse(token.getDisabled());

        token.disabled();
        assertTrue(token.getDisabled());
    }

    @Test
    @DisplayName("Test setters")
    void testSetters() {
        Token token = new Token();
        token.setId(1L);
        token.setToken("new_token");
        token.setUser(user);
        Instant expectedExpireDate = Instant.now().plusSeconds(3600);
        token.setExpireDate(expectedExpireDate);
        token.setDisabled(true);

        assertEquals(token.getId(),1L);
        assertEquals("new_token", token.getToken());
        assertEquals(user, token.getUser());
        assertEquals(expectedExpireDate, token.getExpireDate());
        assertTrue(token.getDisabled());
    }

    @Test
    @DisplayName("Test equals and hashCode based on id")
    void testEqualsAndHashCode() {
        Token token1 = new Token();
        token1.setId(1L);

        Token token2 = new Token();
        token2.setId(1L);

        Token token3 = new Token();
        token3.setId(2L);

        assertEquals(token1, token2); // Deve ser igual
        assertEquals(token1.hashCode(), token2.hashCode()); // hashCode deve ser igual

        assertNotEquals(token1, token3); // Deve ser diferente
        assertNotEquals(token1.hashCode(), token3.hashCode()); // hashCode deve ser diferente
        assertTrue(token1.equals(token2));
        assertFalse(token1.equals(token3));
    }

}
