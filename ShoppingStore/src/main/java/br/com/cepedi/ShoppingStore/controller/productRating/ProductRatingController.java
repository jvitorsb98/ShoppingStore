package br.com.cepedi.ShoppingStore.controller.productRating;



import br.com.cepedi.ShoppingStore.model.records.productRating.details.DataProductRatingDetails;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.service.productRating.ProductRatingService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.MediaType;

import java.net.URI;

@RestController
@RequestMapping("/api/v2/productsRating")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Product Ratings", description = "Product Ratings messages")
public class ProductRatingController {

    private static final Logger log = LoggerFactory.getLogger(ProductRatingController.class);

    @Autowired
    private ProductRatingService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @Operation(summary = "Register a new product rating", method = "POST", description = "Creates a new product rating with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product rating registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataProductRatingDetails.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<DataProductRatingDetails> register(
            @Parameter(description = "Data required to register a product rating", required = true)
            @RequestBody @Valid DataRegisterProductRating data,
            UriComponentsBuilder uriBuilder
    ) {
        log.info("Registering new ProductRating...");
        DataProductRatingDetails details = service.register(data);
        URI uri = uriBuilder.path("/product-ratings/{id}").buildAndExpand(details.id()).toUri();
        log.info("ProductRating registered successfully with ID: {}", details.id());
        return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).body(details);
    }


    @GetMapping
    @Operation(summary = "List all product ratings", method = "GET", description = "Retrieves a paginated list of all product ratings.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product ratings retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product Ratings not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Page<DataProductRatingDetails>> list(
            @Parameter(description = "Pagination and sorting information")
            @PageableDefault(size = 5, sort = {"id"}) Pageable pageable
    ) {
        log.info("Fetching list of ProductRatings...");
        Page<DataProductRatingDetails> page = service.list(pageable);
        log.info("List of ProductRatings fetched successfully. Total elements: {}", page.getTotalElements());
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product rating details by ID", method = "GET", description = "Retrieves the details of a product rating by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product rating details retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataProductRatingDetails.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product rating not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<DataProductRatingDetails> details(
            @Parameter(description = "ID of the product rating to retrieve", required = true)
            @PathVariable Long id
    ) {
        log.info("Fetching details of ProductRating with ID: {}", id);
        DataProductRatingDetails details = service.detailsProduct(id);
        log.info("Details of ProductRating with ID {} fetched successfully.", id);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "List product ratings by product ID", method = "GET", description = "Retrieves a paginated list of product ratings by product ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product ratings by product ID retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Page<DataProductRatingDetails>> listByProduct(
            @Parameter(description = "ID of the product to retrieve ratings for", required = true)
            @PathVariable Long productId,
            @PageableDefault(size = 5, sort = {"id"}) Pageable pageable
    ) {
        log.info("Fetching list of ProductRatings by product with ID: {}", productId);
        Page<DataProductRatingDetails> page = service.getProductRatingsByProductId(productId, pageable);
        log.info("List of ProductRatings by product with ID {} fetched successfully. Total elements: {}", productId, page.getTotalElements());
        return ResponseEntity.ok(page);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "List product ratings by user ID", method = "GET", description = "Retrieves a paginated list of product ratings by user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product ratings by user ID retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Page<DataProductRatingDetails>> listByUser(
            @Parameter(description = "ID of the user to retrieve ratings for", required = true)
            @PathVariable Long userId,
            @PageableDefault(size = 5, sort = {"id"}) Pageable pageable
    ) {
        log.info("Fetching list of ProductRatings by user with ID: {}", userId);
        Page<DataProductRatingDetails> page = service.getProductRatingsByUserId(userId, pageable);
        log.info("List of ProductRatings by user with ID {} fetched successfully. Total elements: {}", userId, page.getTotalElements());
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Update a product rating", method = "PUT", description = "Updates the details of an existing product rating.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product rating updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataProductRatingDetails.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product rating not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<DataProductRatingDetails> update(
            @Parameter(description = "Data required to update a product rating", required = true)
            @RequestBody @Valid DataUpdateProductRating data
    ) {
        log.info("Updating ProductRating with ID: {}", data.id());
        DataProductRatingDetails details = service.updateProductRating(data);
        log.info("ProductRating with ID {} updated successfully.", data.id());
        return ResponseEntity.ok(details);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete a product rating", method = "DELETE", description = "Deletes a product rating by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product rating deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product rating not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the product rating to delete", required = true)
            @PathVariable Long id
    ) {
        log.info("Deleting ProductRating with ID: {}", id);
        service.disableProductRating(id);
        log.info("ProductRating with ID {} deleted successfully.", id);
        return ResponseEntity.noContent().build();
    }
    
}
