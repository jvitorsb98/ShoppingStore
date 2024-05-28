package br.com.cepedi.ShoppingStore.controller.productAttribute;




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

import br.com.cepedi.ShoppingStore.model.records.productAttribute.details.DataProductAttributeDetails;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataRegisterProductAttribute;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataUpdateProductAttribute;
import br.com.cepedi.ShoppingStore.service.productAttribute.ProductAttributeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v2/productsAttributte")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Product Attributes", description = "Product Attributes messages")
public class ProductAttributeController {

    private static final Logger log = LoggerFactory.getLogger(ProductAttributeController.class);

    @Autowired
    private ProductAttributeService service;


    @PostMapping
    @Transactional
    @Operation(summary = "Register a new product attribute", method = "POST", description = "Creates a new product attribute with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product attribute registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataProductAttributeDetails.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<DataProductAttributeDetails> register(
            @Parameter(description = "Data required to register a product attribute", required = true)
            @RequestBody @Valid DataRegisterProductAttribute data,
            UriComponentsBuilder uriBuilder
    ) {
        log.info("Scheduling new ProductAttribute...");
        DataProductAttributeDetails details = service.register(data);
        URI uri = uriBuilder.path("/product-Attribute/{id}").buildAndExpand(details.id()).toUri();
        log.info("ProductAttribute registered successfully with ID: {}", details.id());
        return ResponseEntity.created(uri).body(details);
	}
    
    @GetMapping
    @Operation(summary = "List all product attributes", method = "GET", description = "Retrieves a paginated list of all product attributes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product attributes retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product Attributes not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Page<DataProductAttributeDetails>> list(
            @Parameter(description = "Pagination and sorting information")
            @PageableDefault(size = 5, sort = {"id"}) Pageable pageable
    ) {
        log.info("Fetching list of ProductAttribute...");
        Page<DataProductAttributeDetails> page = service.list(pageable);
        log.info("List of deactivated ProductAttributes fetched successfully. Total elements: {}", page.getTotalElements());
        return ResponseEntity.ok(page);
    }
    
    @GetMapping("/listDeactivated")
    @Operation(summary = "List all deactivated product attributes", method = "GET", description = "Retrieves a paginated list of all deactivated product attributes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deactivated product attributes retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Deactivated product attributes not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Page<DataProductAttributeDetails>> listDeactivated(
            @Parameter(description = "Pagination and sorting information")
            @PageableDefault(size = 5, sort = {"id"}) Pageable pageable
    ) {
        log.info("Fetching list of ProductAttribute...");
        Page<DataProductAttributeDetails> page = service.listDeactivated(pageable);
        log.info("List of ProductAttributes fetched successfully. Total elements: {}", page.getTotalElements());
        return ResponseEntity.ok(page);
    }
    
    @GetMapping("/product/{id}")
    @Operation(summary = "List product attributes by product ID", method = "GET", description = "Retrieves a list of product attributes by product ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product attributes by product ID retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<List<DataProductAttributeDetails>> listByProductId(
            @Parameter(description = "ID of the product to retrieve attributes for", required = true)
            @PathVariable Long id
    ) {
    	log.info("Fetching list of ProductAttributes for product with ID: {}", id);
        List<DataProductAttributeDetails> attributes = service.listByProductId(id);
        log.info("List of ProductAttributes for product with ID: {} fetched successfully. Total elements: {}", id, attributes.size());
        return ResponseEntity.ok(attributes);
    }
    
    @GetMapping("/productDactivated/{id}")
    @Operation(summary = "List deactivated product attributes by product ID", method = "GET", description = "Retrieves a list of deactivated product attributes by product ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deactivated product attributes by product ID retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<List<DataProductAttributeDetails>> listByProductIdDeactivated(
            @Parameter(description = "ID of the product to retrieve deactivated attributes for", required = true)
            @PathVariable Long id
    ) {
		log.info("Fetching list of ProductAttributes for product with ID: {}", id);
        List<DataProductAttributeDetails> attributes = service.listByProductIdDeactivated(id);
		log.info("List of ProductAttributes for product with ID: {} fetched successfully. Total elements: {}", id, attributes.size());
        return ResponseEntity.ok(attributes);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get product attribute details by ID", method = "GET", description = "Retrieves the details of a product attribute by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product attribute details retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataProductAttributeDetails.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product attribute not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<DataProductAttributeDetails> getDetails(
            @Parameter(description = "ID of the product attribute to retrieve", required = true)
            @PathVariable Long id
    ) {
		log.info("Fetching details of ProductAttribute with ID: {}", id);
        DataProductAttributeDetails details = service.details(id);
		log.info("Details of ProductAttribute with ID {} fetched successfully.", id);
        return ResponseEntity.ok(details);
    }
    
    @PutMapping
	@Transactional
    @Operation(summary = "Update a product attribute", method = "PUT", description = "Updates an existing product attribute with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product attribute updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataProductAttributeDetails.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product attribute not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
	public ResponseEntity<DataProductAttributeDetails> update(
            @Parameter(description = "Data required to update a product attribute", required = true)
            @RequestBody @Valid DataUpdateProductAttribute data
    ) {
		log.info("Updating ProductAttribute with ID: {}", data.id());
		DataProductAttributeDetails details = service.update( data);
		log.info("ProductAttribute with ID {} updated successfully.", data.id());
		return ResponseEntity.ok(details);
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Disable a product attribute", method = "DELETE", description = "Disables a product attribute by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product attribute disabled successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product attribute not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Void> disabled(
            @Parameter(description = "ID of the product attribute to disable", required = true)
            @PathVariable Long id
    ) {
		log.info("Disabling ProductAttribute with ID: {}", id);
		service.disabled(id);
		log.info("ProductAttribute with ID {} disabled successfully.", id);
		return ResponseEntity.noContent().build();
	}
}
