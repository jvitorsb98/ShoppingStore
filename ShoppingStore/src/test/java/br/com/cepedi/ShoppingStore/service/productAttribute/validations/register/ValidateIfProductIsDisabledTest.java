package br.com.cepedi.ShoppingStore.service.productAttribute.validations.register;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
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
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ValidateIfProductIsDisabledTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ValidateIfProductIsDisabled validateIfProductIsDisabled;

    private DataRegisterProductAttribute dataRegisterProductAttribute;
    private Faker faker;

    @BeforeEach
    public void setUp() {
        faker = new Faker();

        String name = faker.commerce().productName();
        String value = faker.lorem().word();
        Long productId = faker.number().randomNumber();

        dataRegisterProductAttribute = new DataRegisterProductAttribute(name, value, productId);
    }

    @Test
    public void testValidation_ProductIsDisabled() {
        Product product = mock(Product.class);

        when(product.getDisabled()).thenReturn(true);
        when(productRepository.getReferenceById(dataRegisterProductAttribute.productId())).thenReturn(product);

        assertThrows(ValidationException.class, () -> {
            validateIfProductIsDisabled.validation(dataRegisterProductAttribute);
        });
    }

    @Test
    public void testValidation_ProductIsNotDisabled() {
        Product product = mock(Product.class);

        when(product.getDisabled()).thenReturn(false);
        when(productRepository.getReferenceById(dataRegisterProductAttribute.productId())).thenReturn(product);

        validateIfProductIsDisabled.validation(dataRegisterProductAttribute);
    }
}


