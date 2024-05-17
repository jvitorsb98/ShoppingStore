package br.com.cepedi.ShoppingStore.security.repository;

import br.com.cepedi.ShoppingStore.security.model.entitys.Token;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;

    @AfterEach
    public void deleteAllTokens() {
        tokenRepository.deleteAll();
    }

    @Test
    @DisplayName("Test save token")
    public void testSaveToken() {
        // Given
        Token token = new Token();
        token.setId(1L);
        token.setToken("TOKEN");

        // When
        Token savedToken = tokenRepository.save(token);

        // Then
        assertNotNull(savedToken.getId());
        assertEquals(token.getId(), 1L);
        assertEquals(token.getToken(),"TOKEN");
    }

    @Test
    @DisplayName("Test find id")
    public void testFindId() {
        // Given
        Token token = new Token();
        token.setToken("TOKEN");

        // When
        Token savedToken = tokenRepository.save(token);

        Token token2 = tokenRepository.getReferenceById(savedToken.getId());

        // Then
        assertNotNull(savedToken.getId());
        assertEquals(token.getId(), token2.getId());
        assertEquals(token.getToken(),token2.getToken());
    }

}



