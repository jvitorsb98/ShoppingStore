package br.com.cepedi.ShoppingStore.repository;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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

    @Test
    @DisplayName("Test Save ShoppingCart")
    public void testSaveShoppingCart() {
        // Given
        BigDecimal price = new BigDecimal("100.00");
        ShoppingCart cart = new ShoppingCart();
        cart.setTotalPrice(price);

        // When
        ShoppingCart savedCart = shoppingCartRepository.save(cart);

        // Then
        assertTrue(savedCart.getId() > 0);
        assertEquals(price, savedCart.getTotalPrice());
    }

    @Test
    @DisplayName("Test Find ShoppingCart by Id")
    public void testFindShoppingCartById() {
        // Given
        BigDecimal price = new BigDecimal("100.00");
        ShoppingCart cart = new ShoppingCart();
        cart.setTotalPrice(price);
        ShoppingCart savedCart = shoppingCartRepository.save(cart);

        // When
        Optional<ShoppingCart> optionalCart = shoppingCartRepository.findById(savedCart.getId());

        // Then
        assertTrue(optionalCart.isPresent());
        assertEquals(price, optionalCart.get().getTotalPrice());
    }

    @Test
    @DisplayName("Test Delete ShoppingCart")
    public void testDeleteShoppingCart() {
        // Given
        BigDecimal price = new BigDecimal("100.00");
        ShoppingCart cart = new ShoppingCart();
        cart.setTotalPrice(price);
        ShoppingCart savedCart = shoppingCartRepository.save(cart);

        // When
        shoppingCartRepository.deleteById(savedCart.getId());

        // Then
        assertTrue(shoppingCartRepository.findById(savedCart.getId()).isEmpty());
    }
}
