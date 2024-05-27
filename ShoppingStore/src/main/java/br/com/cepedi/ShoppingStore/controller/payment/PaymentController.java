package br.com.cepedi.ShoppingStore.controller.payment;

import br.com.cepedi.ShoppingStore.model.records.payments.details.DataDetailsPayment;
import br.com.cepedi.ShoppingStore.model.records.payments.input.DataRegisterPayment;
import br.com.cepedi.ShoppingStore.service.payment.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v2/payments")
@SecurityRequirement(name = "bearer-key")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping
    @Transactional
    public ResponseEntity<DataDetailsPayment> registerPayment(@Valid @RequestBody DataRegisterPayment data, UriComponentsBuilder uriBuilder) {
        LOGGER.info("Registering a payment");
        DataDetailsPayment dataDetailsPayment = paymentService.register(data);
        
        Long id = dataDetailsPayment.id();
        if (id != null) {
            URI uri = uriBuilder.path("/payments/{id}").buildAndExpand(id).toUri(); // Corrigido para /payments/{id}
            LOGGER.info("Payment registered successfully");
            LOGGER.info("ID: {}", id);
            return ResponseEntity.created(uri).body(dataDetailsPayment);
        } else {
            // Tratar o caso em que o id é null
            LOGGER.error("Failed to register payment: ID is null");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping
    public ResponseEntity<Page<DataDetailsPayment>> listAllPayments(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        LOGGER.info("Retrieving all payments");
        Page<DataDetailsPayment> payments = paymentService.listAllPayments(pageable);
        LOGGER.info("All payments retrieved successfully");
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<DataDetailsPayment>> listPaymentsByUser(@PathVariable Long userId, Pageable pageable) {
        LOGGER.info("Retrieving payments for user with id: {}", userId);
        Page<DataDetailsPayment> payments = paymentService.listAllPaymentsForUser(userId, pageable);
        LOGGER.info("Payments for user with id {} retrieved successfully", userId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> disablePayment(@PathVariable Long id) {
        LOGGER.info("Disabling payment with id: {}", id);
        paymentService.disable(id);
        LOGGER.info("Payment with id {} disabled successfully", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
