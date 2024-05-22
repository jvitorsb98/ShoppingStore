package br.com.cepedi.ShoppingStore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import br.com.cepedi.ShoppingStore.model.records.product.details.DataProductDetails;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;
import br.com.cepedi.ShoppingStore.service.product.ProductService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("v2/product")
@SecurityRequirement(name = "bearer-key")
public class ProductControllerV2 {
	
	private static final Logger log = LoggerFactory.getLogger(ProductControllerV2.class);
	
	private ProductService service;
	
	
	 	@PostMapping
	    @Transactional
	    public ResponseEntity<Object> schedule(@RequestBody @Valid DataRegisterProduct data) {
	        log.info("Scheduling new Product...");
	        DataProductDetails details = service.register(data);
	        log.info("Product scheduled successfully.");
	        return ResponseEntity.ok(details);
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
	     
	     @PutMapping("/{id}")
	     @Transactional
	     public ResponseEntity<DataProductDetails> update(@PathVariable Long id, @RequestBody @Valid DataUpdateProduct data) {
	         log.info("Updating Product with ID: {}", id);
	         DataProductDetails details = service.updateProduct(data);
	         log.info("Product with ID {} updated successfully.", id);
	         return ResponseEntity.ok(details);
	     }
	     
	     @DeleteMapping("/{id}")
	     @Transactional
	     public ResponseEntity<Object> disabled(@PathVariable Long id) {
	         log.info("Disabling Product with ID: {}", id);
	         service.deleteProduct(id);
	         log.info("Product with ID {} disabled successfully.", id);
	         return ResponseEntity.noContent().build();
	     }
}
