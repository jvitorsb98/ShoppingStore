package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.model.records.category.input.DataRegisterCategory;
import br.com.cepedi.ShoppingStore.model.records.category.input.DataUpdateCategory;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.javafaker.Faker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;

@TestMethodOrder(MethodOrderer.Random.class)
@DisplayName("Test entity Category")
@ExtendWith(SpringExtension.class)
class CategoryTest {

	private Product product;
	private Faker faker;
	private Category category;
	private Brand brand;
	
	@BeforeEach
    public void setUp() {
        faker = new Faker();
        category = new Category();
        brand = new Brand();

        DataRegisterProduct data = new DataRegisterProduct(
                faker.commerce().productName(),
                faker.commerce().material(),
                BigDecimal.valueOf(faker.number().randomDouble(2, 10, 1000)),
                faker.code().ean13(),
                faker.internet().url(),
                null, BigInteger.valueOf(faker.number().numberBetween(1, 100)),
                null, faker.bool().bool()
        );

        product = new Product(data, category, brand);
    }

	
    @Test
    @DisplayName("Teste de criação básico")
    public void testCategoryCreation() {
        Category category = new Category();
        Assertions.assertNotNull(category);
    }

    @Test
    @DisplayName("Teste de igualdade")
    public void testCategoryEquality() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Eletrônicos");

        Category category2 = new Category();
        category2.setId(1L);
        category2.setName("Eletrônicos");

        assertEquals(category1, category2);
    }

    @Test
    @DisplayName("Teste de desigualdade")
    public void testCategoryInequality() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Eletrônicos");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Roupas");

        Assertions.assertNotEquals(category1, category2);
    }

    @Test
    @DisplayName("Test constructor and getters")
    void testConstructorAndGetters() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("Electronic devices and accessories");

        assertEquals(1L, category.getId());
        assertEquals("Electronics", category.getName());
        assertEquals("Electronic devices and accessories", category.getDescription());
    }

    @Test
    @DisplayName("Teste de hash code - igualdade")
    public void testCategoryHashCodeEquality() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Eletrônicos");

        Category category2 = new Category();
        category2.setId(1L);
        category2.setName("Eletrônicos");

        assertEquals(category1.hashCode(), category2.hashCode());
    }

    @Test
    @DisplayName("Teste de hash code - desigualdade")
    public void testCategoryHashCodeInequality() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Eletrônicos");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Roupas");

        Assertions.assertNotEquals(category1.hashCode(), category2.hashCode());
    }

    @Test
    @DisplayName("Teste do construtor com DataRegisterCategory")
    public void testConstructorWithDataRegisterCategory() {
        DataRegisterCategory data = new DataRegisterCategory("Livros", "Categoria de livros");
        Category category = new Category(data);

        assertEquals("Livros", category.getName());
        assertEquals("Categoria de livros", category.getDescription());
    }
    
    @Test
    public void testSetName() {
        Category category = new Category();

        String name = faker.commerce().department();
        category.setName(name);

        assertEquals(name, category.getName());
    }

    @Test
    public void testSetDescription() {
        Category category = new Category();

        String description = faker.lorem().sentence();
        category.setDescription(description);

        assertEquals(description, category.getDescription());
    }

    @Test
    public void testSetDisabled() {
        Category category = new Category();

        boolean disabled = true;
        category.setDisabled(disabled);

        assertEquals(disabled, category.getDisabled());
    }
    
    @Test
    public void testUpdateDataCategory() {
        // Criando uma nova categoria
        Category category = new Category();
        category.setName("Old Name");
        category.setDescription("Old Description");

        // Criando dados de atualização
        String updatedName = faker.commerce().department();
        String updatedDescription = faker.lorem().sentence();
        Long categoryId = faker.number().randomNumber();
        DataUpdateCategory dataUpdateCategory = new DataUpdateCategory(categoryId, updatedName, updatedDescription);

        // Atualizando os dados da categoria
        category.updateDataCategory(dataUpdateCategory);

        // Verificando se os dados foram atualizados corretamente
        assertEquals(updatedName, category.getName());
        assertEquals(updatedDescription, category.getDescription());
    }
    
    @Test
    public void testDisable() {
        category.disable();
        assertTrue(category.getDisabled(), "Category should be disabled");
    }

    @Test
    public void testEnable() {
        category.disable(); // First disable it to ensure it can be enabled
        category.enable();
        assertFalse(category.getDisabled(), "Category should be enabled");
    }

    @Test
    public void testToString() {
        String expected = "Category(id=" + category.getId() + 
                          ", name=" + category.getName() + 
                          ", description=" + category.getDescription() + 
                          ", disabled=" + category.getDisabled() + ")";
        assertEquals(expected, category.toString(), "toString() method output mismatch");
    }
    
    @Test
    public void testSetters() {
        // Generate random data for testing
        Long initialId = 1L;
        String newName = faker.commerce().productName();
        String newDescription = faker.commerce().material();
        BigDecimal newPrice = BigDecimal.valueOf(faker.number().randomDouble(2, 10, 1000));
        String newSku = faker.code().ean13();
        String newImageUrl = faker.internet().url();
        BigInteger newQuantity = BigInteger.valueOf(faker.number().numberBetween(1, 100));
        Boolean newFeatured = faker.bool().bool();

        product.setId(initialId);
        product.setName(newName);
        product.setDescription(newDescription);
        product.setPrice(newPrice);
        product.setSku(newSku);
        product.setImageUrl(newImageUrl);
        product.setQuantity(newQuantity);
        product.setFeatured(newFeatured);

        // Check if the attributes are set correctly
        assertEquals(initialId, product.getId());
        assertEquals(newName, product.getName());
        assertEquals(newDescription, product.getDescription());
        assertEquals(newPrice, product.getPrice());
        assertEquals(newSku, product.getSku());
        assertEquals(newImageUrl, product.getImageUrl());
        assertEquals(newQuantity, product.getQuantity());
        assertEquals(newFeatured, product.getFeatured());
    }

    
    @Test
    public void testUpdateDataProduct() {
        DataUpdateProduct updateData = new DataUpdateProduct(
                null, faker.commerce().productName(),
                faker.commerce().material(),
                BigDecimal.valueOf(faker.number().randomDouble(2, 10, 1000)),
                faker.code().ean13(),
                faker.internet().url(),
                null, BigInteger.valueOf(faker.number().numberBetween(1, 100)), null, false
        );

        product.updateDataProduct(updateData, category);

        assertEquals(updateData.name(), product.getName());
        assertEquals(updateData.description(), product.getDescription());
        assertEquals(updateData.price(), product.getPrice());
        assertEquals(updateData.sku(), product.getSku());
        assertEquals(updateData.imageUrl(), product.getImageUrl());
        assertEquals(updateData.quantity(), product.getQuantity());
        assertEquals(category, product.getCategory());
    }

    

    
}
