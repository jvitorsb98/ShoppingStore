package br.com.cepedi.ShoppingStore.service.possibleFacets;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.details.DataPossibleFacetsDetails;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataRegisterPossibleFacets;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import br.com.cepedi.ShoppingStore.repository.PossibleFacetsRepository;
import br.com.cepedi.ShoppingStore.service.possibleFacets.validations.register.ValidationRegisterPossibleFacets;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PossibleFacetsServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PossibleFacetsRepository possibleFacetsRepository;

    @Mock
    private ValidationRegisterPossibleFacets validationRegisterPossibleFacets;

    @InjectMocks
    private PossibleFacetsService possibleFacetsService;

    @Before
    public void setUp() {
        // Inicializar a lista de validações manualmente
        List<ValidationRegisterPossibleFacets> validationRegisterPossibleFacetsList = new ArrayList<>();
        validationRegisterPossibleFacetsList.add(validationRegisterPossibleFacets);

        // Usar reflexão para injetar a lista na instância possível de facetas
        possibleFacetsService = new PossibleFacetsService(validationRegisterPossibleFacetsList);

        // Configurar mocks dos repositórios
        when(categoryRepository.getReferenceById(anyLong())).thenReturn(new Category());
        when(possibleFacetsRepository.save(any(PossibleFacets.class))).thenReturn(new PossibleFacets());
    }

    @Test
    public void testRegister_Success() {
        // Configurar categoria simulada
        Category category = new Category();
        category.setId(1L);
        when(categoryRepository.getReferenceById(1L)).thenReturn(category);

        // Dados de entrada para o méto
        DataRegisterPossibleFacets data = new DataRegisterPossibleFacets("Example Name", 1L);

        // Configurar o possível facet salvo
        PossibleFacets savedPossibleFacets = new PossibleFacets(data, category);
        when(possibleFacetsRepository.save(any())).thenReturn(savedPossibleFacets);

        // Chamar o método register
        DataPossibleFacetsDetails result = possibleFacetsService.register(data);

        // Verificar se o resultado não é nulo
        assertNotNull(result);
    }
}
