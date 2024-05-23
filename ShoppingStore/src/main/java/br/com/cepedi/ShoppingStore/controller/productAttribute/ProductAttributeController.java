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
import br.com.cepedi.ShoppingStore.service.productAttribute.ProductAttributeService;

import java.net.URI;

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
        log.info("ProductAttribute scheduled successfully.");
        return ResponseEntity.created(uri).body(details);
	}
    
    @GetMapping
    public ResponseEntity<Page<DataProductAttributeDetails>> list(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        log.info("Fetching list of ProductAttribute...");
        Page<DataProductAttributeDetails> page = service.list(pageable);
        log.info("List of ProductAttribute fetched successfully.");
        return ResponseEntity.ok(page);
    }
    
    @GetMapping("/listDeactivated")
    public ResponseEntity<Page<DataProductAttributeDetails>> listDeactivated(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        log.info("Fetching list of ProductAttribute...");
        Page<DataProductAttributeDetails> page = service.listDeactivated(pageable);
        log.info("List of ProductAttribute fetched successfully.");
        return ResponseEntity.ok(page);
    }
}
