package br.com.cepedi.ShoppingStore.service.payment;

import br.com.cepedi.ShoppingStore.model.entitys.*;
import br.com.cepedi.ShoppingStore.model.enums.PaymentType;
import br.com.cepedi.ShoppingStore.model.records.payments.details.DataDetailsPayment;
import br.com.cepedi.ShoppingStore.model.records.payments.input.DataRegisterPayment;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.details.DataProductAttributeDetails;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataRegisterProductAttribute;
import br.com.cepedi.ShoppingStore.model.records.productRating.details.DataProductRatingDetails;
import br.com.cepedi.ShoppingStore.repository.PaymentRepository;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.service.payment.validations.disabled.ValidationsDisabledPayment;
import br.com.cepedi.ShoppingStore.service.payment.validations.register.ValidationsRegisterPayment;
import br.com.cepedi.ShoppingStore.service.productAttribute.ProductAttributeServiceTest;
import br.com.cepedi.ShoppingStore.service.productRating.ProductRatingServiceTest;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.Random.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Test PaymentService methods")
@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private List<ValidationsRegisterPayment> validationsRegister;

    @Mock
    private List<ValidationsDisabledPayment> validationsDisabled;

    @InjectMocks
    private PaymentService paymentService;

    private User user;

    private ShoppingCart shoppingCart;

    private Payment payment;

    private DataRegisterPayment data;

    private DataDetailsPayment expected;

    private static final Faker faker = new Faker();

    private static class ModelMocks{
        public static Long idUser = faker.random().nextLong();
        public static Long idShoppingCart = faker.random().nextLong();
        public static Long idPayment = faker.random().nextLong();
        public static PaymentType paymentType = PaymentType.CREDIT_CARD;
    }

    @BeforeEach
    void setUp(){
        user = new User();
        user.setId(ModelMocks.idUser);
        shoppingCart = new ShoppingCart();
        shoppingCart.setId(ModelMocks.idShoppingCart);
        shoppingCart.setUser(user);
        data = new DataRegisterPayment(ModelMocks.idShoppingCart, ModelMocks.paymentType);
        payment = new Payment();
        payment.setId(ModelMocks.idPayment);
        payment.setShoppingCart(shoppingCart);
        payment.setPaymentType(ModelMocks.paymentType);
        expected = new DataDetailsPayment(ModelMocks.idPayment, payment.getShoppingCart().getId(), payment.getPaymentType(), false);
    }

    @Test
    void registerTestWithSucces(){
        when(shoppingCartRepository.getReferenceById(anyLong())).thenReturn(shoppingCart);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        DataDetailsPayment result = paymentService.register(data);

        verify(validationsRegister, times(1)).forEach(any());
        verify(shoppingCartRepository, times(1)).getReferenceById(ModelMocks.idShoppingCart);
        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(expected.idShoppingCart(), result.idShoppingCart());
        assertEquals(expected.paymentType(), result.paymentType());
        assertEquals(expected.disabled(), result.disabled());
    }

    @Test
    void listAllPaymentsTestWithSucces(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        List<Payment> payments = new ArrayList<>();
        payments.add(payment);

        Page<Payment> page = new PageImpl<>(payments, pageable, payments.size());
        when(paymentRepository.findAll(pageable)).thenReturn(page);

        Page<DataDetailsPayment> result = paymentService.listAllPayments(pageable);

        verify(paymentRepository, times(1)).findAll(pageable);

        List<DataDetailsPayment> expectedDetails = result.getContent();
        assertEquals(1, expectedDetails.size());
        assertEquals(ModelMocks.idShoppingCart, expectedDetails.get(0).idShoppingCart());
        assertEquals(ModelMocks.paymentType, expectedDetails.get(0).paymentType());
        assertEquals(false, expectedDetails.get(0).disabled());
    }

    @Test
    void listAllPaymentsAndDisabledTrueTestWithSucces(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        List<Payment> payments = new ArrayList<>();
        payment.disable();
        payments.add(payment);

        Page<Payment> page = new PageImpl<>(payments, pageable, payments.size());
        when(paymentRepository.findAllPaymentsAndDisabledTrue(pageable)).thenReturn(page);

        Page<DataDetailsPayment> result = paymentService.listAllPaymentsAndDisabledTrue(pageable);

        verify(paymentRepository, times(1)).findAllPaymentsAndDisabledTrue(pageable);

        List<DataDetailsPayment> expectedDetails = result.getContent();
        assertEquals(1, expectedDetails.size());
        assertEquals(ModelMocks.idShoppingCart, expectedDetails.get(0).idShoppingCart());
        assertEquals(ModelMocks.paymentType, expectedDetails.get(0).paymentType());
        assertEquals(true, expectedDetails.get(0).disabled());
    }

    @Test
    void listAllPaymentsForUserTestWithSuccess() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        Long shoppingCartId = ModelMocks.idShoppingCart;

        List<Payment> payments = new ArrayList<>();
        payments.add(payment);

        Page<Payment> page = new PageImpl<>(payments, pageable, payments.size());
        when(paymentRepository.findAllPaymentsByUserId(ModelMocks.idUser, pageable)).thenReturn(page);

        Page<DataDetailsPayment> result = paymentService.listAllPaymentsForUser(ModelMocks.idUser, pageable);

        verify(paymentRepository, times(1)).findAllPaymentsByUserId(ModelMocks.idUser, pageable);

        Page<DataDetailsPayment> expectedDetails = new PageImpl<>(payments
                .stream()
                .map(DataDetailsPayment::new)
                .toList(), pageable, payments.size());

        assertEquals(expectedDetails.getTotalElements(), result.getTotalElements());
        assertEquals(expectedDetails.getTotalPages(), result.getTotalPages());
        for (int i = 0; i < expectedDetails.getTotalElements(); i++) {
            assertEquals(ModelMocks.paymentType, result.getContent().get(i).paymentType());
            assertEquals(ModelMocks.idShoppingCart, result.getContent().get(i).idShoppingCart());
            assertEquals(false, result.getContent().get(i).disabled());
        }
    }

    @Test
    void listAllPaymentsForUserAndDisabledTrueTestWithSuccess() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
        Long shoppingCartId = ModelMocks.idShoppingCart;

        List<Payment> payments = new ArrayList<>();
        payment.disable();
        payments.add(payment);

        Page<Payment> page = new PageImpl<>(payments, pageable, payments.size());
        when(paymentRepository.findAllPaymentsByUserIdAndDisabledTrue(ModelMocks.idUser, pageable)).thenReturn(page);

        Page<DataDetailsPayment> result = paymentService.listAllPaymentsForUserAndDisabledTrue(ModelMocks.idUser, pageable);

        verify(paymentRepository, times(1)).findAllPaymentsByUserIdAndDisabledTrue(ModelMocks.idUser, pageable);

        Page<DataDetailsPayment> expectedDetails = new PageImpl<>(payments
                .stream()
                .map(DataDetailsPayment::new)
                .toList(), pageable, payments.size());

        assertEquals(expectedDetails.getTotalElements(), result.getTotalElements());
        assertEquals(expectedDetails.getTotalPages(), result.getTotalPages());
        for (int i = 0; i < expectedDetails.getTotalElements(); i++) {
            assertEquals(ModelMocks.paymentType, result.getContent().get(i).paymentType());
            assertEquals(ModelMocks.idShoppingCart, result.getContent().get(i).idShoppingCart());
            assertEquals(true, result.getContent().get(i).disabled());
        }
    }

    @Test
    void disableTestWithSuccess(){
        when(paymentRepository.getReferenceById(anyLong())).thenReturn(payment);

        paymentService.disable(ModelMocks.idPayment);

        assertTrue(payment.isDisabled());
    }
}
