package br.com.cepedi.ShoppingStore.controller.brand;


import br.com.cepedi.ShoppingStore.model.records.brand.details.DataBrandDetails;
import br.com.cepedi.ShoppingStore.model.records.brand.input.DataRegisterBrand;
import br.com.cepedi.ShoppingStore.model.records.brand.input.DataUpdateBrand;
import br.com.cepedi.ShoppingStore.service.brand.BrandService;
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
@RequestMapping("/api/v2/brands")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Brand", description = "Brand messages")
public class BrandController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandController.class);

    @Autowired
    private BrandService brandService;

    @PostMapping
    @Transactional
    @Operation(summary = "Register a new Brand", method = "POST")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Brand registered successfully",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = DataBrandDetails.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data",
                content = @Content),
        @ApiResponse(responseCode = "401", description = "Unauthorized",
                content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content)
    })
    public ResponseEntity<DataBrandDetails> register(
            @Parameter(description = "Data required to register a brand", required = true)
            @Valid @RequestBody
            DataRegisterBrand data,
            UriComponentsBuilder uriBuilder
    ) {
        LOGGER.info("Registering a brand");
        DataBrandDetails brandDetails = brandService.register(data);
        URI uri = uriBuilder.path("/brands/{id}").buildAndExpand(brandDetails.id()).toUri();
        LOGGER.info("Brand registered successfully");
        return ResponseEntity.created(uri).body(brandDetails);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get brand by ID", method = "GET", description = "Retrieves a brand by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand retrieved successfully",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DataBrandDetails.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Brand not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<DataBrandDetails> getBrandById(
            @Parameter(description = "ID of the brand to be retrieved", required = true)
            @PathVariable Long id
    ) {
        LOGGER.info("Retrieving brand with id: {}", id);
        DataBrandDetails brandDetails = brandService.getBrandById(id);
        LOGGER.info("Brand retrieved successfully");
        return new ResponseEntity<>(brandDetails, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all brands", method = "GET", description = "Retrieves a paginated list of all brands.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand retrieved successfully",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Brand not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Page<DataBrandDetails>> listAllBrands(
            @Parameter(description = "Pagination and sorting information")
            @PageableDefault(size = 10, sort = {"name"}) Pageable pageable
    ) {
        LOGGER.info("Retrieving all brands");
        Page<DataBrandDetails> brands = brandService.listAllBrands(pageable);
        LOGGER.info("All brands retrieved successfully");
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Update brand details", method = "PUT", description = "Updates the details of an existing brand.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand updated successfully",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DataBrandDetails.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Brand not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<DataBrandDetails> update(
            @Parameter(description = "Data required to update a brand", required = true)
            @Valid @RequestBody DataUpdateBrand data
    ) {
        LOGGER.info("Updating brand with id: {}", data.id());
        DataBrandDetails updatedBrand = brandService.update(data);
        LOGGER.info("Brand updated successfully");
        return new ResponseEntity<>(updatedBrand, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Disable brand by ID", method = "DELETE", description = "Disables a brand by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Brand disabled successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Brand not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Void> disable(
            @Parameter(description = "ID of the brand to be disabled", required = true)
            @PathVariable Long id
    ) {
        LOGGER.info("Disabling brand with id: {}", id);
        brandService.disabled(id);
        LOGGER.info("Brand disabled successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
