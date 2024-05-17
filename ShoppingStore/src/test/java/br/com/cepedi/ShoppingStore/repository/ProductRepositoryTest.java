package br.com.cepedi.ShoppingStore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.Product;

@DataJpaTest
@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Test Product Repository")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    void setUp() {
//      category = new Category(null, "Electronics", null);
    	category = new Category();//remover esse ao decomentar o de cima
        categoryRepository.save(category);
    }
    
    @DisplayName("Test Product save")
    @Test
    void testSaveProduct() {
        // Arrange
        Product product = new Product(
            null,
            "Product Name",
            "Product Description",
            BigDecimal.valueOf(19.99),
            "SKU12345",
            "http://example.com/image.jpg",
            10,
            "Manufacturer",
            true,
            category
//            List.of(),
//            List.of()
        );

        // Act
        Product savedProduct = productRepository.save(product);

        // Assert
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Product Name");
    }
}
