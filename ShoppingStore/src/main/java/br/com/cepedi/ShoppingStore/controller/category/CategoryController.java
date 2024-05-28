package br.com.cepedi.ShoppingStore.controller.category;



import br.com.cepedi.ShoppingStore.model.records.brand.details.DataBrandDetails;
import br.com.cepedi.ShoppingStore.model.records.category.details.DataCategoryDetails;
import br.com.cepedi.ShoppingStore.model.records.category.input.DataRegisterCategory;
import br.com.cepedi.ShoppingStore.model.records.category.input.DataUpdateCategory;
import br.com.cepedi.ShoppingStore.service.category.CategoryService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v2/categories")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Category", description = "Category messages")
public class CategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @Transactional
    @Operation(summary = "Register a new Category", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category registered successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataCategoryDetails.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<DataCategoryDetails> registerCategory(
            @Parameter(description = "Data required to register a Category", required = true)
            @Valid @RequestBody DataRegisterCategory data,
            UriComponentsBuilder uriBuilder
    ) {
        LOGGER.info("Registering a category");
        DataCategoryDetails categoryDetails = categoryService.registerCategory(data);
        URI uri = uriBuilder.path("/categories/{id}").buildAndExpand(categoryDetails.id()).toUri();
        LOGGER.info("Category registered successfully");
        return ResponseEntity.created(uri).body(categoryDetails);
    }

    @GetMapping
    @Operation(summary = "List all categorys", method = "GET", description = "Retrieves a paginated list of all categorys.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Categories not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Page<DataCategoryDetails>> listAllCategories(Pageable pageable) {
        LOGGER.info("Retrieving all categories");
        Page<DataCategoryDetails> categories = categoryService.listAllCategories(pageable);
        LOGGER.info("All categories retrieved successfully");
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(summary = "Search categories by name", method = "GET", description = "Retrieves a paginated list of categories that match the given name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Categories not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Page<DataCategoryDetails>> listCategoriesByName(
            @Parameter(description = "Name of the category to be searched", required = true)
            @RequestParam("name") String name, Pageable pageable
    ) {
        LOGGER.info("Searching categories by name: {}", name);
        Page<DataCategoryDetails> categories = categoryService.listCategoriesByName(name, pageable);
        LOGGER.info("Categories searched successfully");
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", method = "GET", description = "Retrieves a category by its ID.")
    @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Category retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataCategoryDetails.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<DataCategoryDetails> getCategoryById(
            @Parameter(description = "ID of the category to be retrieved", required = true)
            @PathVariable Long id
    ) {
        LOGGER.info("Retrieving category with id: {}", id);
        DataCategoryDetails categoryDetails = categoryService.getCategoryById(id);
        LOGGER.info("Category retrieved successfully");
        return new ResponseEntity<>(categoryDetails, HttpStatus.OK);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Update category details", method = "PUT", description = "Updates the details of an existing category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataCategoryDetails.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<DataCategoryDetails> updateCategory(
            @Parameter(description = "Data required to update a category", required = true)
            @Valid @RequestBody DataUpdateCategory data
    ) {
        LOGGER.info("Updating category with id: {}", data.id());
        DataCategoryDetails updatedCategory = categoryService.updateCategory(data);
        LOGGER.info("Category updated successfully");
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Disable category by ID", method = "DELETE", description = "Disables a category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category disabled successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public ResponseEntity<Void> disableCategory(
            @Parameter(description = "ID of the category to be disabled", required = true)
            @PathVariable Long id
    ) {
        LOGGER.info("Disabling category with id: {}", id);
        categoryService.disableCategory(id);
        LOGGER.info("Category disabled successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    
}

