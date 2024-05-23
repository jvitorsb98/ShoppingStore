package br.com.cepedi.ShoppingStore.controller.productRating;



import br.com.cepedi.ShoppingStore.model.records.productRating.details.DataProductRatingDetails;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.service.productRating.ProductRatingService;
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
@RequestMapping("/api/v2/productsRating")
@SecurityRequirement(name = "bearer-key")
public class ProductRatingController {

    private static final Logger log = LoggerFactory.getLogger(ProductRatingController.class);

    @Autowired
    private ProductRatingService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DataProductRatingDetails> register(@RequestBody @Valid DataRegisterProductRating data, UriComponentsBuilder uriBuilder) {
        System.out.println(data);
        log.info("Registering new ProductRating...");
        DataProductRatingDetails details = service.register(data);
        URI uri = uriBuilder.path("/product-ratings/{id}").buildAndExpand(details.id()).toUri();
        log.info("ProductRating registered successfully with ID: {}", details.id());
        return ResponseEntity.created(uri).body(details);
    }

    @GetMapping
    public ResponseEntity<Page<DataProductRatingDetails>> list(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        log.info("Fetching list of ProductRatings...");
        Page<DataProductRatingDetails> page = service.list(pageable);
        log.info("List of ProductRatings fetched successfully. Total elements: {}", page.getTotalElements());
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataProductRatingDetails> details(@PathVariable Long id) {
        log.info("Fetching details of ProductRating with ID: {}", id);
        DataProductRatingDetails details = service.detailsProduct(id);
        log.info("Details of ProductRating with ID {} fetched successfully.", id);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Page<DataProductRatingDetails>> listByProduct(
            @PathVariable Long productId,
            @PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        log.info("Fetching list of ProductRatings by product with ID: {}", productId);
        Page<DataProductRatingDetails> page = service.getProductRatingsByProductId(productId, pageable);
        log.info("List of ProductRatings by product with ID {} fetched successfully. Total elements: {}", productId, page.getTotalElements());
        return ResponseEntity.ok(page);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<DataProductRatingDetails>> listByUser(
            @PathVariable Long userId,
            @PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        log.info("Fetching list of ProductRatings by user with ID: {}", userId);
        Page<DataProductRatingDetails> page = service.getProductRatingsByUserId(userId, pageable);
        log.info("List of ProductRatings by user with ID {} fetched successfully. Total elements: {}", userId, page.getTotalElements());
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DataProductRatingDetails> update(@RequestBody @Valid DataUpdateProductRating data) {
        log.info("Updating ProductRating with ID: {}", data.id());
        DataProductRatingDetails details = service.updateProductRating(data);
        log.info("ProductRating with ID {} updated successfully.", data.id());
        return ResponseEntity.ok(details);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting ProductRating with ID: {}", id);
        service.disableProductRating(id);
        log.info("ProductRating with ID {} deleted successfully.", id);
        return ResponseEntity.noContent().build();
    }
}
