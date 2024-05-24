package br.com.cepedi.ShoppingStore.service.productAttribute.validations.update;

import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataUpdateProductAttribute;
import br.com.cepedi.ShoppingStore.repository.ProductAttributeRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidateIfProductAttributeIsDisabledTest {

    @Mock
    private ProductAttributeRepository productAttributeRepository;

    @InjectMocks
    private ValidateIfProductAttributeIsDisabled validateIfProductAttributeIsDisabled;

    private DataUpdateProductAttribute validData;
    private DataUpdateProductAttribute disabledData;

    @BeforeEach
    public void setUp() {
        validData = new DataUpdateProductAttribute(1L, "Attribute 1", "Value 1");
        disabledData = new DataUpdateProductAttribute(2L, "Attribute 2", "Value 2");
    }

    @Test
    public void testValidation_ProductAttributeIsNotDisabled() {
        when(productAttributeRepository.findDisabledById(validData.id())).thenReturn(false);
        
        validateIfProductAttributeIsDisabled.validation(validData);
    }

    @Test
    public void testValidation_ProductAttributeIsDisabled() {
        when(productAttributeRepository.findDisabledById(disabledData.id())).thenReturn(true);
        
        assertThrows(ValidationException.class, () -> {
            validateIfProductAttributeIsDisabled.validation(disabledData);
        });
    }
}

