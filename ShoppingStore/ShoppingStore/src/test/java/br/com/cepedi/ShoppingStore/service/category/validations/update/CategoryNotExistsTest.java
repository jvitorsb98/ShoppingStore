package br.com.cepedi.ShoppingStore.service.category.validations.update;

import br.com.cepedi.ShoppingStore.model.records.category.input.DataUpdateCategory;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryNotExistsTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryNotExists categoryNotExists;

    public CategoryNotExistsTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidationWithExistingCategory() {
        // Setup
        Long categoryId = 1L;
        DataUpdateCategory dataUpdateCategory = new DataUpdateCategory(categoryId, "Updated Name", "Updated Description");

        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        // Validation
        assertDoesNotThrow(() -> {
            categoryNotExists.validation(dataUpdateCategory);
        });
    }

    @Test
    void testValidationWithNonExistingCategory() {
        // Setup
        Long categoryId = 2L;
        DataUpdateCategory dataUpdateCategory = new DataUpdateCategory(categoryId, "Updated Name", "Updated Description");

        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        // Validation
        assertThrows(ValidationException.class, () -> {
            categoryNotExists.validation(dataUpdateCategory);
        });
    }
}
