package br.com.cepedi.ShoppingStore.repository;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.security.config.TestConfig;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.model.records.input.DataRegisterUser;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("ShoppingCart Repository Test - Random Order")
public class ShoppingCartRepositoryTest {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void delete(){
        userRepository.deleteAll();
        shoppingCartRepository.deleteAll();
    }
    @Test
    @DisplayName("Test Save ShoppingCart")
    public void testSaveShoppingCart() {
        // Given
        String login = "testuser";
        String email = "testuser@example.com";
        String name = "Test User";
        String password = "password123*";
        DataRegisterUser dataRegisterUser = new DataRegisterUser(login, email, name, password);

        User userTest = userRepository.save(new User(dataRegisterUser, TestConfig.passwordEncoder()));

        ShoppingCart cart = new ShoppingCart(userTest);

        // When
        ShoppingCart savedCart = shoppingCartRepository.save(cart);

        // Then
        assertTrue(savedCart.getId() > 0);
        assertEquals(BigDecimal.ZERO, savedCart.getTotalPrice());
    }

    @Test
    @DisplayName("Test Find ShoppingCart by Id")
    public void testFindShoppingCartById() {
        // Given
        String login = "testuser";
        String email = "testuser@example.com";
        String name = "Test User";
        String password = "password123*";
        DataRegisterUser dataRegisterUser = new DataRegisterUser(login, email, name, password);

        userRepository.save(new User(dataRegisterUser, TestConfig.passwordEncoder()));

        User userTest = userRepository.findUserByEmail(email);

        System.out.println(userTest);

        ShoppingCart cart = new ShoppingCart(userTest);
        ShoppingCart savedCart = shoppingCartRepository.save(cart);

        // When
        Optional<ShoppingCart> optionalCart = shoppingCartRepository.findById(savedCart.getId());

        // Then
        assertTrue(optionalCart.isPresent());
        assertEquals(BigDecimal.ZERO, optionalCart.get().getTotalPrice());
    }

    @Test
    @DisplayName("Test Delete ShoppingCart")
    public void testDeleteShoppingCart() {
        // Given
        String login = "testuser";
        String email = "testuser@example.com";
        String name = "Test User";
        String password = "password123*";
        DataRegisterUser dataRegisterUser = new DataRegisterUser(login, email, name, password);

        User userTest = userRepository.save(new User(dataRegisterUser, TestConfig.passwordEncoder()));

        ShoppingCart cart = new ShoppingCart(userTest);
        ShoppingCart savedCart = shoppingCartRepository.save(cart);

        // When
        shoppingCartRepository.deleteById(savedCart.getId());

        // Then
        assertTrue(shoppingCartRepository.findById(savedCart.getId()).isEmpty());
    }
}
