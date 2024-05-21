package br.com.cepedi.ShoppingStore.service.category.validations.disabled;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryAlreadyDisabledForDisabledTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryAlreadyDisabledForDisabled categoryAlreadyDisabledForDisabled;

    public CategoryAlreadyDisabledForDisabledTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidationWithDisabledCategory() {
        // Setup
        Long categoryId = 1L;
        Category disabledCategory = new Category();
        disabledCategory.setId(categoryId);
        disabledCategory.setDisabled(true);

        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        when(categoryRepository.getReferenceById(categoryId)).thenReturn(disabledCategory);

        // Validation
        assertThrows(ValidationException.class, () -> {
            categoryAlreadyDisabledForDisabled.validation(categoryId);
        });
    }

    @Test
    void testValidationWithEnabledCategory() {
        // Setup
        Long categoryId = 2L;
        Category enabledCategory = new Category();
        enabledCategory.setId(categoryId);
        enabledCategory.setDisabled(false);

        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        when(categoryRepository.getReferenceById(categoryId)).thenReturn(enabledCategory);

        // Validation
        assertDoesNotThrow(() -> {
            categoryAlreadyDisabledForDisabled.validation(categoryId);
        });
    }
}
