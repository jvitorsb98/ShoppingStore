package br.com.cepedi.ShoppingStore.service.brand;

import br.com.cepedi.ShoppingStore.model.entitys.Brand;
import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;
import br.com.cepedi.ShoppingStore.model.records.brand.details.DataBrandDetails;
import br.com.cepedi.ShoppingStore.model.records.brand.input.DataRegisterBrand;
import br.com.cepedi.ShoppingStore.model.records.brand.input.DataUpdateBrand;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.details.DataPossibleFacetsDetails;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataRegisterPossibleFacets;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataUpdatePossibleFacets;
import br.com.cepedi.ShoppingStore.repository.BrandRepository;
import br.com.cepedi.ShoppingStore.service.brand.validations.disabled.BrandValidatorDisabled;
import br.com.cepedi.ShoppingStore.service.brand.validations.update.BrandValidationUpdate;
import br.com.cepedi.ShoppingStore.service.possibleFacets.PossibleFacetsServiceTest;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
@DisplayName("Test BrandService methods")
@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;
    @Mock
    private List<BrandValidatorDisabled> validationDisabled;
    @Mock
    private List<BrandValidationUpdate> validationUpdate;

    @InjectMocks
    private BrandService brandService;

    private Brand brand;
    private DataRegisterBrand data;
    private DataBrandDetails expected;

    private static final Faker faker = new Faker();

    private static class ModelMocks{
        public static Long idBrand = faker.random().nextLong();
        public static String nameBrand = faker.name().fullName();
    }

    @BeforeEach
    void setUp(){
        data = new DataRegisterBrand(ModelMocks.nameBrand);
        brand = new Brand();
        brand.setId(ModelMocks.idBrand);
        brand.setName(ModelMocks.nameBrand);
        brand.enable();
        expected = new DataBrandDetails(ModelMocks.idBrand, brand.getName(), false);
    }

    @Test
    void registerTestWithSucces(){
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        DataBrandDetails result = brandService.register(data);

        verify(brandRepository, times(1)).save(any(Brand.class));
        assertEquals(expected.name(), result.name());
    }

    @Test
    void updateTestWithSuccess(){
        when(brandRepository.getReferenceById(anyLong())).thenReturn(brand);
        String newName = "alsyeweeeeeee";
        DataUpdateBrand data = new DataUpdateBrand(brand.getId(), newName);

        DataBrandDetails result = brandService.update(data);

        assertNotEquals(ModelMocks.nameBrand, result.name());
        assertEquals(newName, result.name());
    }

    @Test
    void getBrandByIdTestWithSuccess(){
        when(brandRepository.findById(ModelMocks.idBrand)).thenReturn(Optional.of(brand));

        DataBrandDetails result = brandService.getBrandById(ModelMocks.idBrand);

        assertEquals(ModelMocks.nameBrand, result.name());
        assertEquals(false, result.disabled());
    }

    @Test
    void getBrandByIdTestUnsuccessfully(){
        when(brandRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> brandService.getBrandById(1L), "PossibleFacets not found");
    }

    @Test
    void disabledTestWithSuccess(){
        when(brandRepository.getReferenceById(anyLong())).thenReturn(brand);

        brandService.disabled(ModelMocks.idBrand);

        assertEquals(true, brand.getDisabled());
    }

    @Test
    void listAllBrandsTestWithSucces(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        List<Brand> brands = new ArrayList<>();
        brands.add(brand);

        Page<Brand> page = new PageImpl<>(brands, pageable, brands.size());
        when(brandRepository.findAll(pageable)).thenReturn(page);

        Page<DataBrandDetails> result = brandService.listAllBrands(pageable);

        verify(brandRepository, times(1)).findAll(pageable);

        List<DataBrandDetails> expectedDetails = result.getContent();
        assertEquals(1, expectedDetails.size());
        assertEquals(ModelMocks.nameBrand, expectedDetails.get(0).name());
        assertEquals(false, expectedDetails.get(0).disabled());
    }

    @Test
    void listAllBrandsAndDisabledTrueTestWithSucces(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        List<Brand> brands = new ArrayList<>();
        brand.disable();
        brands.add(brand);

        Page<Brand> page = new PageImpl<>(brands, pageable, brands.size());
        when(brandRepository.findAllByDisabledTrue(pageable)).thenReturn(page);

        Page<DataBrandDetails> result = brandService.listAllBrandsAndDisabledTrue(pageable);

        verify(brandRepository, times(1)).findAllByDisabledTrue(pageable);

        List<DataBrandDetails> expectedDetails = result.getContent();
        assertEquals(1, expectedDetails.size());
        assertEquals(ModelMocks.nameBrand, expectedDetails.get(0).name());
        assertEquals(true, expectedDetails.get(0).disabled());
    }
}
