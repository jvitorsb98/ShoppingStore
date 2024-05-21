package br.com.cepedi.ShoppingStore.service.product;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.records.product.details.DataProductDetails;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import br.com.cepedi.ShoppingStore.service.product.validations.cancel.ValidationCancelProduct;
import br.com.cepedi.ShoppingStore.service.product.validations.register.ValidationProduct;
import br.com.cepedi.ShoppingStore.service.product.validations.update.ValidationUpdateProduct;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Mock
    private List<ValidationProduct> validatorsRegister;

    @Mock
    private List<ValidationUpdateProduct> validatorsUpdateProduct;

    @Mock
    private List<ValidationCancelProduct> validatorsCancelProduct;

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
                faker.company().name(),
                faker.bool().bool()
        );
    }

    @Test
    public void testRegister() {
        // Mock data
        DataRegisterProduct data = createRandomData();

        // Cria uma categoria fictícia com um ID específico
        long categoryId = 37L; // ou qualquer outro ID que você deseja usar
        Category category = createRandomCategory(categoryId);

        // Define o comportamento do stubbing do método getReferenceById com o mesmo ID da categoria criada
        when(categoryRepository.getReferenceById(anyLong())).thenReturn(category);

        Product savedProduct = new Product(data, category);
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
        assertEquals(savedProduct.getManufacturer(), result.manufacturer());
        assertEquals(savedProduct.getFeatured(), result.featured());
        assertEquals(savedProduct.getCategory().getId(), result.categoryId());
    }

    public Category createRandomCategory(long id) {
        Category category = new Category();
        category.setId(id);
        category.setName(faker.commerce().department());

        return category;
    }
}
