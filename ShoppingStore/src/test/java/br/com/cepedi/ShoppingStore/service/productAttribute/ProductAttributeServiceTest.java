package br.com.cepedi.ShoppingStore.service.productAttribute;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.entitys.ProductAttribute;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.details.DataProductAttributeDetails;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataRegisterProductAttribute;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataUpdateProductAttribute;
import br.com.cepedi.ShoppingStore.repository.ProductAttributeRepository;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import br.com.cepedi.ShoppingStore.service.productAttribute.validations.disabled.ValidationDisabledProductAttribute;
import br.com.cepedi.ShoppingStore.service.productAttribute.validations.register.ValidationRegisterProductAttribute;
import br.com.cepedi.ShoppingStore.service.productAttribute.validations.update.ValidationUpdateProductAttribute;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.Random.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Test ProductAttributeService methods")
@ExtendWith(MockitoExtension.class)
public class ProductAttributeServiceTest {

    @Mock
    private ProductAttributeRepository productAttributeRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private List<ValidationRegisterProductAttribute> validationsRegister;

    @Mock
    private List<ValidationDisabledProductAttribute> validationsDisabled;

    @Mock
    private List<ValidationUpdateProductAttribute> validationsUpdate;

    @InjectMocks
    private ProductAttributeService productAttributeService;

    private Product product;

    private ProductAttribute productAttribute;

    private DataRegisterProductAttribute data;

    private DataProductAttributeDetails expected;

    private static final Faker faker = new Faker();

    private static class ModelMocks{
        public static Long idProduct = faker.random().nextLong();
        public static Long idProductAttribute = faker.random().nextLong();
        public static String nameProductAttribute = faker.name().fullName();
        public static String valueProductAttribute = faker.lorem().word();
    }

    @BeforeEach
    void setUp(){
        product = new Product();
        product.setId(ModelMocks.idProduct);
        data = new DataRegisterProductAttribute(ModelMocks.nameProductAttribute, ModelMocks.valueProductAttribute, ModelMocks.idProduct);
        productAttribute = new ProductAttribute();
        productAttribute.setId(ModelMocks.idProductAttribute);
        productAttribute.setName(ModelMocks.nameProductAttribute);
        productAttribute.setValue(ModelMocks.valueProductAttribute);
        productAttribute.setProduct(product);
        productAttribute.enable();
        expected = new DataProductAttributeDetails(ModelMocks.idProductAttribute, productAttribute.getName(), productAttribute.getValue(), productAttribute.getProduct().getId(), false);
    }

    @Test
    void registerTestWithSucces(){
        when(productRepository.getReferenceById(anyLong())).thenReturn(product);
        when(productAttributeRepository.save(any(ProductAttribute.class))).thenReturn(productAttribute);

        DataProductAttributeDetails result = productAttributeService.register(data);

        verify(validationsRegister, times(1)).forEach(any());
        verify(productRepository, times(1)).getReferenceById(ModelMocks.idProduct);
        verify(productAttributeRepository, times(1)).save(any(ProductAttribute.class));
        assertEquals(expected.productId(), result.productId());
        assertEquals(expected.value(), result.value());
        assertEquals(expected.name(), result.name());
    }

    @Test
    void listTestWithSucces(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        List<ProductAttribute> productAttributes = new ArrayList<>();
        productAttributes.add(productAttribute);

        Page<ProductAttribute> page = new PageImpl<>(productAttributes, pageable, productAttributes.size());
        when(productAttributeRepository.findAllByDisabledFalse(pageable)).thenReturn(page);

        Page<DataProductAttributeDetails> result = productAttributeService.list(pageable);

        verify(productAttributeRepository, times(1)).findAllByDisabledFalse(pageable);

        List<DataProductAttributeDetails> expectedDetails = result.getContent();
        assertEquals(1, expectedDetails.size());
        assertEquals(ModelMocks.nameProductAttribute, expectedDetails.get(0).name());
        assertEquals(ModelMocks.valueProductAttribute, expectedDetails.get(0).value());
        assertEquals(false, expectedDetails.get(0).disabled());
    }

    @Test
    void listDeactivatedTestWithSucces(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        List<ProductAttribute> productAttributes = new ArrayList<>();
        productAttribute.disable();
        productAttributes.add(productAttribute);

        Page<ProductAttribute> page = new PageImpl<>(productAttributes, pageable, productAttributes.size());
        when(productAttributeRepository.findAllByDisabledTrue(pageable)).thenReturn(page);

        Page<DataProductAttributeDetails> result = productAttributeService.listDeactivated(pageable);

        verify(productAttributeRepository, times(1)).findAllByDisabledTrue(pageable);

        List<DataProductAttributeDetails> expectedDetails = result.getContent();
        assertEquals(1, expectedDetails.size());
        assertEquals(ModelMocks.nameProductAttribute, expectedDetails.get(0).name());
        assertEquals(ModelMocks.valueProductAttribute, expectedDetails.get(0).value());
        assertEquals(true, expectedDetails.get(0).disabled());
    }

    @Test
    void listByProductIdTestWithSuccess() {
        Long productId = ModelMocks.idProduct;

        List<ProductAttribute> productAttributes = new ArrayList<>();
        productAttributes.add(productAttribute);

        when(productAttributeRepository.findAllByProductIdAndDisabledIsFalse(productId)).thenReturn(productAttributes);

        List<DataProductAttributeDetails> result = productAttributeService.listByProductId(productId);

        verify(productAttributeRepository, times(1)).findAllByProductIdAndDisabledIsFalse(productId);

        List<DataProductAttributeDetails> expectedDetails = productAttributes.stream()
                .map(DataProductAttributeDetails::fromEntity)
                .toList();

        assertEquals(expectedDetails.size(), result.size());
        for (int i = 0; i < expectedDetails.size(); i++) {
            assertEquals(ModelMocks.nameProductAttribute, result.get(i).name());
            assertEquals(ModelMocks.valueProductAttribute, result.get(i).value());
            assertEquals(false, result.get(i).disabled());
        }
    }

    @Test
    void listByProductIdDeactivatedTestWithSuccess() {
        Long productId = ModelMocks.idProduct;

        List<ProductAttribute> productAttributes = new ArrayList<>();
        productAttribute.disable();
        productAttributes.add(productAttribute);

        when(productAttributeRepository.findAllByProductIdAndDisabledIsTrue(productId)).thenReturn(productAttributes);

        List<DataProductAttributeDetails> result = productAttributeService.listByProductIdDeactivated(productId);

        verify(productAttributeRepository, times(1)).findAllByProductIdAndDisabledIsTrue(productId);

        List<DataProductAttributeDetails> expectedDetails = productAttributes.stream()
                .map(DataProductAttributeDetails::fromEntity)
                .toList();

        assertEquals(expectedDetails.size(), result.size());
        for (int i = 0; i < expectedDetails.size(); i++) {
            assertEquals(ModelMocks.nameProductAttribute, result.get(i).name());
            assertEquals(ModelMocks.valueProductAttribute, result.get(i).value());
            assertEquals(true, result.get(i).disabled());
        }
    }

    @Test
    void detailsTestWithSuccess(){
        when(productAttributeRepository.getReferenceById(ModelMocks.idProductAttribute)).thenReturn(productAttribute);

        DataProductAttributeDetails result = productAttributeService.details(ModelMocks.idProductAttribute);

        assertEquals(expected.name(), result.name());
        assertEquals(expected.value(), result.value());
    }

    @Test
    void updateTestWithSuccess(){
        when(productAttributeRepository.getReferenceById(anyLong())).thenReturn(productAttribute);
        String newName = "alsyjhgfdb";
        String newValue = "alsyeweeeeeee";
        DataUpdateProductAttribute data = new DataUpdateProductAttribute(ModelMocks.idProductAttribute, newName, newValue);
        productAttribute.updateProductAttribute(data);
        when(productAttributeRepository.save(any(ProductAttribute.class))).thenReturn(productAttribute);


        DataProductAttributeDetails result = productAttributeService.update(data);

        assertEquals(newName, result.name());
        assertEquals(newValue, result.value());
    }

    @Test
    void disabledTestWithSuccess(){
        when(productAttributeRepository.getReferenceById(anyLong())).thenReturn(productAttribute);
        productAttribute.disable();
        when(productAttributeRepository.save(any(ProductAttribute.class))).thenReturn(productAttribute);

        productAttributeService.disabled(ModelMocks.idProductAttribute);

        assertEquals(true, productAttribute.getDisabled());
    }
}
