package br.com.cepedi.ShoppingStore.service.possibleFacets.validations.update;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataUpdatePossibleFacets;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CategoryDisabledForPossibleFacetsUpdateTest {

    @InjectMocks
    private CategoryDisabledForPossibleFacetsUpdate validator;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testValidationWithDisabledCategory() {
        long categoryId = 1L; // ID da categoria existente
        DataUpdatePossibleFacets data = new DataUpdatePossibleFacets(1L, "Updated", categoryId);
        Category category = new Category();
        category.setDisabled(true);
        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        when(categoryRepository.getReferenceById(categoryId)).thenReturn(category);

        assertThrows(ValidationException.class, () -> validator.validation(data));
    }

    @Test
    public void testValidationWithEnabledCategory() {
        long categoryId = 1L; // ID da categoria existente
        DataUpdatePossibleFacets data = new DataUpdatePossibleFacets(1L, "Updated", categoryId);
        Category category = new Category();
        category.setDisabled(false);
        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        when(categoryRepository.getReferenceById(categoryId)).thenReturn(category);

        // Não deve lançar exceção, pois a categoria está habilitada
        validator.validation(data);
    }

    @Test
    public void testValidationWithNonExistingCategory() {
        long categoryId = 2L; // ID de uma categoria que não existe
        DataUpdatePossibleFacets data = new DataUpdatePossibleFacets(1L, "Updated", categoryId);
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        // Não deve lançar exceção, pois a categoria não existe
        validator.validation(data);
    }
}
