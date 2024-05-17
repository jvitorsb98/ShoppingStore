package br.com.cepedi.ShoppingStore.model.entitys;

import com.github.javafaker.Faker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductAttributeTest {

    @Mock
    private ProductAttribute mockAttribute;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAllArgsConstructor() {
        // Given
        Faker faker = new Faker();
        Long id = faker.number().randomNumber();
        String name = faker.lorem().word();
        String value = faker.lorem().word();

        // When
        ProductAttribute attribute = new ProductAttribute(id, name, value);

        // Then
        assertNotNull(attribute);
        assertEquals(id, attribute.getId());
        assertEquals(name, attribute.getName());
        assertEquals(value, attribute.getValue());
    }


    @Test
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
    public void testGetter() {
        // Given
        Faker faker = new Faker();
        Long id = faker.number().randomNumber();
        String name = faker.lorem().word();
        String value = faker.lorem().word();

        // When
        when(mockAttribute.getId()).thenReturn(id);
        when(mockAttribute.getName()).thenReturn(name); // Configuração do mock para retornar o nome gerado pelo Faker
        when(mockAttribute.getValue()).thenReturn(value);
        
        // Then
        mockAttribute.setId(id);
        mockAttribute.setName(name);
        mockAttribute.setValue(value);

        assertEquals(id, mockAttribute.getId());
        assertEquals(name, mockAttribute.getName());
        assertEquals(value, mockAttribute.getValue());
    }


        @Test
        public void testSetter() {
            // Given
            ProductAttribute attribute = new ProductAttribute();
            Faker faker = new Faker();
            Long id = faker.number().randomNumber();
            String name = faker.lorem().word();
            String value = faker.lorem().word();

            // When
            attribute.setId(id);
            attribute.setName(name);
            attribute.setValue(value);

            // Then
            assertEquals(id, attribute.getId());
            assertEquals(name, attribute.getName());
            assertEquals(value, attribute.getValue());
        }
}


