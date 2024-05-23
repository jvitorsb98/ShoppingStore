package br.com.cepedi.ShoppingStore.repository;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import com.github.javafaker.Faker;

import br.com.cepedi.ShoppingStore.model.entitys.ProductAttribute;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductAttributeRepositoryTest {

    @Mock
    private ProductAttributeRepository productAttributeRepository;

    private Faker faker = new Faker();

    public ProductAttribute createMockProductAttribute() {
        ProductAttribute attribute = new ProductAttribute();
        attribute.setId(faker.number().randomNumber());
        attribute.setName(faker.lorem().word());
        attribute.setValue(faker.lorem().word());
        return attribute;
    }

    @Test
    public void testSaveProductAttribute() {
        // Given
        ProductAttribute attribute = createMockProductAttribute();

        // When
        when(productAttributeRepository.save(attribute)).thenReturn(attribute);
        ProductAttribute savedAttribute = productAttributeRepository.save(attribute);

        // Then
        assertEquals(attribute.getName(), savedAttribute.getName());
        assertEquals(attribute.getValue(), savedAttribute.getValue());
    }

    @Test
    public void testFindAllProductAttributes() {
        // Given
        List<ProductAttribute> mockAttributes = Arrays.asList(createMockProductAttribute(), createMockProductAttribute());

        // When
        when(productAttributeRepository.findAll()).thenReturn(mockAttributes);
        List<ProductAttribute> attributes = productAttributeRepository.findAll();

        // Then
        assertEquals(2, attributes.size());
    }

    @Test
    public void testFindProductAttributeById() {
        // Given
        ProductAttribute attribute = createMockProductAttribute();
        Long id = attribute.getId();

        // When
        when(productAttributeRepository.findById(id)).thenReturn(Optional.of(attribute));
        Optional<ProductAttribute> foundAttribute = productAttributeRepository.findById(id);

        // Then
        assertEquals(id, foundAttribute.get().getId());
    }
}

