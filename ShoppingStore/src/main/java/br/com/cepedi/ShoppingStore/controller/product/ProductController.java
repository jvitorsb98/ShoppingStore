package br.com.cepedi.ShoppingStore.controller.product;


import br.com.cepedi.ShoppingStore.model.records.product.details.DataProductDetails;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;
import br.com.cepedi.ShoppingStore.service.product.ProductService;
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

import java.net.URI;

@RestController
@RequestMapping("/api/v2/products")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Product", description = "Product messages")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService service;


    @PostMapping
    @Transactional
    @Operation(summary = "Register a new product", method = "POST", description = "Creates a new product with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataProductDetails.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<DataProductDetails> register(
            @Parameter(description = "Data required to register a product", required = true)
            @RequestBody @Valid DataRegisterProduct data,
            UriComponentsBuilder uriBuilder
    ) {
        log.info("Scheduling new Product...");
        System.out.println(data);
        DataProductDetails details = service.register(data);
        URI uri = uriBuilder.path("/possible-facets/{id}").buildAndExpand(details.id()).toUri();
        log.info("Product scheduled successfully.");
        return ResponseEntity.created(uri).body(details);
    }

    @GetMapping
    @Operation(summary = "List all products", method = "GET", description = "Retrieves a paginated list of all products.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Payments not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Page<DataProductDetails>> list(
            @Parameter(description = "Pagination and sorting information")
            @PageableDefault(size = 5, sort = {"id"}) Pageable pageable
    ) {
        log.info("Fetching list of Product...");
        Page<DataProductDetails> page = service.list(pageable);
        log.info("List of Product fetched successfully.");
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product details by ID", method = "GET", description = "Retrieves the details of a product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product details retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataProductDetails.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<DataProductDetails> details(
            @Parameter(description = "ID of the product to retrieve", required = true)
            @PathVariable Long id
    ) {
        log.info("Fetching details of Product with ID: {}", id);
        DataProductDetails details = service.detailsProduct(id);
        log.info("Details of Product with ID {} fetched successfully.", id);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "List products by category ID", method = "GET", description = "Retrieves a paginated list of products by category ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products by category retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Page<DataProductDetails>> listByCategory(
            @Parameter(description = "ID of the category to retrieve products for", required = true)
            @PathVariable Long categoryId,
            @PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        log.info("Fetching list of Products by category with ID: {}", categoryId);
        Page<DataProductDetails> page = service.detailsProductCategory(categoryId, pageable);
        log.info("List of Products by category with ID {} fetched successfully.", categoryId);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Update a product", method = "PUT", description = "Updates an existing product with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataProductDetails.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<DataProductDetails> update(
            @Parameter(description = "Data required to update a product", required = true)
            @RequestBody @Valid DataUpdateProduct data
    ) {
        log.info("Updating Product with ID: {}", data.id());
        DataProductDetails details = service.updateProduct(data);
        log.info("Product with ID {} updated successfully.", data.id());
        return ResponseEntity.ok(details);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Disable a product", method = "DELETE", description = "Disables a product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product disabled successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Void> disabled(
            @Parameter(description = "ID of the product to disable", required = true)
            @PathVariable Long id
    ) {
        System.out.println(id);
        log.info("Disabling Product with ID: {}", id);
        service.deleteProduct(id);
        log.info("Product with ID {} disabled successfully.", id);
        return ResponseEntity.noContent().build();
    }
}