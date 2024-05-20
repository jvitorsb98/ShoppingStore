package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.input.DataRegisterShoppingCart;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.Random.class)
@DisplayName("ShoppingCart Test - Random Order")
public class ShoppingCartTest {

    @Test
    @DisplayName("Test ShoppingCart Creation with Valid Data")
    public void testShoppingCartCreationWithValidData() {
        BigDecimal price = BigDecimal.ZERO;
        Long userId = 1L;
        User user = new User();
        user.setId(1L);

        DataRegisterShoppingCart data = new DataRegisterShoppingCart(userId);

        ShoppingCart cart = new ShoppingCart(user);

        assertEquals(price, BigDecimal.ZERO);
        assertEquals(user, cart.getUser());
    }

    @Test
    @DisplayName("Test Set Total Price")
    public void testSetTotalPrice() {
        ShoppingCart cart = new ShoppingCart();
        BigDecimal newPrice = new BigDecimal("150.00");

        cart.setTotalPrice(newPrice);

        assertEquals(newPrice, cart.getTotalPrice());
    }

    @Test
    @DisplayName("Test Update Total Price")
    public void testUpdateTotalPrice() {
        BigDecimal initialPrice = new BigDecimal("100.00");
        BigDecimal updatedPrice = new BigDecimal("150.00");
        Long userId = 1L;
        User user = new User();


        DataRegisterShoppingCart data = new DataRegisterShoppingCart(userId);

        ShoppingCart cart = new ShoppingCart(user);

        BigDecimal initialCartTotalPrice = cart.getTotalPrice();

        cart.setTotalPrice(BigDecimal.valueOf(2.000));

        assertEquals(BigDecimal.ZERO, initialCartTotalPrice);

        cart.setTotalPrice(updatedPrice);
        assertEquals(updatedPrice, cart.getTotalPrice());
    }

    @Test
    @DisplayName("Test Get and Set Id")
    public void testGetAndSetId() {
        ShoppingCart cart = new ShoppingCart();
        Long id = 123L;

        // Set the ID
        cart.setId(id);

        // Get and assert the ID
        assertEquals(id, cart.getId());
    }

    @Test
    @DisplayName("Test Set User")
    public void testSetUser() {
        ShoppingCart cart = new ShoppingCart();
        User user = new User();
        user.setId(123L);

        cart.setUser(user);

        assertEquals(user, cart.getUser());
    }

    @Test
    @DisplayName("Test equals method with same object")
    public void testEqualsWithSameObject() {
        ShoppingCart cart1 = new ShoppingCart();
        ShoppingCart cart2 = cart1;

        assertTrue(cart1.equals(cart2));
    }

    @Test
    @DisplayName("Test equals method with null")
    public void testEqualsWithNull() {
        ShoppingCart cart = new ShoppingCart();

        assertFalse(cart.equals(null));
    }

    @Test
    @DisplayName("Test equals method with different class")
    public void testEqualsWithDifferentClass() {
        ShoppingCart cart = new ShoppingCart();
        Object obj = new Object();

        assertFalse(cart.equals(obj));
    }



    @Test
    @DisplayName("Test hashCode method")
    public void testHashCode() {
        ShoppingCart cart1 = new ShoppingCart();
        ShoppingCart cart2 = new ShoppingCart();

        assertEquals(cart1.hashCode(), cart2.hashCode());
    }

    @Test
    @DisplayName("Test canEqual method with same object")
    public void testCanEqualWithSameObject() {
        ShoppingCart cart1 = new ShoppingCart();
        ShoppingCart cart2 = cart1;

        assertTrue(cart1.canEqual(cart2));
    }

    @Test
    @DisplayName("Test canEqual method with different class")
    public void testCanEqualWithDifferentClass() {
        ShoppingCart cart = new ShoppingCart();
        Object obj = new Object();

        assertFalse(cart.canEqual(obj));
    }

    @Test
    @DisplayName("Test canEqual method with different objects")
    public void testCanEqualWithDifferentObjects() {
        ShoppingCart cart1 = new ShoppingCart();
        ShoppingCart cart2 = new ShoppingCart();

        assertTrue(cart1.canEqual(cart2));
    }

}
