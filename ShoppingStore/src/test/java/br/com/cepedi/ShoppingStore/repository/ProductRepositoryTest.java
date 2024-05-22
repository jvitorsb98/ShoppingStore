package br.com.cepedi.ShoppingStore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;

@DataJpaTest
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
        category = new Category();
        categoryRepository.save(category);
    }

    @DisplayName("Test Product save")
    @Test
    void testSaveProduct() {
        // Data for registration
        DataRegisterProduct dataRegisterProduct = new DataRegisterProduct(
                "Product Name",
                "Product Description",
                BigDecimal.valueOf(19.99),
                "SKU12345",
                "http://example.com/image.jpg",
                category.getId(),
                BigInteger.valueOf(10),
                "Manufacturer",
                false
        );

        // Arrange
        Product product = new Product(dataRegisterProduct, category);

        // Act
        Product savedProduct = productRepository.save(product);

        // Assert
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Product Name");
    }

    @DisplayName("Test Find All Products")
    @Test
    void testFindAllProducts() {
        // Arrange
        DataRegisterProduct dataRegisterProduct1 = new DataRegisterProduct(
                "Product Name 1",
                "Product Description 1",
                BigDecimal.valueOf(19.99),
                "SKU12345",
                "http://example.com/image.jpg",
                category.getId(),
                BigInteger.valueOf(10),
                "Manufacturer",
                false
        );

        DataRegisterProduct dataRegisterProduct2 = new DataRegisterProduct(
                "Product Name 2",
                "Product Description 2",
                BigDecimal.valueOf(29.99),
                "SKU67890",
                "http://example.com/image2.jpg",
                category.getId(),
                BigInteger.valueOf(5),
                "Manufacturer",
                false
        );

        productRepository.save(new Product(dataRegisterProduct1, category));
        productRepository.save(new Product(dataRegisterProduct2, category));

        // Act
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = productRepository.findAll(pageable);

        // Assert
        assertThat(productPage.getTotalElements()).isEqualTo(2);
    }

    @DisplayName("Test Find Product by Category ID")
    @Test
    void testFindProductByCategoryId() {
        // Arrange
        DataRegisterProduct dataRegisterProduct = new DataRegisterProduct(
                "Product Name",
                "Product Description",
                BigDecimal.valueOf(19.99),
                "SKU12345",
                "http://example.com/image.jpg",
                category.getId(),
                BigInteger.valueOf(10),
                "Manufacturer",
                false
        );

        Product product = new Product(dataRegisterProduct, category);
        productRepository.save(product);

        // Act
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = productRepository.findAllByCategoryId(category.getId(), pageable);

        // Assert
        assertThat(productPage.getTotalElements()).isEqualTo(1);
        assertThat(productPage.getContent().get(0).getName()).isEqualTo("Product Name");
    }

    @DisplayName("Test Update Product")
    @Test
    void testUpdateProduct() {
        // Arrange
        DataRegisterProduct dataRegisterProduct = new DataRegisterProduct(
                "Product Name",
                "Product Description",
                BigDecimal.valueOf(19.99),
                "SKU12345",
                "http://example.com/image.jpg",
                category.getId(),
                BigInteger.valueOf(10),
                "Manufacturer",
                false
        );

        Product product = new Product(dataRegisterProduct, category);
        Product savedProduct = productRepository.save(product);

        // Act
        savedProduct.setName("Updated Product Name");
        productRepository.save(savedProduct);

        // Assert
        Product updatedProduct = productRepository.findById(savedProduct.getId()).orElse(null);
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getName()).isEqualTo("Updated Product Name");
    }

    @DisplayName("Test Delete Product")
    @Test
    void testDeleteProduct() {
        // Arrange
        DataRegisterProduct dataRegisterProduct = new DataRegisterProduct(
                "Product Name",
                "Product Description",
                BigDecimal.valueOf(19.99),
                "SKU12345",
                "http://example.com/image.jpg",
                category.getId(),
                BigInteger.valueOf(10),
                "Manufacturer",
                false
        );

        Product product = new Product(dataRegisterProduct, category);
        Product savedProduct = productRepository.save(product);

        // Act
        productRepository.delete(savedProduct);

        // Assert
        Product deletedProduct = productRepository.findById(savedProduct.getId()).orElse(null);
        assertThat(deletedProduct).isNull();
    }
}
