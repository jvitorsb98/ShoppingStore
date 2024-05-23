package br.com.cepedi.ShoppingStore.service.product.validations.update;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ValidationCategoryDisabledForUpdateProductTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ValidationCategoryDisabledForUpdateProduct validationCategoryDisabledForUpdateProduct;

    public ValidationCategoryDisabledForUpdateProductTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidationWithEnabledCategory() {
        // Setup
        Long categoryId = 1L;
        DataUpdateProduct dataUpdateProduct = new DataUpdateProduct(
                1L,
                "Updated Name",
                "Updated Description",
                BigDecimal.valueOf(100.00),
                "SKU123",
                "http://example.com/image.jpg",
                categoryId,
                BigInteger.valueOf(10),
                1L,
                true
        );

        Category enabledCategory = new Category();
        enabledCategory.setId(categoryId);
        enabledCategory.setDisabled(false);

        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        when(categoryRepository.getReferenceById(categoryId)).thenReturn(enabledCategory);

        // Validation
        assertDoesNotThrow(() -> {
            validationCategoryDisabledForUpdateProduct.validation(dataUpdateProduct);
        });
    }

    @Test
    void testValidationWithDisabledCategory() {
        // Setup
        Long categoryId = 2L;
        DataUpdateProduct dataUpdateProduct = new DataUpdateProduct(
                1L,
                "Updated Name",
                "Updated Description",
                BigDecimal.valueOf(100.00),
                "SKU123",
                "http://example.com/image.jpg",
                categoryId,
                BigInteger.valueOf(10),
                1L,
                true
        );

        Category disabledCategory = new Category();
        disabledCategory.setId(categoryId);
        disabledCategory.setDisabled(true);

        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        when(categoryRepository.getReferenceById(categoryId)).thenReturn(disabledCategory);
        // Validation
        assertThrows(ValidationException.class, () -> {
            validationCategoryDisabledForUpdateProduct.validation(dataUpdateProduct);
        });
    }

    @Test
    void testValidationWithNonExistingCategory() {
        // Setup
        Long categoryId = 3L;
        DataUpdateProduct dataUpdateProduct = new DataUpdateProduct(
                1L,
                "Updated Name",
                "Updated Description",
                BigDecimal.valueOf(100.00),
                "SKU123",
                "http://example.com/image.jpg",
                categoryId,
                BigInteger.valueOf(10),
                1L,
                true
        );

        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        // Validation
        assertDoesNotThrow(() -> {
            validationCategoryDisabledForUpdateProduct.validation(dataUpdateProduct);
        });
    }

}
