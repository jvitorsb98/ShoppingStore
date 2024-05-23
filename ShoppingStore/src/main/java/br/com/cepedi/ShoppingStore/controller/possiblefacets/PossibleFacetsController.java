package br.com.cepedi.ShoppingStore.controller.possiblefacets;


import java.net.URI;

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
public class PossibleFacetsController {

    @Autowired
    private PossibleFacetsService service;

    private static final Logger log = LoggerFactory.getLogger(PossibleFacetsController.class);


    @PostMapping
    @Transactional
    public ResponseEntity<DataPossibleFacetsDetails> register(@RequestBody @Valid DataRegisterPossibleFacets data, UriComponentsBuilder uriBuilder) {
        log.info("Registering possible facet");
        DataPossibleFacetsDetails createdFacet = service.register(data);
        URI uri = uriBuilder.path("/possible-facets/{id}").buildAndExpand(createdFacet.id()).toUri();
        log.info("Registering possible facet with ID: {}", createdFacet.id());
        return ResponseEntity.created(uri).body(createdFacet);
    }


    @GetMapping
    public ResponseEntity<Page<DataPossibleFacetsDetails>> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        log.info("Listing all possible facets");
        Page<DataPossibleFacetsDetails> facets = service.listAll(pageable);
        log.info("Listed all possible facets");
        return ResponseEntity.ok(facets);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DataPossibleFacetsDetails> findById(@PathVariable Long id) {
        log.info("Fetching possible facet with ID: {}", id);
        DataPossibleFacetsDetails facet = service.findById(id);
        log.info("Fetched possible facet with ID: {}", id);
        return ResponseEntity.ok(facet);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<DataPossibleFacetsDetails>> findByCategory(@PathVariable Long categoryId, Pageable pageable) {
        log.info("Fetching possible facets by category ID: {}", categoryId);
        Page<DataPossibleFacetsDetails> facets = service.findByCategory(categoryId, pageable);
        log.info("Fetched possible facets by category ID: {}", categoryId);
        return ResponseEntity.ok(facets);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DataPossibleFacetsDetails> update(@RequestBody @Valid DataUpdatePossibleFacets data) {
        log.info("Updating possible facet with ID: {}", data.id());
        DataPossibleFacetsDetails updatedFacet = service.update(data);
        log.info("Updated possible facet with ID: {}", data.id());
        return ResponseEntity.ok(updatedFacet);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> disabled(@PathVariable Long id) {
        log.info("Disabling possible facet with ID: {}", id);
        service.disable(id);
        log.info("Disabled possible facet with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
