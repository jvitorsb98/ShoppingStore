package br.com.cepedi.ShoppingStore.service.possibleFacets;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataUpdatePossibleFacets;
import br.com.cepedi.ShoppingStore.service.possibleFacets.validations.disabled.ValidationDisabledPossibleFacets;
import br.com.cepedi.ShoppingStore.service.possibleFacets.validations.update.ValidationUpdatePossibleFacets;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.details.DataPossibleFacetsDetails;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataRegisterPossibleFacets;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import br.com.cepedi.ShoppingStore.repository.PossibleFacetsRepository;
import br.com.cepedi.ShoppingStore.service.possibleFacets.validations.register.ValidationRegisterPossibleFacets;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.Random.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Test PossibleFacetsService methods")
@ExtendWith(MockitoExtension.class)
public class PossibleFacetsServiceTest {

    @InjectMocks
    private PossibleFacetsService possibleFacetsService;

    @Mock
    private List<ValidationRegisterPossibleFacets> validationRegisterPossibleFacetsList;

    @Mock
    private List<ValidationUpdatePossibleFacets> validationUpdatePossibleFacetsList;

    @Mock
    private List<ValidationDisabledPossibleFacets> validationDisabledPossibleFacetsList;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PossibleFacetsRepository possibleFacetsRepository;

    private Category category;
    private PossibleFacets possibleFacets;
    private DataRegisterPossibleFacets data;
    private DataPossibleFacetsDetails expected;

    private static final Faker faker = new Faker();

    private static class ModelMocks{
        public static Long idCategory = faker.random().nextLong();
        public static Long idPossibleFacets = faker.random().nextLong();
        public static String namePossibleFacets = faker.name().fullName();
    }

    @BeforeEach
    void setUp(){
        category = new Category();
        category.setId(ModelMocks.idCategory);
        data = new DataRegisterPossibleFacets(ModelMocks.namePossibleFacets,ModelMocks.idCategory);
        possibleFacets = new PossibleFacets();
        possibleFacets.setId(ModelMocks.idPossibleFacets);
        possibleFacets.setName(ModelMocks.namePossibleFacets);
        possibleFacets.setCategory(category);
        possibleFacets.enable();
        expected = new DataPossibleFacetsDetails(ModelMocks.idPossibleFacets, possibleFacets.getName(), possibleFacets.getCategory().getId(), false);
    }

    @Test
    void registerTestWithSucces(){
        when(categoryRepository.getReferenceById(anyLong())).thenReturn(category);
        when(possibleFacetsRepository.save(any(PossibleFacets.class))).thenReturn(possibleFacets);

        DataPossibleFacetsDetails result = possibleFacetsService.register(data);

        verify(validationRegisterPossibleFacetsList, times(1)).forEach(any());
        verify(categoryRepository, times(1)).getReferenceById(ModelMocks.idCategory);
        verify(possibleFacetsRepository, times(1)).save(any(PossibleFacets.class));
        assertEquals(expected.idCategory(), result.idCategory());
        assertEquals(expected.disabled(), result.disabled());
        assertEquals(expected.name(), result.name());
    }

    @Test
    void listAllTestWithSucces(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        List<PossibleFacets> possibleFacetsList = new ArrayList<>();
        possibleFacetsList.add(possibleFacets);

        Page<PossibleFacets> page = new PageImpl<>(possibleFacetsList, pageable, possibleFacetsList.size());
        when(possibleFacetsRepository.findAll(pageable)).thenReturn(page);

        Page<DataPossibleFacetsDetails> result = possibleFacetsService.listAll(pageable);

        verify(possibleFacetsRepository, times(1)).findAll(pageable);

        List<DataPossibleFacetsDetails> expectedDetails = result.getContent();
        assertEquals(1, expectedDetails.size());
        assertEquals(ModelMocks.namePossibleFacets, expectedDetails.get(0).name());
        assertEquals(ModelMocks.idCategory, expectedDetails.get(0).idCategory());
        assertEquals(false, expectedDetails.get(0).disabled());
    }

    @Test
    void listAllDeactivatedTestWithSucces(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        List<PossibleFacets> possibleFacetsList = new ArrayList<>();
        possibleFacets.disable();
        possibleFacetsList.add(possibleFacets);

        Page<PossibleFacets> page = new PageImpl<>(possibleFacetsList, pageable, possibleFacetsList.size());
        when(possibleFacetsRepository.findAllByDisabledTrue(pageable)).thenReturn(page);

        Page<DataPossibleFacetsDetails> result = possibleFacetsService.listAllDeactivated(pageable);

        verify(possibleFacetsRepository, times(1)).findAllByDisabledTrue(pageable);

        List<DataPossibleFacetsDetails> expectedDetails = result.getContent();
        assertEquals(1, expectedDetails.size());
        assertEquals(ModelMocks.namePossibleFacets, expectedDetails.get(0).name());
        assertEquals(ModelMocks.idCategory, expectedDetails.get(0).idCategory());
        assertEquals(true, expectedDetails.get(0).disabled());
    }

    @Test
    void findByIdTestWithSuccess(){
        when(possibleFacetsRepository.findById(ModelMocks.idPossibleFacets)).thenReturn(Optional.of(possibleFacets));

        DataPossibleFacetsDetails result = possibleFacetsService.findById(ModelMocks.idPossibleFacets);

        assertEquals(ModelMocks.namePossibleFacets, result.name());
        assertEquals(ModelMocks.idCategory, result.idCategory());
        assertEquals(false, result.disabled());
    }

    @Test
    void findByIdTestUnsuccessfully(){
        when(possibleFacetsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> possibleFacetsService.findById(1L), "PossibleFacets not found");
    }

    @Test
    void findByCategoryTestWithSuccess() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        Long categoryId = ModelMocks.idCategory;

        List<PossibleFacets> possibleFacetsList = new ArrayList<>();
        possibleFacetsList.add(possibleFacets);

        Page<PossibleFacets> page = new PageImpl<>(possibleFacetsList, pageable, possibleFacetsList.size());
        when(possibleFacetsRepository.findByCategory(categoryId, pageable)).thenReturn(page);

        Page<DataPossibleFacetsDetails> result = possibleFacetsService.findByCategory(categoryId, pageable);

        verify(possibleFacetsRepository, times(1)).findByCategory(categoryId, pageable);

        Page<DataPossibleFacetsDetails> expectedDetails = new PageImpl<>(possibleFacetsList
                .stream()
                .map(DataPossibleFacetsDetails::new)
                .toList(), pageable, possibleFacetsList.size());

        assertEquals(expectedDetails.getTotalElements(), result.getTotalElements());
        assertEquals(expectedDetails.getTotalPages(), result.getTotalPages());
        for (int i = 0; i < expectedDetails.getTotalElements(); i++) {
            assertEquals(ModelMocks.namePossibleFacets, expectedDetails.getContent().get(0).name());
            assertEquals(ModelMocks.idCategory, expectedDetails.getContent().get(0).idCategory());
            assertEquals(false, expectedDetails.getContent().get(0).disabled());
        }
    }

    @Test
    void findByCategoryAndIsDisabledTestWithSuccess() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        Long categoryId = ModelMocks.idCategory;

        List<PossibleFacets> possibleFacetsList = new ArrayList<>();
        possibleFacets.disable();
        possibleFacetsList.add(possibleFacets);

        Page<PossibleFacets> page = new PageImpl<>(possibleFacetsList, pageable, possibleFacetsList.size());
        when(possibleFacetsRepository.findByCategoryAndIsDisabled(categoryId, pageable)).thenReturn(page);

        Page<DataPossibleFacetsDetails> result = possibleFacetsService.findByCategoryAndIsDisabled(categoryId, pageable);

        verify(possibleFacetsRepository, times(1)).findByCategoryAndIsDisabled(categoryId, pageable);

        Page<DataPossibleFacetsDetails> expectedDetails = new PageImpl<>(possibleFacetsList
                .stream()
                .map(DataPossibleFacetsDetails::new)
                .toList(), pageable, possibleFacetsList.size());

        assertEquals(expectedDetails.getTotalElements(), result.getTotalElements());
        assertEquals(expectedDetails.getTotalPages(), result.getTotalPages());
        for (int i = 0; i < expectedDetails.getTotalElements(); i++) {
            assertEquals(ModelMocks.namePossibleFacets, expectedDetails.getContent().get(0).name());
            assertEquals(ModelMocks.idCategory, expectedDetails.getContent().get(0).idCategory());
            assertEquals(true, expectedDetails.getContent().get(0).disabled());
        }
    }

    @Test
    void updateTestWithSuccess(){
        when(possibleFacetsRepository.getReferenceById(anyLong())).thenReturn(possibleFacets);
        when(categoryRepository.getReferenceById(anyLong())).thenReturn(category);
        Long newCategoryId = 1L;
        String newName = "alsyeweeeeeee";
        DataUpdatePossibleFacets data = new DataUpdatePossibleFacets(ModelMocks.idPossibleFacets, newName, newCategoryId);

        DataPossibleFacetsDetails result = possibleFacetsService.update(data);

        assertNotEquals(ModelMocks.namePossibleFacets, result.name());
        assertEquals(newName, result.name());
    }

    @Test
    void disableTestWithSuccess(){
        when(possibleFacetsRepository.getReferenceById(anyLong())).thenReturn(possibleFacets);

        possibleFacetsService.disable(ModelMocks.idPossibleFacets);

        assertEquals(true, possibleFacets.getDisabled());
    }
}
