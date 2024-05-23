/*package br.com.cepedi.ShoppingStore.service.possibleFacets;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.details.DataPossibleFacetsDetails;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataRegisterPossibleFacets;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import br.com.cepedi.ShoppingStore.repository.PossibleFacetsRepository;
import br.com.cepedi.ShoppingStore.service.possibleFacets.validations.register.ValidationRegisterPossibleFacets;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PossibleFacetsServiceTest {

    @InjectMocks
    private PossibleFacetsService possibleFacetsService;

    @Mock
    private List<ValidationRegisterPossibleFacets> validationRegisterPossibleFacetsList;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PossibleFacetsRepository possibleFacetsRepository;

    @Test
    public void testRegisterWithValidData() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
        DataRegisterPossibleFacets validData = new DataRegisterPossibleFacets("New Possible Facet", 1L);
        Category category = new Category();
        when(categoryRepository.getReferenceById(1L)).thenReturn(category);
        when(possibleFacetsRepository.save(any())).thenReturn(new PossibleFacets());


        DataPossibleFacetsDetails result = possibleFacetsService.register(validData);

        assertEquals("New Possible Facet", result.name());
        assertEquals(1L, result.idCategory());
    }
} */
