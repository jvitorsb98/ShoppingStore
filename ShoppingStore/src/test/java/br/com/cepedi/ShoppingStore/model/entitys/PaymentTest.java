package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.model.enums.PaymentType;
import br.com.cepedi.ShoppingStore.model.records.payments.input.DataRegisterPayment;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.Random.class)
@DisplayName("Test entity Payment")
public class PaymentTest {

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = new Payment();
    }

    @Test
    public void testConstructorWithDataRegisterPayment() {
        // Given
        ShoppingCart shoppingCart = new ShoppingCart();
        DataRegisterPayment dataRegisterPayment = new DataRegisterPayment(123L, PaymentType.DEBIT_CARD);

        // When
        Payment payment = new Payment(dataRegisterPayment, shoppingCart);

        // Then
        assertNotNull(payment);
        assertEquals(shoppingCart, payment.getShoppingCart());
        assertEquals(PaymentType.DEBIT_CARD, payment.getPaymentType());
        assertFalse(payment.isDisabled());
    }
    @Test
    public void testSetId() {
        // Given
        Payment payment = new Payment();
        Long id = 123L;

        // When
        payment.setId(id);

        // Then
        assertEquals(id, payment.getId());
    }


    @Test
    public void testEmptyConstructor() {
        // Given
        Payment payment = new Payment();

        // Then
        assertNotNull(payment);
        assertNull(payment.getId());
        assertNull(payment.getShoppingCart());
        assertNull(payment.getPaymentType());
        assertFalse(payment.isDisabled());
    }

    @Test
    public void testDataRegisterPaymentConstructor() {
        // Given
        Long shoppingCartId = 123L;
        PaymentType paymentType = PaymentType.CREDIT_CARD;

        // When
        DataRegisterPayment dataRegisterPayment = new DataRegisterPayment(shoppingCartId, paymentType);

        // Then
        assertNotNull(dataRegisterPayment);
        assertEquals(shoppingCartId, dataRegisterPayment.shoppingCartId());
        assertEquals(paymentType, dataRegisterPayment.paymentType());
    }


    // Teste de Construção
    @Test
    public void testConstruction() {
        assertNotNull(payment);
    }

    // Teste de Setter
    @Test
    public void testSetter() {
        ShoppingCart shoppingCart = new ShoppingCart();
        payment.setShoppingCart(shoppingCart);
        assertEquals(shoppingCart, payment.getShoppingCart());

        payment.setPaymentType(PaymentType.CREDIT_CARD);
        assertEquals(PaymentType.CREDIT_CARD, payment.getPaymentType());

        payment.setDisabled(true);
        assertTrue(payment.isDisabled());
    }

    // Teste de Getter


    // Teste de Atualização
    @Test
    public void testUpdate() {
        assertFalse(payment.isDisabled());
        payment.disable();
        assertTrue(payment.isDisabled());
    }

    // Teste de Desabilitação
    @Test
    public void testDisable() {
        assertFalse(payment.isDisabled());
        payment.disable();
        assertTrue(payment.isDisabled());
    }



    // Teste de Relacionamento
    @Test
    public void testRelationship() {
        ShoppingCart shoppingCart = new ShoppingCart();
        payment.setShoppingCart(shoppingCart);
        assertEquals(shoppingCart, payment.getShoppingCart());
    }

    // Teste de Tipo de Pagamento
    @Test
    public void testPaymentType() {
        payment.setPaymentType(PaymentType.DEBIT_CARD);
        assertEquals(PaymentType.DEBIT_CARD, payment.getPaymentType());
    }

}
