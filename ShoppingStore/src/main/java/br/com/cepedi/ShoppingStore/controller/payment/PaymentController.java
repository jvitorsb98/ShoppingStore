package br.com.cepedi.ShoppingStore.controller.payment;

import br.com.cepedi.ShoppingStore.model.records.payments.details.DataDetailsPayment;
import br.com.cepedi.ShoppingStore.model.records.payments.input.DataRegisterPayment;
import br.com.cepedi.ShoppingStore.service.payment.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Payment", description = "Payment messages")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping
    @Transactional
    @Operation(summary = "Register a new payment", method = "POST", description = "Creates a new payment with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataDetailsPayment.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<DataDetailsPayment> registerPayment(
            @Parameter(description = "Data required to register a payment", required = true)
            @Valid @RequestBody DataRegisterPayment data,
            UriComponentsBuilder uriBuilder
    ) {
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
    @Operation(summary = "List all payments", method = "GET", description = "Retrieves a paginated list of all payments.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Payments not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Page<DataDetailsPayment>> listAllPayments(
            @Parameter(description = "Pagination and sorting information")
            @PageableDefault(size = 10, sort = {"name"}) Pageable pageable
    ) {
        LOGGER.info("Retrieving all payments");
        Page<DataDetailsPayment> payments = paymentService.listAllPayments(pageable);
        LOGGER.info("All payments retrieved successfully");
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "List payments by user ID", method = "GET", description = "Retrieves a paginated list of payments for a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Page<DataDetailsPayment>> listPaymentsByUser(
            @Parameter(description = "ID of the user to retrieve payments for", required = true)
            @PathVariable Long userId,
            Pageable pageable
    ) {
        LOGGER.info("Retrieving payments for user with id: {}", userId);
        Page<DataDetailsPayment> payments = paymentService.listAllPaymentsForUser(userId, pageable);
        LOGGER.info("Payments for user with id {} retrieved successfully", userId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Disable category by ID", method = "DELETE", description = "Disables a category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category disabled successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Payment not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Void> disablePayment(
            @Parameter(description = "ID of the payment to be disabled", required = true)
            @PathVariable Long id
    ) {
        LOGGER.info("Disabling payment with id: {}", id);
        paymentService.disable(id);
        LOGGER.info("Payment with id {} disabled successfully", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
