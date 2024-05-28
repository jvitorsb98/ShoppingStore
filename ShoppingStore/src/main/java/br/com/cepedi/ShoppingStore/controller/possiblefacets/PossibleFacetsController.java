package br.com.cepedi.ShoppingStore.controller.possiblefacets;


import java.net.URI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.details.DataPossibleFacetsDetails;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataRegisterPossibleFacets;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataUpdatePossibleFacets;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import br.com.cepedi.ShoppingStore.service.possibleFacets.PossibleFacetsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/possible-facets")
//@SecurityRequirement(name = "bearer-key")
@Tag(name = "Possible Facets", description = "Possible Facets messages")
public class PossibleFacetsController {

    @Autowired
    private PossibleFacetsService service;

    private static final Logger log = LoggerFactory.getLogger(PossibleFacetsController.class);


    @PostMapping
    @Transactional
    @Operation(summary = "Register a new possible facet", method = "POST", description = "Creates a new possible facet with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Possible facet registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataPossibleFacetsDetails.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<DataPossibleFacetsDetails> register(
            @Parameter(description = "Data required to register a possible facet", required = true)
            @RequestBody @Valid DataRegisterPossibleFacets data,
            UriComponentsBuilder uriBuilder
    ) {
        log.info("Registering possible facet");
        DataPossibleFacetsDetails createdFacet = service.register(data);
        URI uri = uriBuilder.path("/possible-facets/{id}").buildAndExpand(createdFacet.id()).toUri();
        log.info("Registering possible facet with ID: {}", createdFacet.id());
        return ResponseEntity.created(uri).body(createdFacet);
    }


    @GetMapping
    @Operation(summary = "List all possible facets", method = "GET", description = "Retrieves a paginated list of all possible facets.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Possible facets retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Possible Facets not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Page<DataPossibleFacetsDetails>> list(
            @Parameter(description = "Pagination and sorting information")
            @PageableDefault(size = 10, sort = {"name"}) Pageable pageable
    ) {
        log.info("Listing all possible facets");
        Page<DataPossibleFacetsDetails> facets = service.listAll(pageable);
        log.info("Listed all possible facets");
        return ResponseEntity.ok(facets);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get a possible facet by ID", method = "GET", description = "Retrieves a possible facet by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Possible facet retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataPossibleFacetsDetails.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Possible facet not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<DataPossibleFacetsDetails> findById(
            @Parameter(description = "ID of the possible facet to retrieve", required = true)
            @PathVariable Long id
    ) {
        log.info("Fetching possible facet with ID: {}", id);
        DataPossibleFacetsDetails facet = service.findById(id);
        log.info("Fetched possible facet with ID: {}", id);
        return ResponseEntity.ok(facet);
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get possible facets by category ID", method = "GET", description = "Retrieves possible facets by the category ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Possible facets retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Page<DataPossibleFacetsDetails>> findByCategory(
            @Parameter(description = "ID of the category to retrieve possible facets for", required = true)
            @PathVariable Long categoryId,
            Pageable pageable
    ) {
        log.info("Fetching possible facets by category ID: {}", categoryId);
        Page<DataPossibleFacetsDetails> facets = service.findByCategory(categoryId, pageable);
        log.info("Fetched possible facets by category ID: {}", categoryId);
        return ResponseEntity.ok(facets);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Update a possible facet", method = "PUT", description = "Updates an existing possible facet with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Possible facet updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataPossibleFacetsDetails.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Possible facet not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<DataPossibleFacetsDetails> update(
            @Parameter(description = "Data required to update a possible facet", required = true)
            @RequestBody @Valid DataUpdatePossibleFacets data
    ) {
        log.info("Updating possible facet with ID: {}", data.id());
        DataPossibleFacetsDetails updatedFacet = service.update(data);
        log.info("Updated possible facet with ID: {}", data.id());
        return ResponseEntity.ok(updatedFacet);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Disable a possible facet", method = "DELETE", description = "Disables a possible facet by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Possible facet disabled successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Possible facet not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Object> disabled(
            @Parameter(description = "ID of the possible facet to disable", required = true)
            @PathVariable Long id
    ) {
        log.info("Disabling possible facet with ID: {}", id);
        service.disable(id);
        log.info("Disabled possible facet with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
