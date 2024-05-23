package br.com.cepedi.ShoppingStore.service.possibleFacets.validations.register;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataRegisterPossibleFacets;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ValidationCategoryIsDisabledTest {

    @InjectMocks
    private ValidationCategoryIsDisabled validator;

    @Mock
    private CategoryRepository categoryRepository;

    private DataRegisterPossibleFacets validData;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        validData = new DataRegisterPossibleFacets("Test", 1L);
    }

    @Test
    public void testValidationWithNonDisabledCategory() {
        when(categoryRepository.existsById(1L)).thenReturn(true);
        Category category = new Category();
        category.setDisabled(false);
        when(categoryRepository.getReferenceById(1L)).thenReturn(category);

        assertDoesNotThrow(() -> validator.validation(validData));
    }

    @Test
    public void testValidationWithDisabledCategory() {
        when(categoryRepository.existsById(1L)).thenReturn(true);
        Category category = new Category();
        category.setDisabled(true);
        when(categoryRepository.getReferenceById(1L)).thenReturn(category);

        assertThrows(ValidationException.class, () -> validator.validation(validData));
    }

    @Test
    public void testValidationWithNonExistentCategory() {
        when(categoryRepository.existsById(2L)).thenReturn(false);

        assertDoesNotThrow(() -> validator.validation(new DataRegisterPossibleFacets("Test", 2L)));
    }
}


