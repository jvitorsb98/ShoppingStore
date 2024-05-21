package br.com.cepedi.ShoppingStore.service.category.validations.update;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.records.category.input.DataUpdateCategory;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryIsDisabledForUpdateTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryIsDisabledForUpdate categoryIsDisabledForUpdate;

    public CategoryIsDisabledForUpdateTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidationWithDisabledCategory() {
        // Setup
        Long categoryId = 1L;
        DataUpdateCategory dataUpdateCategory = new DataUpdateCategory(categoryId, "Updated Name", "Updated Description");
        Category disabledCategory = new Category();
        disabledCategory.setId(categoryId);
        disabledCategory.setDisabled(true);

        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        when(categoryRepository.getReferenceById(categoryId)).thenReturn(disabledCategory);

        // Validation
        assertThrows(ValidationException.class, () -> {
            categoryIsDisabledForUpdate.validation(dataUpdateCategory);
        });
    }

    @Test
    void testValidationWithEnabledCategory() {
        // Setup
        Long categoryId = 2L;
        DataUpdateCategory dataUpdateCategory = new DataUpdateCategory(categoryId, "Updated Name", "Updated Description");
        Category enabledCategory = new Category();
        enabledCategory.setId(categoryId);
        enabledCategory.setDisabled(false);

        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        when(categoryRepository.getReferenceById(categoryId)).thenReturn(enabledCategory);

        // Validation
        assertDoesNotThrow(() -> {
            categoryIsDisabledForUpdate.validation(dataUpdateCategory);
        });
    }

    @Test
    void testValidationWithNonExistingCategory() {
        // Setup
        Long categoryId = 3L;
        DataUpdateCategory dataUpdateCategory = new DataUpdateCategory(categoryId, "Updated Name", "Updated Description");

        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        // Validation
        assertDoesNotThrow(() -> {
            categoryIsDisabledForUpdate.validation(dataUpdateCategory);
        });
    }
}
