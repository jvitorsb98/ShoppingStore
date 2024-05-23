package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
@DisplayName("Test entity Product")
class ProductTest {

    @Test
    @DisplayName("Inequality test")
    public void testProductInequality() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Smartphone");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Laptop");

        assertNotEquals(product1, product2);
    }

    @Test
    @DisplayName("Basic creation test")
    public void testProductCreation() {
        Product product = new Product();
        assertNotNull(product);
    }

    @Test
    @DisplayName("Hash code inequality test")
    public void testProductHashCodeInequality() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Smartphone");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Laptop");

        assertNotEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    @DisplayName("Test getId and setId")
    public void testGetAndSetId() {
        Product product = new Product();
        product.setId(1L);
        assertEquals(1L, product.getId());
    }

    @Test
    @DisplayName("Test disable and enable")
    public void testDisableAndEnable() {
        Product product = new Product();
        product.setDisabled(false);
        product.disable();
        assertTrue(product.getDisabled());
        product.enable();
        assertFalse(product.getDisabled());
    }

    @Test
    @DisplayName("Test setBrand and getBrand")
    public void testSetAndGetBrand() {
        Product product = new Product();
        Brand brand = new Brand();
        brand.setId(1L);
        product.setBrand(brand);
        assertEquals(brand, product.getBrand());
    }

    @Test
    @DisplayName("Test creation of product from register data, category, and brand")
    public void testProductCreationFromRegisterDataCategoryAndBrand() {
        // Create a category
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        // Create a brand
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Samsung");

        // Create data for product registration
        DataRegisterProduct data = new DataRegisterProduct(
                "Smartphone",
                "A smartphone description",
                BigDecimal.valueOf(999.99),
                "SKU123",
                "https://example.com/image.jpg",
                category.getId(),
                BigInteger.valueOf(10),
                brand.getId(),
                true
        );

        // Create a new product based on the registration data, category, and brand
        Product product = new Product(data, category, brand);

        // Assert that the product is not null
        assertNotNull(product);

        // Assert that the product's properties are correctly set
        assertEquals(data.name(), product.getName());
        assertEquals(data.description(), product.getDescription());
        assertEquals(data.price(), product.getPrice());
        assertEquals(data.sku(), product.getSku());
        assertEquals(data.imageUrl(), product.getImageUrl());
        assertEquals(data.quantity(), product.getQuantity());
        assertEquals(brand, product.getBrand());
        assertEquals(data.featured(), product.getFeatured());
        assertEquals(category, product.getCategory());
        assertFalse(product.getDisabled());
    }

}
