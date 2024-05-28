package br.com.cepedi.ShoppingStore.service.productRating;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.entitys.ProductRating;
import br.com.cepedi.ShoppingStore.model.records.productRating.details.DataProductRatingDetails;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.repository.ProductRatingRepository;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import br.com.cepedi.ShoppingStore.service.productRating.validation.disabled.ValidationDisabledProductRating;
import br.com.cepedi.ShoppingStore.service.productRating.validation.register.ValidationProductRatingRegister;
import br.com.cepedi.ShoppingStore.service.productRating.validation.update.ValidationUpdateProductRating;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.Random.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Test ProductRatingService methods")
@ExtendWith(MockitoExtension.class)
public class ProductRatingServiceTest {

    @Mock
    private ProductRatingRepository productRatingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductRatingService productRatingService;

    @Mock
    private List<ValidationProductRatingRegister> validationsRegister;

    @Mock
    private List<ValidationDisabledProductRating> validationsDisabled;

    @Mock
    private List<ValidationUpdateProductRating> validationsUpdate;

    private Product product;
    private ProductRating productRating;
    private User user;

    private DataRegisterProductRating data;
    private DataProductRatingDetails expected;

    private static final Faker faker = new Faker();

    private static class ModelMocks{
        public static Long idProduct = faker.random().nextLong();
        public static Long idProductRating = faker.random().nextLong();
        public static Long idUser = faker.random().nextLong();
        public static BigDecimal ratingStarsProductRating = BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100));
        public static String review = faker.lorem().sentence();
    }

    @BeforeEach
    void setUp(){
        product = new Product();
        product.setId(ModelMocks.idProduct);
        user = new User();
        user.setId(ModelMocks.idUser);
        productRating = new ProductRating();
        productRating.setProduct(product);
        productRating.setRatingStars(ModelMocks.ratingStarsProductRating);
        productRating.setId(ModelMocks.idProductRating);
        productRating.setUser(user);
        productRating.setReview(ModelMocks.review);
        productRating.enable();
        data = new DataRegisterProductRating(ModelMocks.idProduct, ModelMocks.ratingStarsProductRating, ModelMocks.review, ModelMocks.idUser);
        expected = new DataProductRatingDetails(ModelMocks.idProductRating, ModelMocks.idProduct, ModelMocks.ratingStarsProductRating, ModelMocks.review, ModelMocks.idUser, false);
    }

    @Test
    void registerTestWithSucces(){
        when(productRepository.getReferenceById(anyLong())).thenReturn(product);
        when(userRepository.getReferenceById(anyLong())).thenReturn(user);
        when(productRatingRepository.save(any(ProductRating.class))).thenReturn(productRating);

        DataProductRatingDetails result = productRatingService.register(data);

        verify(productRepository, times(1)).getReferenceById(ModelMocks.idProduct);
        verify(userRepository, times(1)).getReferenceById(ModelMocks.idUser);
        verify(productRatingRepository, times(1)).save(any(ProductRating.class));
        assertEquals(expected.idProduct(), result.idProduct());
        assertEquals(expected.idUser(), result.idUser());
        assertEquals(expected.review(), result.review());
    }

    @Test
    void listTestWithSucces(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        List<ProductRating> productRatings = new ArrayList<>();
        productRatings.add(productRating);

        Page<ProductRating> page = new PageImpl<>(productRatings, pageable, productRatings.size());
        when(productRatingRepository.findAll(pageable)).thenReturn(page);

        Page<DataProductRatingDetails> result = productRatingService.list(pageable);

        verify(productRatingRepository, times(1)).findAll(pageable);

        List<DataProductRatingDetails> expectedDetails = result.getContent();
        assertEquals(1, expectedDetails.size());
        assertEquals(ModelMocks.review, expectedDetails.get(0).review());
        assertEquals(ModelMocks.idProduct, expectedDetails.get(0).idProduct());
        assertEquals(ModelMocks.idUser, expectedDetails.get(0).idUser());

        assertEquals(false, expectedDetails.get(0).disabled());
    }

    @Test
    void listDeactivatedTestWithSucces(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        List<ProductRating> productRatings = new ArrayList<>();
        productRating.disable();
        productRatings.add(productRating);

        Page<ProductRating> page = new PageImpl<>(productRatings, pageable, productRatings.size());
        when(productRatingRepository.findAllByDisabledTrue(pageable)).thenReturn(page);

        Page<DataProductRatingDetails> result = productRatingService.listDeactivated(pageable);

        verify(productRatingRepository, times(1)).findAllByDisabledTrue(pageable);

        List<DataProductRatingDetails> expectedDetails = result.getContent();
        assertEquals(1, expectedDetails.size());
        assertEquals(ModelMocks.review, expectedDetails.get(0).review());
        assertEquals(ModelMocks.idProduct, expectedDetails.get(0).idProduct());
        assertEquals(ModelMocks.idUser, expectedDetails.get(0).idUser());

        assertEquals(true, expectedDetails.get(0).disabled());
    }

    @Test
    void detailsTestWithSuccess(){
        when(productRatingRepository.getReferenceById(ModelMocks.idProductRating)).thenReturn(productRating);

        DataProductRatingDetails result = productRatingService.detailsProduct(ModelMocks.idProductRating);

        assertEquals(ModelMocks.review, result.review());
        assertEquals(ModelMocks.idProduct, result.idProduct());
        assertEquals(ModelMocks.idUser, result.idUser());
    }

    @Test
    void listProductRatingsByUserIdWithSuccess() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        Long userId = ModelMocks.idUser;

        List<ProductRating> productRatings = new ArrayList<>();
        productRatings.add(productRating);

        Page<ProductRating> page = new PageImpl<>(productRatings, pageable, productRatings.size());
        when(productRatingRepository.findAllByUserId(userId, pageable)).thenReturn(page);

        Page<DataProductRatingDetails> result = productRatingService.getProductRatingsByUserId(userId, pageable);

        verify(productRatingRepository, times(1)).findAllByUserId(userId, pageable);

        Page<DataProductRatingDetails> expectedDetails = new PageImpl<>(productRatings
                .stream()
                .map(DataProductRatingDetails::new)
                .toList(), pageable, productRatings.size());

        assertEquals(expectedDetails.getTotalElements(), result.getTotalElements());
        assertEquals(expectedDetails.getTotalPages(), result.getTotalPages());
        for (int i = 0; i < expectedDetails.getTotalElements(); i++) {
            assertEquals(ModelMocks.review, result.getContent().get(i).review());
            assertEquals(ModelMocks.idUser, result.getContent().get(i).idUser());
            assertEquals(ModelMocks.idProduct, result.getContent().get(i).idProduct());

            assertEquals(false, result.getContent().get(i).disabled());
        }
    }

    @Test
    void listProductRatingsByUserIdAndDisabledTrueWithSuccess() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        Long userId = ModelMocks.idUser;

        List<ProductRating> productRatings = new ArrayList<>();
        productRating.disable();
        productRatings.add(productRating);

        Page<ProductRating> page = new PageImpl<>(productRatings, pageable, productRatings.size());
        when(productRatingRepository.findAllByUserIdAndDisabledTrue(userId, pageable)).thenReturn(page);

        Page<DataProductRatingDetails> result = productRatingService.getProductRatingsByUserIdAndDisabledTrue(userId, pageable);

        verify(productRatingRepository, times(1)).findAllByUserIdAndDisabledTrue(userId, pageable);

        Page<DataProductRatingDetails> expectedDetails = new PageImpl<>(productRatings
                .stream()
                .map(DataProductRatingDetails::new)
                .toList(), pageable, productRatings.size());

        assertEquals(expectedDetails.getTotalElements(), result.getTotalElements());
        assertEquals(expectedDetails.getTotalPages(), result.getTotalPages());
        for (int i = 0; i < expectedDetails.getTotalElements(); i++) {
            assertEquals(ModelMocks.review, result.getContent().get(i).review());
            assertEquals(ModelMocks.idUser, result.getContent().get(i).idUser());
            assertEquals(ModelMocks.idProduct, result.getContent().get(i).idProduct());

            assertEquals(true, result.getContent().get(i).disabled());
        }
    }

    @Test
    void listProductRatingsByProductIdWithSuccess() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        Long productId = ModelMocks.idProduct;

        List<ProductRating> productRatings = new ArrayList<>();
        productRatings.add(productRating);

        Page<ProductRating> page = new PageImpl<>(productRatings, pageable, productRatings.size());
        when(productRatingRepository.findAllByProductId(productId, pageable)).thenReturn(page);

        Page<DataProductRatingDetails> result = productRatingService.getProductRatingsByProductId(productId, pageable);

        verify(productRatingRepository, times(1)).findAllByProductId(productId, pageable);

        Page<DataProductRatingDetails> expectedDetails = new PageImpl<>(productRatings
                .stream()
                .map(DataProductRatingDetails::new)
                .toList(), pageable, productRatings.size());

        assertEquals(expectedDetails.getTotalElements(), result.getTotalElements());
        assertEquals(expectedDetails.getTotalPages(), result.getTotalPages());
        for (int i = 0; i < expectedDetails.getTotalElements(); i++) {
            assertEquals(ModelMocks.review, result.getContent().get(i).review());
            assertEquals(ModelMocks.idUser, result.getContent().get(i).idUser());
            assertEquals(ModelMocks.idProduct, result.getContent().get(i).idProduct());

            assertEquals(false, result.getContent().get(i).disabled());
        }
    }

    @Test
    void listProductRatingsByProductIdAndDisabledTrueWithSuccess() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        Long productId = ModelMocks.idProduct;

        List<ProductRating> productRatings = new ArrayList<>();
        productRating.disable();
        productRatings.add(productRating);

        Page<ProductRating> page = new PageImpl<>(productRatings, pageable, productRatings.size());
        when(productRatingRepository.findAllByProductIdAndDisabledTrue(productId, pageable)).thenReturn(page);

        Page<DataProductRatingDetails> result = productRatingService.getProductRatingsByProductIdAndDisabledTrue(productId, pageable);

        verify(productRatingRepository, times(1)).findAllByProductIdAndDisabledTrue(productId, pageable);

        Page<DataProductRatingDetails> expectedDetails = new PageImpl<>(productRatings
                .stream()
                .map(DataProductRatingDetails::new)
                .toList(), pageable, productRatings.size());

        assertEquals(expectedDetails.getTotalElements(), result.getTotalElements());
        assertEquals(expectedDetails.getTotalPages(), result.getTotalPages());
        for (int i = 0; i < expectedDetails.getTotalElements(); i++) {
            assertEquals(ModelMocks.review, result.getContent().get(i).review());
            assertEquals(ModelMocks.idUser, result.getContent().get(i).idUser());
            assertEquals(ModelMocks.idProduct, result.getContent().get(i).idProduct());

            assertEquals(true, result.getContent().get(i).disabled());
        }
    }

    @Test
    void updateTestWithSuccess(){
        when(productRatingRepository.getReferenceById(anyLong())).thenReturn(productRating);
        when(productRepository.getReferenceById(anyLong())).thenReturn(product);
        when(userRepository.getReferenceById(anyLong())).thenReturn(user);
        Long newId = 1L;
        Long newProductId = 1L;
        BigDecimal newRatingStars = BigDecimal.TEN;
        String newReview = "alsyeweeeeeee";
        Long newUserId = 1L;
        DataUpdateProductRating data = new DataUpdateProductRating(ModelMocks.idProductRating, newProductId, newRatingStars, newReview, newUserId);
        productRating.updateDataProductRating(data, user, product);

        DataProductRatingDetails result = productRatingService.updateProductRating(data);

        assertEquals(ModelMocks.idProduct, result.idProduct());
        assertEquals(ModelMocks.idUser, result.idUser());
    }

    @Test
    void disabledTestWithSuccess(){
        when(productRatingRepository.getReferenceById(anyLong())).thenReturn(productRating);

        productRatingService.disableProductRating(ModelMocks.idProductRating);

        assertEquals(true, productRating.getDisabled());
    }
}
