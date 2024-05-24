package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    
    @Test
    @DisplayName("Test disable method of ProductRating")
    void testDisable() {
        ProductRating productRating = new ProductRating();
        assertNull(productRating.getDisabled(), "Initial state should have null disabled");

        productRating.disable();

        assertTrue(productRating.getDisabled(), "ProductRating should be disabled after calling disable()");

        productRating.setDisabled(false);
        assertFalse(productRating.getDisabled(), "ProductRating should be enabled after calling setDisabled(false)");
    }



    @Test
    @DisplayName("Test enable method of ProductRating")
    void testEnable() {
        ProductRating productRating = new ProductRating();
        productRating.disable(); 
        assertTrue(productRating.getDisabled(), "Initial state should be disabled");

        productRating.enable();

        assertFalse(productRating.getDisabled(), "ProductRating should be enabled");
    }
    
    @Test
    @DisplayName("Test updateDataProductRating method of ProductRating - Rating Stars Update")
    void testUpdateDataProductRating_RatingStarsUpdate() {
        ProductRating productRating = new ProductRating();
        BigDecimal initialRating = new BigDecimal("4.0");
        productRating.setRatingStars(initialRating);

        BigDecimal newRating = new BigDecimal("3.5");
        DataUpdateProductRating updateData = new DataUpdateProductRating(1L, 1L, newRating, "New review", 1L);

        productRating.updateDataProductRating(updateData, null, null);

        assertEquals(newRating, productRating.getRatingStars());
    }

    @Test
    @DisplayName("Test updateDataProductRating method of ProductRating - Review Update")
    void testUpdateDataProductRating_ReviewUpdate() {
        ProductRating productRating = new ProductRating();
        String initialReview = "Initial review";
        productRating.setReview(initialReview);


        String newReview = "New review";
        DataUpdateProductRating updateData = new DataUpdateProductRating(1L, 1L, new BigDecimal("4.0"), newReview, 1L);

        productRating.updateDataProductRating(updateData, null, null);

        assertEquals(newReview, productRating.getReview());
    }

    @Test
    @DisplayName("Test updateDataProductRating method of ProductRating - User Update")
    void testUpdateDataProductRating_UserUpdate() {
        ProductRating productRating = new ProductRating();
        User initialUser = new User();
        productRating.setUser(initialUser);

        User newUser = new User();
        DataUpdateProductRating updateData = new DataUpdateProductRating(1L, 1L, new BigDecimal("4.0"), "New review", newUser.getId());

        productRating.updateDataProductRating(updateData, null, null);

        assertEquals(newUser, productRating.getUser());
    }

    @Test
    @DisplayName("Test updateDataProductRating method of ProductRating - Product Update")
    void testUpdateDataProductRating_ProductUpdate() {
        ProductRating productRating = new ProductRating();
        Product initialProduct = new Product();
        productRating.setProduct(initialProduct);

        Product newProduct = new Product();
        DataUpdateProductRating updateData = new DataUpdateProductRating(1L, newProduct.getId(), new BigDecimal("4.0"), "New review", 1L);

    }

}
