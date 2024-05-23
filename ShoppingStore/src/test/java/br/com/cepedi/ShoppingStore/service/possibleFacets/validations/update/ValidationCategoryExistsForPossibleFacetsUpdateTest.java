package br.com.cepedi.ShoppingStore.service.possibleFacets.validations.update;

import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataUpdatePossibleFacets;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidationCategoryExistsForPossibleFacetsUpdateTest {

    @InjectMocks
    private ValidationCategoryExistsForPossibleFacetsUpdate validator;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testValidationWithExistingCategory() {
        long categoryId = 1L; // ID da categoria existente
        DataUpdatePossibleFacets data = new DataUpdatePossibleFacets(1L, "Updated", categoryId);
        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        assertThrows(ValidationException.class, () -> validator.validation(data));
    }

    @Test
    public void testValidationWithNonExistingCategory() {
        long categoryId = 2L; // ID de uma categoria que não existe
        DataUpdatePossibleFacets data = new DataUpdatePossibleFacets(1L, "Updated", categoryId);
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        validator.validation(data); // Não deve lançar exceção
    }
}

