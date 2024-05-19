package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataRegisterPossibleFacets;
import com.github.javafaker.Faker;

import br.com.cepedi.ShoppingStore.controller.PossibleFacetsController;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import br.com.cepedi.ShoppingStore.service.PossibleFacetsService;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.Random.class)
@DisplayName("Test entity possibleFacetsTest")
public class PossibleFacetsTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PossibleFacetsService possibleFacetsService;

    private Faker faker = new Faker();

    @InjectMocks
    private PossibleFacetsController possibleFacetsController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    public PossibleFacets createMockPossibleFacets() {
        PossibleFacets possibleFacets = new PossibleFacets();
        possibleFacets.setId(faker.number().randomNumber());
        possibleFacets.setName(faker.lorem().word());
        Category category = new Category();
        category.setId(faker.number().randomNumber());
        category.setName(faker.lorem().word());
        category.setDescription(faker.lorem().sentence());
        possibleFacets.setCategory(category);
        return possibleFacets;
    }

    @Test
    public void testSavePossibleFacets() {
        // Given
        PossibleFacets possibleFacets = createMockPossibleFacets();

        // When
        when(possibleFacetsService.savePossibleFacets(possibleFacets)).thenReturn(possibleFacets);
        PossibleFacets savedPossibleFacets = possibleFacetsController.savePossibleFacets(possibleFacets);

        // Then
        assertEquals(possibleFacets.getId(), savedPossibleFacets.getId());
        assertEquals(possibleFacets.getName(), savedPossibleFacets.getName());
        assertEquals(possibleFacets.getCategory(), savedPossibleFacets.getCategory());
    }

    @Test
    public void testConstructor() {
        // Dados de exemplo
        DataRegisterPossibleFacets data = new DataRegisterPossibleFacets("Test Name");
        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");

        // Criar uma instância de PossibleFacets usando o construtor
        PossibleFacets possibleFacets = new PossibleFacets(data, category);

        // Verificar se os valores foram atribuídos corretamente
        assertEquals("Test Name", possibleFacets.getName());
        assertEquals(category, possibleFacets.getCategory());
    }

}

