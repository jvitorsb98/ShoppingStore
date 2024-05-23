package br.com.cepedi.ShoppingStore.controller.productAttribute;




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

import br.com.cepedi.ShoppingStore.model.records.productAttribute.details.DataProductAttributeDetails;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataRegisterProductAttribute;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataUpdateProductAttribute;
import br.com.cepedi.ShoppingStore.service.productAttribute.ProductAttributeService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v2/productsAttributte")
@SecurityRequirement(name = "bearer-key")
public class ProductAttributeController {

    private static final Logger log = LoggerFactory.getLogger(ProductAttributeController.class);

    @Autowired
    private ProductAttributeService service;


    @PostMapping
    @Transactional
    public ResponseEntity<DataProductAttributeDetails> register(@RequestBody @Valid DataRegisterProductAttribute data , UriComponentsBuilder uriBuilder) {
        log.info("Scheduling new ProductAttribute...");
        DataProductAttributeDetails details = service.register(data);
        URI uri = uriBuilder.path("/product-Attribute/{id}").buildAndExpand(details.id()).toUri();
        log.info("ProductAttribute registered successfully with ID: {}", details.id());
        return ResponseEntity.created(uri).body(details);
	}
    
    @GetMapping
    public ResponseEntity<Page<DataProductAttributeDetails>> list(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        log.info("Fetching list of ProductAttribute...");
        Page<DataProductAttributeDetails> page = service.list(pageable);
        log.info("List of deactivated ProductAttributes fetched successfully. Total elements: {}", page.getTotalElements());
        return ResponseEntity.ok(page);
    }
    
    @GetMapping("/listDeactivated")
    public ResponseEntity<Page<DataProductAttributeDetails>> listDeactivated(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        log.info("Fetching list of ProductAttribute...");
        Page<DataProductAttributeDetails> page = service.listDeactivated(pageable);
        log.info("List of ProductAttributes fetched successfully. Total elements: {}", page.getTotalElements());
        return ResponseEntity.ok(page);
    }
    
    @GetMapping("/product/{id}")
    public ResponseEntity<List<DataProductAttributeDetails>> listByProductId(@PathVariable Long id) {
    	log.info("Fetching list of ProductAttributes for product with ID: {}", id);
        List<DataProductAttributeDetails> attributes = service.listByProductId(id);
        log.info("List of ProductAttributes for product with ID: {} fetched successfully. Total elements: {}", id, attributes.size());
        return ResponseEntity.ok(attributes);
    }
    
    @GetMapping("/productDactivated/{id}")
    public ResponseEntity<List<DataProductAttributeDetails>> listByProductIdDeactivated(@PathVariable Long id) {
		log.info("Fetching list of ProductAttributes for product with ID: {}", id);
        List<DataProductAttributeDetails> attributes = service.listByProductIdDeactivated(id);
		log.info("List of ProductAttributes for product with ID: {} fetched successfully. Total elements: {}", id, attributes.size());
        return ResponseEntity.ok(attributes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DataProductAttributeDetails> getDetails(@PathVariable Long id) {
		log.info("Fetching details of ProductAttribute with ID: {}", id);
        DataProductAttributeDetails details = service.details(id);
		log.info("Details of ProductAttribute with ID {} fetched successfully.", id);
        return ResponseEntity.ok(details);
    }
    
    @PutMapping
	@Transactional
	public ResponseEntity<DataProductAttributeDetails> update( @RequestBody @Valid DataUpdateProductAttribute data) { 
		log.info("Updating ProductAttribute with ID: {}", data.id());
		DataProductAttributeDetails details = service.update( data);
		log.info("ProductAttribute with ID {} updated successfully.", data.id());
		return ResponseEntity.ok(details);
    }
    

}
