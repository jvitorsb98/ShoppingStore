package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataRegisterProductAttribute;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataUpdateProductAttribute;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @Test
    @DisplayName("Test updateDataProductAttribute method of ProductAttribute")
    void testUpdateDataProductAttribute() {
        // Criação de instância de ProductAttribute com estado inicial
        ProductAttribute productAttribute = new ProductAttribute();
        
        // Criação de instância de DataUpdateProductAttribute com novos dados
        DataUpdateProductAttribute updateData = new DataUpdateProductAttribute(1L, "New Name", "New Value");
        
        // Chamada do método updateDataProductAttribute com os novos dados
        productAttribute.updateProductAttribute(updateData);

        // Verificação se a entidade ProductAttribute foi atualizada corretamente
        assertEquals("New Name", productAttribute.getName(), "Name should be updated");
        assertEquals("New Value", productAttribute.getValue(), "Value should be updated");
    }


}
