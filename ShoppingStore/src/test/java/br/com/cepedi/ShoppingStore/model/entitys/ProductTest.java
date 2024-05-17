//package br.com.cepedi.ShoppingStore.model.entitys;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//import br.com.cepedi.ShoppingStore.model.entitys.Category;
//import br.com.cepedi.ShoppingStore.model.entitys.Product;
//
//import java.math.BigDecimal;
//import java.util.Collections;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.*;
//
//@TestMethodOrder(MethodOrderer.Random.class)
//@DisplayName("Test entity Product")
//class ProductTest {
//	
//	
//	@DisplayName("Test Product constructor and getters")
//    @Test
//    void testProductConstructorAndGetters() {
//        // Arrange
//        Category category = new Category(1L, "Electronics", null);
//        Product product = new Product(
//            1L,
//            "Product Name",
//            "Product Description",
//            BigDecimal.valueOf(19.99),
//            "SKU12345",
//            "http://example.com/image.jpg",
//            10,
//            "Manufacturer",
//            true,
//            category,
//            Collections.emptyList(),
//            Collections.emptyList()
//        );
//
//        // Assert
//        assertThat(product.getId()).isEqualTo(1L);
//        assertThat(product.getName()).isEqualTo("Product Name");
//        assertThat(product.getDescription()).isEqualTo("Product Description");
//        assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(19.99));
//        assertThat(product.getSku()).isEqualTo("SKU12345");
//        assertThat(product.getImageUrl()).isEqualTo("http://example.com/image.jpg");
//        assertThat(product.getQuantity()).isEqualTo(10);
//        assertThat(product.getManufacturer()).isEqualTo("Manufacturer");
//        assertThat(product.isFeatured()).isTrue();
//        assertThat(product.getCategory()).isEqualTo(category);
//        assertThat(product.getProductAttributeList()).isEmpty();
//        assertThat(product.getProductRating()).isEmpty();
//    }
//	
//	 @DisplayName("Test Product setters")
//	 @Test
//	    void testProductSetters() {
//	        // Arrange
//	        Product product = new Product();
//	        
//	        // Act
//	        product.setId(1L);
//	        product.setName("Product Name");
//	        product.setDescription("Product Description");
//	        product.setPrice(BigDecimal.valueOf(19.99));
//	        product.setSku("SKU12345");
//	        product.setImageUrl("http://example.com/image.jpg");
//	        product.setQuantity(10);
//	        product.setManufacturer("Manufacturer");
//	        product.setFeatured(true);
//	        Category category = new Category(1L, "Electronics", null);
//	        product.setCategory(category);
//	        product.setProductAttributeList(Collections.emptyList());
//	        product.setProductRating(Collections.emptyList());
//	        
//	        // Assert
//	        assertThat(product.getId()).isEqualTo(1L);
//	        assertThat(product.getName()).isEqualTo("Product Name");
//	        assertThat(product.getDescription()).isEqualTo("Product Description");
//	        assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(19.99));
//	        assertThat(product.getSku()).isEqualTo("SKU12345");
//	        assertThat(product.getImageUrl()).isEqualTo("http://example.com/image.jpg");
//	        assertThat(product.getQuantity()).isEqualTo(10);
//	        assertThat(product.getManufacturer()).isEqualTo("Manufacturer");
//	        assertThat(product.isFeatured()).isTrue();
//	        assertThat(product.getCategory()).isEqualTo(category);
//	        assertThat(product.getProductAttributeList()).isEmpty();
//	        assertThat(product.getProductRating()).isEmpty();
//	    }
//}
