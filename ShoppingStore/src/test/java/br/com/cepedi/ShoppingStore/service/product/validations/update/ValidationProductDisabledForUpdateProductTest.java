package br.com.cepedi.ShoppingStore.service.product.validations.update;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidationProductDisabledForUpdateProductTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ValidationProductDisabledForUpdateProduct validationProductDisabledForUpdateProduct;

    public ValidationProductDisabledForUpdateProductTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidationWithEnabledProduct() {
        // Setup
        long productId = 1L;
        DataUpdateProduct dataUpdateProduct = new DataUpdateProduct(
                productId,
                "Updated Name",
                "Updated Description",
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );

        Product enabledProduct = new Product();
        enabledProduct.setId(productId);
        enabledProduct.setDisabled(false);

        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.getReferenceById(productId)).thenReturn(enabledProduct);

        // Validation
        assertDoesNotThrow(() -> {
            validationProductDisabledForUpdateProduct.validation(dataUpdateProduct);
        });
    }

    @Test
    void testValidationWithDisabledProduct() {
        // Setup
        long productId = 2L;
        DataUpdateProduct dataUpdateProduct = new DataUpdateProduct(
                productId,
                "Updated Name",
                "Updated Description",
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );

        Product disabledProduct = new Product();
        disabledProduct.setId(productId);
        disabledProduct.setDisabled(true);

        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.getReferenceById(productId)).thenReturn(disabledProduct);

        // Validation
        assertThrows(ValidationException.class, () -> {
            validationProductDisabledForUpdateProduct.validation(dataUpdateProduct);
        });
    }

    @Test
    void testValidationWithNonExistingProduct() {
        // Setup
        long productId = 3L;
        DataUpdateProduct dataUpdateProduct = new DataUpdateProduct(
                productId,
                "Updated Name",
                "Updated Description",
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );

        when(productRepository.existsById(productId)).thenReturn(false);

        // Validation
        assertDoesNotThrow(() -> {
            validationProductDisabledForUpdateProduct.validation(dataUpdateProduct);
        });
    }

}
