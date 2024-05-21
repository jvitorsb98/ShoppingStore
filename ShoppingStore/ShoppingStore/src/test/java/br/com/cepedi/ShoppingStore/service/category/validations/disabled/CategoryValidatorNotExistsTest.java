package br.com.cepedi.ShoppingStore.service.category.validations.disabled;

import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryValidatorNotExistsTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryValidatorNotExists categoryValidatorNotExists;

    public CategoryValidatorNotExistsTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidationWithExistingCategory() {
        // Setup
        Long categoryId = 1L;
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        // Validation
        assertThrows(ValidationException.class, () -> {
            categoryValidatorNotExists.validation(categoryId);
        });
    }

    @Test
    void testValidationWithNonExistingCategory() {
        // Setup
        Long categoryId = 2L;
        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        // Validation
        assertDoesNotThrow(() -> {
            categoryValidatorNotExists.validation(categoryId);
        });
    }
}
