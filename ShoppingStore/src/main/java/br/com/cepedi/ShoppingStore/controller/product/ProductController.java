package br.com.cepedi.ShoppingStore.controller.product;


import br.com.cepedi.ShoppingStore.model.records.product.details.DataProductDetails;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;
import br.com.cepedi.ShoppingStore.service.product.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v2/products")
@SecurityRequirement(name = "bearer-key")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService service;


    @PostMapping
    @Transactional
    public ResponseEntity<DataProductDetails> register(@RequestBody @Valid DataRegisterProduct data , UriComponentsBuilder uriBuilder) {
        log.info("Scheduling new Product...");
        System.out.println(data);
        DataProductDetails details = service.register(data);
        URI uri = uriBuilder.path("/possible-facets/{id}").buildAndExpand(details.id()).toUri();
        log.info("Product scheduled successfully.");
        return ResponseEntity.created(uri).body(details);
    }

    @GetMapping
    public ResponseEntity<Page<DataProductDetails>> list(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        log.info("Fetching list of Product...");
        Page<DataProductDetails> page = service.list(pageable);
        log.info("List of Product fetched successfully.");
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataProductDetails> details(@PathVariable Long id) {
        log.info("Fetching details of Product with ID: {}", id);
        DataProductDetails details = service.detailsProduct(id);
        log.info("Details of Product with ID {} fetched successfully.", id);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<DataProductDetails>> listByCategory(
            @PathVariable Long categoryId,
            @PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        log.info("Fetching list of Products by category with ID: {}", categoryId);
        Page<DataProductDetails> page = service.detailsProductCategory(categoryId, pageable);
        log.info("List of Products by category with ID {} fetched successfully.", categoryId);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DataProductDetails> update(@RequestBody @Valid DataUpdateProduct data) {
        log.info("Updating Product with ID: {}", data.id());
        DataProductDetails details = service.updateProduct(data);
        log.info("Product with ID {} updated successfully.", data.id());
        return ResponseEntity.ok(details);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> disabled(@PathVariable Long id) {
        System.out.println(id);
        log.info("Disabling Product with ID: {}", id);
        service.deleteProduct(id);
        log.info("Product with ID {} disabled successfully.", id);
        return ResponseEntity.noContent().build();
    }
}