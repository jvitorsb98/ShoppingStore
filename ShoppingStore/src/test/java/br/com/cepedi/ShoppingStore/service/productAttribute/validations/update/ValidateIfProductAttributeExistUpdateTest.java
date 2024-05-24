package br.com.cepedi.ShoppingStore.service.productAttribute.validations.update;

import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataUpdateProductAttribute;
import br.com.cepedi.ShoppingStore.repository.ProductAttributeRepository;
import com.github.javafaker.Faker;
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
public class ValidateIfProductAttributeExistUpdateTest {

    @Mock
    private ProductAttributeRepository productAttributeRepository;

    @InjectMocks
    private ValidateIfProductAttributeExistUpdate validateIfProductAttributeExistUpdate;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
    }

    @Test
    public void testValidation_ProductAttributeExists() {
        Long attributeId = faker.number().randomNumber();
        DataUpdateProductAttribute dataUpdateProductAttribute = new DataUpdateProductAttribute(
                attributeId,
                faker.commerce().productName(),
                faker.lorem().word()
        );

        when(productAttributeRepository.existsById(attributeId)).thenReturn(true);

        validateIfProductAttributeExistUpdate.validation(dataUpdateProductAttribute);
    }

    @Test
    public void testValidation_ProductAttributeDoesNotExist() {
        Long attributeId = faker.number().randomNumber();
        DataUpdateProductAttribute dataUpdateProductAttribute = new DataUpdateProductAttribute(
                attributeId,
                faker.commerce().productName(),
                faker.lorem().word()
        );

        when(productAttributeRepository.existsById(attributeId)).thenReturn(false);

        assertThrows(ValidationException.class, () -> {
            validateIfProductAttributeExistUpdate.validation(dataUpdateProductAttribute);
        });
    }
}

