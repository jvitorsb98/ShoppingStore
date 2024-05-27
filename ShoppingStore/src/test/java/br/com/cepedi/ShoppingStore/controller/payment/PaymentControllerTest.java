package br.com.cepedi.ShoppingStore.controller.payment;

import br.com.cepedi.ShoppingStore.model.enums.PaymentType;
import br.com.cepedi.ShoppingStore.model.records.payments.details.DataDetailsPayment;
import br.com.cepedi.ShoppingStore.model.records.payments.input.DataRegisterPayment;
import br.com.cepedi.ShoppingStore.service.payment.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
	@Test
	void testRegisterPayment() {
		// Configurar os dados de entrada e saída do teste
		DataRegisterPayment data = new DataRegisterPayment(1L, PaymentType.CREDIT_CARD);
		DataDetailsPayment details = new DataDetailsPayment(1L, 1L, PaymentType.CREDIT_CARD);
		when(paymentService.register(any(DataRegisterPayment.class))).thenReturn(details);
		
		// Executar o método a ser testado
		ResponseEntity<DataDetailsPayment> response = paymentController.registerPayment(data, UriComponentsBuilder.newInstance());
		
		// Verificar os resultados
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(details, response.getBody());
		
		// Verificar interações com o mock
		verify(paymentService, times(1)).register(any(DataRegisterPayment.class));
	}

    
	@Test
	void testGetAllPayments() {
		// Configurar os dados de entrada e saída do teste
		List<DataDetailsPayment> payments = new ArrayList<>();
		payments.add(new DataDetailsPayment(1L, 1L, PaymentType.CREDIT_CARD));
		payments.add(new DataDetailsPayment(2L, 2L, PaymentType.CREDIT_CARD));
		Page<DataDetailsPayment> page = new PageImpl<>(payments);
		when(paymentService.listAllPayments(any(Pageable.class))).thenReturn(page);
		
		// Executar o método a ser testado
		ResponseEntity<Page<DataDetailsPayment>> response = paymentController.listAllPayments(Pageable.unpaged());
		
		// Verificar os resultados
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(page, response.getBody());
		
		// Verificar interações com o mock
		verify(paymentService, times(1)).listAllPayments(any(Pageable.class));
	}
	
	@Test
	void testGetPaymentsByUser() {
	    // Configurar os dados de entrada e saída do teste
	    List<DataDetailsPayment> payments = new ArrayList<>();
	    payments.add(new DataDetailsPayment(1L, 1L, PaymentType.CREDIT_CARD));
	    payments.add(new DataDetailsPayment(2L, 2L, PaymentType.CREDIT_CARD));
	    Page<DataDetailsPayment> page = new PageImpl<>(payments);

	    // Configurar o comportamento do mock
	    when(paymentService.listAllPaymentsForUser(eq(1L), any(Pageable.class))).thenReturn(page);
	    
	    // Executar o método a ser testado
	    ResponseEntity<Page<DataDetailsPayment>> response = paymentController.listPaymentsByUser(1L, Pageable.unpaged());
	    
	    // Verificar os resultados
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    assertEquals(page, response.getBody());
	    
	    // Verificar interações com o mock
	    verify(paymentService, times(1)).listAllPaymentsForUser(eq(1L), any(Pageable.class));
	}

	
	
}		
