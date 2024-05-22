package br.com.cepedi.ShoppingStore.controller.Purchase;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.model.records.purchase.details.DataDetailsPurchase;
import br.com.cepedi.ShoppingStore.model.records.purchase.register.DataRegisterPurchase;
import br.com.cepedi.ShoppingStore.service.purchase.PurharseService.PurchaseService;
import br.com.cepedi.ShoppingStore.service.purchase.shoppingCart.ShoppingCartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
public class PurchaseController {

    private static final Logger log = LoggerFactory.getLogger(PurchaseController.class);

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping
    @Transactional
    public ResponseEntity<DataDetailsPurchase> register(@RequestBody @Valid DataRegisterPurchase data, UriComponentsBuilder uriBuilder) {
        log.info("Registering new shoppingCart...");
        DataDetailsPurchase details = purchaseService.register(data);
        URI uri = uriBuilder.path("/api/v2/purchases/{id}").buildAndExpand(details.detailsShoppingCart().id()).toUri();
        log.info("shoppingCart registered successfully.");
        return ResponseEntity.created(uri).body(details);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<DataDetailsPurchase> disable(@PathVariable Long id) {
        log.info("Disabling shoppingCart with ID: {}", id);
        shoppingCartService.disabled(id);
        log.info("shoppingCart with ID {} disabled successfully.", id);
        return ResponseEntity.noContent().build();
    }


}