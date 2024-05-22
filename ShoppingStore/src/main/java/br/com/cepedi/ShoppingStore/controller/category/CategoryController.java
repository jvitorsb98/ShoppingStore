package br.com.cepedi.ShoppingStore.controller.category;



import br.com.cepedi.ShoppingStore.model.records.category.details.DataCategoryDetails;
import br.com.cepedi.ShoppingStore.model.records.category.input.DataRegisterCategory;
import br.com.cepedi.ShoppingStore.model.records.category.input.DataUpdateCategory;
import br.com.cepedi.ShoppingStore.service.category.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

@RestController
@RequestMapping("/api/v2/categories")
@SecurityRequirement(name = "bearer-key")
public class CategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @Transactional
    public ResponseEntity<DataCategoryDetails> registerCategory(@Valid @RequestBody DataRegisterCategory data) {
        LOGGER.info("Registering a category");
        DataCategoryDetails categoryDetails = categoryService.registerCategory(data);
        LOGGER.info("Category registered successfully");
        return new ResponseEntity<>(categoryDetails, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<DataCategoryDetails>> listAllCategories(Pageable pageable) {
        LOGGER.info("Retrieving all categories");
        Page<DataCategoryDetails> categories = categoryService.listAllCategories(pageable);
        LOGGER.info("All categories retrieved successfully");
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DataCategoryDetails>> listCategoriesByName(
            @RequestParam("name") String name, Pageable pageable) {
        LOGGER.info("Searching categories by name: {}", name);
        Page<DataCategoryDetails> categories = categoryService.listCategoriesByName(name, pageable);
        LOGGER.info("Categories searched successfully");
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataCategoryDetails> getCategoryById(@PathVariable Long id) {
        LOGGER.info("Retrieving category with id: {}", id);
        DataCategoryDetails categoryDetails = categoryService.getCategoryById(id);
        LOGGER.info("Category retrieved successfully");
        return new ResponseEntity<>(categoryDetails, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DataCategoryDetails> updateCategory(
            @PathVariable Long id, @Valid @RequestBody DataUpdateCategory data) {
        LOGGER.info("Updating category with id: {}", id);
        DataCategoryDetails updatedCategory = categoryService.updateCategory(data);
        LOGGER.info("Category updated successfully");
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> disableCategory(@PathVariable Long id) {
        LOGGER.info("Disabling category with id: {}", id);
        categoryService.disableCategory(id);
        LOGGER.info("Category disabled successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

