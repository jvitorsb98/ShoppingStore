package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.Random.class)
@DisplayName("Test entity ProductRatingTest")
public class ProductRatingTest {

    private ProductRating productRating;
    private Product product;
    private User user;
    private DataRegisterProductRating data;

    @BeforeEach
    @DisplayName("Setup mocks and initialize ProductRating instance")
    void setUp() {
        // Mock objects
        product = mock(Product.class);
        user = mock(User.class);

        // Mock behavior
        when(product.getName()).thenReturn("Test Product");
        when(user.getUsername()).thenReturn("testUser");

        // Initialize DataRegisterProductRating as needed
        data = mock(DataRegisterProductRating.class);
        when(data.ratingStars()).thenReturn(new BigDecimal("4.5"));
        when(data.review()).thenReturn("Great product!");

        // Create instance of ProductRating
        productRating = new ProductRating(data, user, product);
    }

    @Test
    @DisplayName("Test ProductRating constructor initializes fields correctly")
    void testProductRatingConstructor() {
        assertEquals(new BigDecimal("4.5"), productRating.getRatingStars());
        assertEquals("Great product!", productRating.getReview());
        assertEquals(user, productRating.getUser());
        assertEquals(product, productRating.getProduct());
    }

    @Test
    @DisplayName("Test setters and getters of ProductRating")
    void testSettersAndGetters() {
        Product newProduct = mock(Product.class);
        User newUser = mock(User.class);

        productRating.setRatingStars(new BigDecimal("3.5"));
        productRating.setReview("Good product");
        productRating.setUser(newUser);
        productRating.setProduct(newProduct);

        assertEquals(new BigDecimal("3.5"), productRating.getRatingStars());
        assertEquals("Good product", productRating.getReview());
        assertEquals(newUser, productRating.getUser());
        assertEquals(newProduct, productRating.getProduct());
    }

    @Test
    @DisplayName("Test getId and setId of ProductRating")
    void testGetAndSetId() {
        Long id = 1L;
        productRating.setId(id);
        assertEquals(id, productRating.getId());
    }
}
