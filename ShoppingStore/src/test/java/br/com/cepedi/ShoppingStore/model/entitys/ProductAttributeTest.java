package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataRegisterProductAttribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductAttributeTest {

    private Product product;
    private DataRegisterProductAttribute data;

    @BeforeEach
    void setUp() {
        product = new Product(); // Você pode ajustar isso conforme a implementação do Product
        data = new DataRegisterProductAttribute("AttributeName", "AttributeValue", 1L);
    }

    @Test
    void testConstructor() {
        ProductAttribute productAttribute = new ProductAttribute(data, product);
        assertEquals("AttributeName", productAttribute.getName());
        assertEquals("AttributeValue", productAttribute.getValue());
        assertEquals(product, productAttribute.getProduct());
        assertFalse(productAttribute.getDisabled());
    }

    @Test
    void testDisable() {
        ProductAttribute productAttribute = new ProductAttribute(data, product);
        productAttribute.disable();
        assertTrue(productAttribute.getDisabled());
    }

    @Test
    void testEnable() {
        ProductAttribute productAttribute = new ProductAttribute(data, product);
        productAttribute.disable(); // Desabilitar primeiro para testar o habilitar
        productAttribute.enable();
        assertFalse(productAttribute.getDisabled());
    }

    @Test
    void testConstructorAndGetters() {
        ProductAttribute productAttribute = new ProductAttribute(data, product);
        assertEquals("AttributeName", productAttribute.getName());
        assertEquals("AttributeValue", productAttribute.getValue());
        assertEquals(product, productAttribute.getProduct());
        assertFalse(productAttribute.getDisabled());
        productAttribute.setId(1L);
        assertEquals(1L, productAttribute.getId()); // Garante que o ID não é nulo após a criação
    }

    @Test
    void testSetters() {
        ProductAttribute productAttribute = new ProductAttribute();

        productAttribute.setId(1L);
        productAttribute.setName("TestName");
        productAttribute.setValue("TestValue");
        productAttribute.setProduct(product);
        productAttribute.setDisabled(true);

        assertEquals(1L, productAttribute.getId());
        assertEquals("TestName", productAttribute.getName());
        assertEquals("TestValue", productAttribute.getValue());
        assertEquals(product, productAttribute.getProduct());
        assertTrue(productAttribute.getDisabled());
    }

}
