package br.com.cepedi.ShoppingStore.service.product;

import br.com.cepedi.ShoppingStore.model.entitys.Brand;
import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.records.product.details.DataProductDetails;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;
import br.com.cepedi.ShoppingStore.repository.BrandRepository;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import br.com.cepedi.ShoppingStore.service.product.validations.cancel.ValidationDisabledProduct;
import br.com.cepedi.ShoppingStore.service.product.validations.register.ValidationRegisterProduct;
import br.com.cepedi.ShoppingStore.service.product.validations.update.ValidationUpdateProduct;
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
@DataJpaTest
@TestMethodOrder(MethodOrderer.Random.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Test ProductService methods")
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Mock
    private List<ValidationRegisterProduct> validatorsRegister;

    @Mock
    private List<ValidationUpdateProduct> validatorsUpdateProduct;

    @Mock
    private List<ValidationDisabledProduct> validatorsCancelProduct;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
    }

    public DataRegisterProduct createRandomData() {
        return new DataRegisterProduct(
                faker.commerce().productName(),
                faker.lorem().sentence(),
                BigDecimal.valueOf(faker.number().randomDouble(2, 1, 1000)),
                faker.lorem().characters(10),
                faker.internet().url(),
                faker.number().randomNumber(),
                BigInteger.valueOf(faker.number().randomNumber()),
                faker.number().randomNumber(),
                faker.bool().bool()
        );
    }

    public Product createRandomProduct() {
        DataRegisterProduct data = createRandomData();
        Category category = createRandomCategory(1L);
        Brand brand = createRandomBrand(1L);
        return new Product(
                data,
                category,
                brand
        );
    }

    @Test
    public void testRegister() {
        // Mock data
        DataRegisterProduct data = createRandomData();

        // Cria uma categoria fictícia com um ID específico
        long categoryId = 37L; // ou qualquer outro ID que você deseja usar
        Category category = createRandomCategory(categoryId);

        long brandId = 42L; // ou qualquer outro ID que você deseja usar
        Brand brand = createRandomBrand(brandId);

        // Define o comportamento do stubbing do método getReferenceById com o mesmo ID da categoria criada
        when(categoryRepository.getReferenceById(anyLong())).thenReturn(category);
        when(brandRepository.getReferenceById(anyLong())).thenReturn(brand);

        Product savedProduct = new Product(data, category, brand);
        when(productRepository.save(any())).thenReturn(savedProduct);

        // Test
        DataProductDetails result = productService.register(data);

        // Verify
        assertNotNull(result);
        assertEquals(savedProduct.getName(), result.name());
        assertEquals(savedProduct.getDescription(), result.description());
        assertEquals(savedProduct.getPrice(), result.price());
        assertEquals(savedProduct.getSku(), result.sku());
        assertEquals(savedProduct.getImageUrl(), result.imageUrl());
        assertEquals(savedProduct.getQuantity(), result.quantity());
        assertEquals(savedProduct.getBrand().getId(), result.brandId());
        assertEquals(savedProduct.getFeatured(), result.featured());
        assertEquals(savedProduct.getCategory().getId(), result.categoryId());
    }

    @Test
    void listTestWithSucces(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        List<Product> products = new ArrayList<>();
        products.add(createRandomProduct());

        Page<Product> page = new PageImpl<>(products, pageable, products.size());
        when(productRepository.findAll(pageable)).thenReturn(page);

        Page<DataProductDetails> result = productService.list(pageable);

        verify(productRepository, times(1)).findAll(pageable);

        List<DataProductDetails> expectedDetails = result.getContent();
        assertEquals(1, expectedDetails.size());
        assertEquals(false, expectedDetails.get(0).disabled());
    }

    @Test
    void listDeactivatedTestWithSucces(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        List<Product> products = new ArrayList<>();
        Product product = createRandomProduct();
        product.disable();
        products.add(product);

        Page<Product> page = new PageImpl<>(products, pageable, products.size());
        when(productRepository.findAllByDisabledTrue(pageable)).thenReturn(page);

        Page<DataProductDetails> result = productService.listDeactivated(pageable);

        verify(productRepository, times(1)).findAllByDisabledTrue(pageable);

        List<DataProductDetails> expectedDetails = result.getContent();
        assertEquals(1, expectedDetails.size());
        assertEquals(true, expectedDetails.get(0).disabled());
    }

    @Test
    void detailsProductTestWithSuccess(){
        Product product = createRandomProduct();
        when(productRepository.getReferenceById(anyLong())).thenReturn(product);

        DataProductDetails result = productService.detailsProduct(1L);

        assertEquals(product.getName(), result.name());
        assertEquals(product.getDisabled(), result.disabled());
    }

    @Test
    void listProductCategoryTestWithSuccess() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        Category category = createRandomCategory(1L);
        Product product = createRandomProduct();

        List<Product> products = new ArrayList<>();
        products.add(product);

        Page<Product> page = new PageImpl<>(products, pageable, products.size());
        when(productRepository.findAllByCategoryId(product.getId(), pageable)).thenReturn(page);

        Page<DataProductDetails> result = productService.detailsProductCategory(product.getId(), pageable);

        verify(productRepository, times(1)).findAllByCategoryId(product.getId(), pageable);

        Page<DataProductDetails> expectedDetails = new PageImpl<>(products
                .stream()
                .map(DataProductDetails::new)
                .toList(), pageable, products.size());

        assertEquals(expectedDetails.getTotalElements(), result.getTotalElements());
        assertEquals(expectedDetails.getTotalPages(), result.getTotalPages());
        for (int i = 0; i < expectedDetails.getTotalElements(); i++) {
            assertEquals(expectedDetails.getContent().get(i).categoryId(), result.getContent().get(i).categoryId());
            assertEquals(expectedDetails.getContent().get(i).name(), result.getContent().get(i).name());
            assertEquals(false, result.getContent().get(i).disabled());
        }
    }

    @Test
    void listProductCategoryAndIsDeactivatedTestWithSuccess() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        Category category = createRandomCategory(1L);
        Product product = createRandomProduct();
        product.disable();

        List<Product> products = new ArrayList<>();
        products.add(product);

        Page<Product> page = new PageImpl<>(products, pageable, products.size());
        when(productRepository.findAllByCategoryIdAndDisabledIsTrue(product.getId(), pageable)).thenReturn(page);

        Page<DataProductDetails> result = productService.detailsProductCategoryAndIsDeactivated(product.getId(), pageable);

        verify(productRepository, times(1)).findAllByCategoryIdAndDisabledIsTrue(product.getId(), pageable);

        Page<DataProductDetails> expectedDetails = new PageImpl<>(products
                .stream()
                .map(DataProductDetails::new)
                .toList(), pageable, products.size());

        assertEquals(expectedDetails.getTotalElements(), result.getTotalElements());
        assertEquals(expectedDetails.getTotalPages(), result.getTotalPages());
        for (int i = 0; i < expectedDetails.getTotalElements(); i++) {
            assertEquals(expectedDetails.getContent().get(i).categoryId(), result.getContent().get(i).categoryId());
            assertEquals(expectedDetails.getContent().get(i).name(), result.getContent().get(i).name());
            assertEquals(true, result.getContent().get(i).disabled());
        }
    }

    @Test
    void updateTestWithSuccess(){
        Category category = createRandomCategory(1L);
        Product product = createRandomProduct();

        when(categoryRepository.getReferenceById(anyLong())).thenReturn(category);
        when(productRepository.getReferenceById(anyLong())).thenReturn(product);
        Long newId = 1L;
        String newName = "alsyeweeeeeee";
        String newDescription = "alsyeasdasdweeeeeee";
        BigDecimal newPrice = BigDecimal.TEN;
        String newSku = "alsyeweeeasdadsdghhjeeee";
        String newImageUrl = "alsyeweeeeee23123123e";
        Long newCategoryId = 1L;
        BigInteger newQuantity = BigInteger.valueOf(123456789L);
        Long newBrandId = 1L;
        boolean newFeastured = true;
        DataUpdateProduct data = new DataUpdateProduct(newId, newName, newDescription, newPrice, newSku, newImageUrl, newCategoryId, newQuantity, newBrandId, newFeastured);
        product.updateDataProduct(data, category);

        DataProductDetails result = productService.updateProduct(data);

        assertEquals(newCategoryId, result.categoryId());
        assertEquals(newBrandId, result.brandId());
    }

    @Test
    void deleteProductTestWithSuccess(){
        Product product = createRandomProduct();

        when(productRepository.getReferenceById(anyLong())).thenReturn(product);
        product.disable();

        productService.deleteProduct(1L);

        assertEquals(true, product.getDisabled());
    }

    public Category createRandomCategory(long id) {
        Category category = new Category();
        category.setId(id);
        category.setName(faker.commerce().department());

        return category;
    }

    public Brand createRandomBrand(long id) {
        Brand brand = new Brand();
        brand.setId(id);
        brand.setName(faker.company().name());

        return brand;
    }
}
