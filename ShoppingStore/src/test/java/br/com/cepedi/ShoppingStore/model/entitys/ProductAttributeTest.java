package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataRegisterProductAttribute;
import com.github.javafaker.Faker;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.Random.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Test entity ProductAttributeTest")
public class ProductAttributeTest {

    @Mock
    private ProductAttribute mockAttribute;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test ProductAttribute creation object with all args constructor")
    public void testAllArgsConstructor() {
        // Given
        Faker faker = new Faker();
        Long id = faker.number().randomNumber();
        String name = faker.lorem().word();
        String value = faker.lorem().word();
        Product product = new Product();

        // When
        ProductAttribute attribute = new ProductAttribute(id, name, value, product);

        // Then
        assertNotNull(attribute);
        assertEquals(id, attribute.getId());
        assertEquals(name, attribute.getName());
        assertEquals(value, attribute.getValue());
    }


    @Test
    @DisplayName("Test ProductAttribute creation object with no args constructor")
    public void testNoArgsConstructor() {
        // When
        ProductAttribute attribute = new ProductAttribute();

        // Then
        assertNotNull(attribute);
        assertNull(attribute.getId());
        assertNull(attribute.getName());
        assertNull(attribute.getValue());
    }

    @Test
    @DisplayName("Tests getters and setters of ProductAttribute entity")
    public void testGetterAndSetter() {
        // Given
        Faker faker = new Faker();
        Long id = faker.number().randomNumber();
        String name = faker.lorem().word();
        String value = faker.lorem().word();
        Product product = new Product();

        // When
        when(mockAttribute.getId()).thenReturn(id);
        when(mockAttribute.getName()).thenReturn(name); // Configuração do mock para retornar o nome gerado pelo Faker
        when(mockAttribute.getValue()).thenReturn(value);
        when(mockAttribute.getProduct()).thenReturn(product);

        // Then
        mockAttribute.setId(id);
        mockAttribute.setName(name);
        mockAttribute.setValue(value);
        mockAttribute.setProduct(product);

        assertEquals(id, mockAttribute.getId());
        assertEquals(name, mockAttribute.getName());
        assertEquals(value, mockAttribute.getValue());
        assertEquals(product, mockAttribute.getProduct());
    }

    @Test
    @DisplayName("Test ProductAttribute object creation with DataRegister")
    public void testProductAttributeCreationWithDataRegister(){
        // Arrange
        Faker faker = new Faker();
        Product product = new Product();
        String name = faker.lorem().word();
        String value = faker.lorem().word();
        Long id = faker.number().randomNumber();

        // Act
        DataRegisterProductAttribute dataRegisterProductAttribute = new DataRegisterProductAttribute(name, value, id);
        ProductAttribute productAttribute = new ProductAttribute(dataRegisterProductAttribute, product);

        // Assert
        assertEquals(product, productAttribute.getProduct());
        assertEquals(name, productAttribute.getName());
        assertEquals(value, productAttribute.getValue());
    }

    @Test
    @DisplayName("Tests for the Equals HashCode and ToString methods of the ProductAttribute entity")
    public void testEqualsHashCodeAndToString(){
        // Arrange
        Faker faker = new Faker();
        Long id = faker.number().randomNumber();
        String name = faker.lorem().word();
        String value = faker.lorem().word();
        Product product = new Product();
        Product product2 = new Product();
        String string = "ProductAttribute(id=" + id + ", name=" + name + ", value=" + value + ", product=Product(id=" + product.getId() + ", name=" + product.getName() + ", description=" + product.getDescription() + ", price=" + product.getPrice() + ", sku=" + product.getSku() + ", imageUrl=" + product.getImageUrl() + ", quantity=" + product.getQuantity() + ", manufacturer=" + product.getManufacturer() + ", featured=false, category=" + product.getCategory() + "))";

        // Act
        ProductAttribute attribute = new ProductAttribute(id, name, value, product);
        ProductAttribute attribute2 = new ProductAttribute(id, name, value, product);
        ProductAttribute attribute3 = new ProductAttribute(faker.number().randomNumber(), faker.lorem().word(), faker.lorem().word(), product2);

        // Assert equals
        assertEquals(attribute, attribute2);
        assertNotEquals(attribute, attribute3);
        assertNotEquals(attribute, null);

        // Assert toString
        assertEquals(attribute2.toString(), string);
        assertNotEquals(attribute2.toString(), "string");

        // Assert hashCode
        assertEquals(attribute.hashCode(), attribute2.hashCode());
        assertNotEquals(attribute.hashCode(), attribute3.hashCode());
    }
}


