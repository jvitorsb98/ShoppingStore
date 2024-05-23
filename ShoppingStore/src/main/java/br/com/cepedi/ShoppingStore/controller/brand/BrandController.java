package br.com.cepedi.ShoppingStore.controller.brand;


import br.com.cepedi.ShoppingStore.controller.category.CategoryController;
import br.com.cepedi.ShoppingStore.model.records.brand.details.DataBrandDetails;
import br.com.cepedi.ShoppingStore.model.records.brand.input.DataRegisterBrand;
import br.com.cepedi.ShoppingStore.model.records.brand.input.DataUpdateBrand;
import br.com.cepedi.ShoppingStore.model.records.category.details.DataCategoryDetails;
import br.com.cepedi.ShoppingStore.model.records.category.input.DataRegisterCategory;
import br.com.cepedi.ShoppingStore.service.brand.BrandService;
import br.com.cepedi.ShoppingStore.service.category.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
public class BrandController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandController.class);

    @Autowired
    private BrandService brandService;

    @PostMapping
    @Transactional
    public ResponseEntity<DataBrandDetails> register(@Valid @RequestBody DataRegisterBrand data , UriComponentsBuilder uriBuilder) {
        LOGGER.info("Registering a brand");
        DataBrandDetails brandDetails = brandService.register(data);
        URI uri = uriBuilder.path("/brands/{id}").buildAndExpand(brandDetails.id()).toUri();
        LOGGER.info("Brand registered successfully");
        return ResponseEntity.created(uri).body(brandDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataBrandDetails> getBrandById(@PathVariable Long id) {
        LOGGER.info("Retrieving brand with id: {}", id);
        DataBrandDetails brandDetails = brandService.getBrandById(id);
        LOGGER.info("Brand retrieved successfully");
        return new ResponseEntity<>(brandDetails, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<DataBrandDetails>> listAllBrands(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        LOGGER.info("Retrieving all brands");
        Page<DataBrandDetails> brands = brandService.listAllBrands(pageable);
        LOGGER.info("All brands retrieved successfully");
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DataBrandDetails> update(@Valid @RequestBody DataUpdateBrand data) {
        LOGGER.info("Updating brand with id: {}", data.id());
        DataBrandDetails updatedBrand = brandService.update(data);
        LOGGER.info("Brand updated successfully");
        return new ResponseEntity<>(updatedBrand, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> disable(@PathVariable Long id) {
        LOGGER.info("Disabling brand with id: {}", id);
        brandService.disabled(id);
        LOGGER.info("Brand disabled successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
