package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input.DataRegisterShoppingCartItem;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.Random.class)
@DisplayName("Test entity ShoppingCartItemTest")
public class ShoppingCartItemTest {

    private ShoppingCartItem shoppingCartItem;
    private ShoppingCart shoppingCart;
    private Product product;
    private DataRegisterShoppingCartItem data;

    @BeforeEach
    @DisplayName("Setup mocks and initialize ShoppingCartItem instance")
    void setUp() {
        // Mock objects
        shoppingCart = mock(ShoppingCart.class);
        product = mock(Product.class);

        // Mock behavior
        when(product.getName()).thenReturn("Test Product");
        when(product.getPrice()).thenReturn(new BigDecimal("19.99"));

        // Initialize DataRegisterShoppingCartItem as needed
        data = mock(DataRegisterShoppingCartItem.class);

        // Create instance of ShoppingCartItem
        shoppingCartItem = new ShoppingCartItem(data, shoppingCart, product);
    }

    @Test
    @DisplayName("Test ShoppingCartItem constructor initializes fields correctly")
    void testShoppingCartItemConstructor() {
        assertEquals("Test Product", shoppingCartItem.getName());
        assertEquals(new BigDecimal("19.99"), shoppingCartItem.getPricePurchase());
        assertEquals(shoppingCart, shoppingCartItem.getShoppingCart());
        assertEquals(product, shoppingCartItem.getProduct());
        assertEquals(data.quantity(), shoppingCartItem.getQuantity());
    }

    @Test
    @DisplayName("Test setters and getters of ShoppingCartItem")
    void testSettersAndGetters() {
        ShoppingCart newShoppingCart = mock(ShoppingCart.class);
        Product newProduct = mock(Product.class);

        shoppingCartItem.setName("New Name");
        shoppingCartItem.setPricePurchase(new BigDecimal("29.99"));
        shoppingCartItem.setShoppingCart(newShoppingCart);
        shoppingCartItem.setProduct(newProduct);
        shoppingCartItem.setQuantity(new BigInteger("3"));

        assertEquals("New Name", shoppingCartItem.getName());
        assertEquals(new BigDecimal("29.99"), shoppingCartItem.getPricePurchase());
        assertEquals(newShoppingCart, shoppingCartItem.getShoppingCart());
        assertEquals(newProduct, shoppingCartItem.getProduct());
        assertEquals(new BigInteger("3"), shoppingCartItem.getQuantity());
    }

    @Test
    @DisplayName("Test getId and setId of ShoppingCartItem")
    void testGetAndSetId() {
        Long id = 1L;
        shoppingCartItem.setId(id);
        assertEquals(id, shoppingCartItem.getId());
    }

    @Test
    @DisplayName("Test Disable ShoppingCartItem")
    void testDisableShoppingCartItem() {
        // Verify initial state
        assertFalse(shoppingCartItem.getDisabled());

        // Disable the item
        shoppingCartItem.disable();

        // Verify disabled state
        assertTrue(shoppingCartItem.getDisabled());
    }

    @Test
    @DisplayName("Test Enable ShoppingCartItem")
    void testEnableShoppingCartItem() {
        // Disable the item
        shoppingCartItem.disable();

        // Verify disabled state
        assertTrue(shoppingCartItem.getDisabled());

        // Enable the item
        shoppingCartItem.enable();

        // Verify enabled state
        assertFalse(shoppingCartItem.getDisabled());
    }

    @Test
    @DisplayName("Test Get and Set Disabled")
    void testGetAndSetDisabled() {
        // Set disabled state
        shoppingCartItem.setDisabled(true);

        // Get and assert disabled state
        assertTrue(shoppingCartItem.getDisabled());

        // Set enabled state
        shoppingCartItem.setDisabled(false);

        // Get and assert enabled state
        assertFalse(shoppingCartItem.getDisabled());
    }

}
