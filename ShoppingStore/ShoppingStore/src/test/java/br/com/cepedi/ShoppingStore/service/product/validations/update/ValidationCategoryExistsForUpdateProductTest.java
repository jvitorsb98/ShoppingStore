package br.com.cepedi.ShoppingStore.service.product.validations.update;

import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidationCategoryExistsForUpdateProductTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ValidationCategoryExistsForUpdateProduct validationCategoryExistsForUpdateProduct;

    public ValidationCategoryExistsForUpdateProductTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidationWithExistingCategory() {
        // Setup
        Long categoryId = 1L;
        DataUpdateProduct dataUpdateProduct = new DataUpdateProduct(
                1L,
                "Updated Name",
                "Updated Description",
                null,
                null,
                null,
                categoryId,
                null,
                null,
                true
        );

        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        // Validation
        assertDoesNotThrow(() -> {
            validationCategoryExistsForUpdateProduct.validation(dataUpdateProduct);
        });
    }

    @Test
    void testValidationWithNonExistingCategory() {
        // Setup
        Long categoryId = 2L;
        DataUpdateProduct dataUpdateProduct = new DataUpdateProduct(
                1L,
                "Updated Name",
                "Updated Description",
                null,
                null,
                null,
                categoryId,
                null,
                null,
                true
        );

        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        // Validation
        assertThrows(ValidationException.class, () -> {
            validationCategoryExistsForUpdateProduct.validation(dataUpdateProduct);
        });
    }

}
