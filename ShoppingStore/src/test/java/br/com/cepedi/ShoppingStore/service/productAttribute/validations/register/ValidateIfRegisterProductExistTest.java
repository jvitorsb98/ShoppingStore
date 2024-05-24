package br.com.cepedi.ShoppingStore.service.productAttribute.validations.register;

import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataRegisterProductAttribute;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
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
public class ValidateIfRegisterProductExistTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ValidateIfRegisterProductExist validateIfRegisterProductExist;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
    }

    @Test
    public void testValidation_ProductIdExists() {
        Long productId = faker.number().randomNumber();
        DataRegisterProductAttribute dataRegisterProductAttribute = new DataRegisterProductAttribute(
                faker.commerce().productName(),
                faker.lorem().word(),
                productId
        );

        when(productRepository.existsById(productId)).thenReturn(true);

        validateIfRegisterProductExist.validation(dataRegisterProductAttribute);
    }

    @Test
    public void testValidation_ProductIdDoesNotExist() {
        Long productId = faker.number().randomNumber();
        DataRegisterProductAttribute dataRegisterProductAttribute = new DataRegisterProductAttribute(
                faker.commerce().productName(),
                faker.lorem().word(),
                productId
        );

        when(productRepository.existsById(productId)).thenReturn(false);

        assertThrows(ValidationException.class, () -> {
            validateIfRegisterProductExist.validation(dataRegisterProductAttribute);
        });
    }

    @Test
    public void testValidation_ProductIdIsNull() {
        DataRegisterProductAttribute dataRegisterProductAttribute = new DataRegisterProductAttribute(
                faker.commerce().productName(),
                faker.lorem().word(),
                null
        );

        validateIfRegisterProductExist.validation(dataRegisterProductAttribute);
    }
}
