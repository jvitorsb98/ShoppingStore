package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;
import org.junit.jupiter.api.*;
import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("Test setCategory and getCategory")
    public void testSetAndGetCategory() {
        Product product = new Product();
        Category category = new Category();
        category.setId(1L);
        product.setCategory(category);
        assertEquals(category, product.getCategory());
    }

    @Test
    @DisplayName("Test creation of product from register data and category")
    public void testProductCreationFromRegisterDataAndCategory() {
        // Create a category
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        // Create data for product registration
        DataRegisterProduct data = new DataRegisterProduct(
                "Smartphone",
                "A smartphone description",
                BigDecimal.valueOf(999.99),
                "SKU123",
                "https://example.com/image.jpg",
                category.getId(),
                BigInteger.valueOf(10),
                "Manufacturer",
                true
        );

        // Create a new product based on the registration data and category
        Product product = new Product(data, category);

        // Assert that the product is not null
        assertNotNull(product);

        // Assert that the product's properties are correctly set
        assertEquals(data.name(), product.getName());
        assertEquals(data.description(), product.getDescription());
        assertEquals(data.price(), product.getPrice());
        assertEquals(data.sku(), product.getSku());
        assertEquals(data.imageUrl(), product.getImageUrl());
        assertEquals(data.quantity(), product.getQuantity());
        assertEquals(data.manufacturer(), product.getManufacturer());
        assertEquals(data.featured(), product.getFeatured());
        assertEquals(category, product.getCategory());
        assertFalse(product.getDisabled());
    }

//    @Test
//    @DisplayName("Test updateDataProduct")
//    public void testUpdateDataProduct() {
//        // Create a category
//        Category category = new Category();
//        category.setId(1L);
//        category.setName("Electronics");
//
//        // Create initial data for the product
//        DataRegisterProduct initialData = new DataRegisterProduct(
//                "Smartphone",
//                "A smartphone description",
//                BigDecimal.valueOf(999.99),
//                "SKU123",
//                "https://example.com/image.jpg",
//                category.getId(),
//                BigInteger.valueOf(10),
//                "Manufacturer",
//                true
//        );
//
//        // Create a new product based on the initial data and category
//        Product product = new Product(initialData, category);
//
//        // Create updated data for the product
//        DataUpdateProduct updatedData = new DataUpdateProduct(
//                product.getId(),
//                "Updated Smartphone",
//                "An updated smartphone description",
//                BigDecimal.valueOf(1299.99),
//                "UPDATEDSKU123",
//                "https://example.com/updated-image.jpg",
//                category.getId(),
//                BigInteger.valueOf(20),
//                "Updated Manufacturer",
//                false
//        );
//
//        // Update the product with the updated data
//        product.updateDataProduct(updatedData);
//
//        // Assert that the product has been updated correctly
//        assertEquals(updatedData.name(), product.getName());
//        assertEquals(updatedData.description(), product.getDescription());
//        assertEquals(updatedData.price(), product.getPrice());
//        assertEquals(updatedData.sku(), product.getSku());
//        assertEquals(updatedData.imageUrl(), product.getImageUrl());
//        assertEquals(updatedData.quantity(), product.getQuantity());
//        assertEquals(updatedData.manufacturer(), product.getManufacturer());
////        assertFalse(product.isFeatured());
//    }



}
