package br.com.cepedi.ShoppingStore.controller.Purchase;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.model.records.purchase.details.DataDetailsPurchase;
import br.com.cepedi.ShoppingStore.model.records.purchase.register.DataRegisterPurchase;
import br.com.cepedi.ShoppingStore.service.purchase.PurharseService.PurchaseService;
import br.com.cepedi.ShoppingStore.service.purchase.shoppingCart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;

@RestController
@RequestMapping("/api/v2/purchases")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Purchases", description = "Purchases messages")
public class PurchaseController {

    private static final Logger log = LoggerFactory.getLogger(PurchaseController.class);

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping
    @Transactional
    @Operation(summary = "Register a new purchase", method = "POST", description = "Creates a new purchase with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Purchase registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataDetailsPurchase.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<DataDetailsPurchase> register(
            @Parameter(description = "Data required to register a purchase", required = true)
            @RequestBody @Valid DataRegisterPurchase data,
            UriComponentsBuilder uriBuilder
    ) {
        log.info("Registering new shoppingCart...");
        DataDetailsPurchase details = purchaseService.register(data);
        URI uri = uriBuilder.path("/api/v2/purchases/{id}").buildAndExpand(details.detailsShoppingCart().id()).toUri();
        log.info("shoppingCart registered successfully.");
        return ResponseEntity.created(uri).body(details);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Disable a shopping cart", method = "DELETE", description = "Disables a shopping cart by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Shopping cart disabled successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Shopping cart not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Void> disabled(
            @Parameter(description = "ID of the shopping cart to disable", required = true)
            @PathVariable Long id
    ) {
        log.info("Disabling shoppingCart with ID: {}", id);
        shoppingCartService.disabled(id);
        log.info("shoppingCart with ID {} disabled successfully.", id);
        return ResponseEntity.noContent().build();
    }


}