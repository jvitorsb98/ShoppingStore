package br.com.cepedi.ShoppingStore.model.entitys;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.Product;

import java.math.BigDecimal;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.Random.class)
@DisplayName("Test entity Product")
class ProductTest {


    @Test
    @DisplayName("Basic creation test")
    public void testProductCreation() {
        Product product = new Product();
        Assertions.assertNotNull(product);
    }

    @Test
    @DisplayName("Equality test")
    public void testProductEquality() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Smartphone");
        product1.setDescription("Smartphone description");
        product1.setPrice(BigDecimal.valueOf(999.99));
        product1.setSku("SKU123");
        product1.setImageUrl("https://example.com/image.jpg");
        product1.setQuantity(10);
        product1.setManufacturer("Manufacturer");
        product1.setFeatured(true);

        Product product2 = new Product();
        product2.setId(1L);
        product2.setName("Smartphone");
        product2.setDescription("Smartphone description");
        product2.setPrice(BigDecimal.valueOf(999.99));
        product2.setSku("SKU123");
        product2.setImageUrl("https://example.com/image.jpg");
        product2.setQuantity(10);
        product2.setManufacturer("Manufacturer");
        product2.setFeatured(true);

        Assertions.assertEquals(product1, product2);
    }

    @Test
    @DisplayName("Inequality test")
    public void testProductInequality() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Smartphone");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Laptop");

        Assertions.assertNotEquals(product1, product2);
    }


}
