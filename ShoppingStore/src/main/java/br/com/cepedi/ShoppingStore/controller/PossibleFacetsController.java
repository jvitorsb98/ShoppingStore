package br.com.cepedi.ShoppingStore.controller;

import br.com.cepedi.ShoppingStore.model.PossibleFacets;
import br.com.cepedi.ShoppingStore.service.PossibleFacetsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/possible-facets")
public class PossibleFacetsController {

    private final PossibleFacetsService possibleFacetsService;
    private static final Logger logger = LoggerFactory.getLogger(PossibleFacetsController.class);

    @Autowired
    public PossibleFacetsController(PossibleFacetsService possibleFacetsService) {
        this.possibleFacetsService = possibleFacetsService;
    }

    // Endpoint to get all PossibleFacets
    @GetMapping
    public List<PossibleFacets> getAllPossibleFacets() {
        logger.debug("Getting all possible facets");
        return possibleFacetsService.getAllPossibleFacets();
    }

    // Endpoint to get a PossibleFacets by ID
    @GetMapping("/{id}")
    public PossibleFacets getPossibleFacetsById(@PathVariable Long id) {
        logger.debug("Getting possible facets by ID: {}", id);
        return possibleFacetsService.getPossibleFacetsById(id);
    }

    // Endpoint to save a new PossibleFacets
    @PostMapping
    public PossibleFacets savePossibleFacets(@RequestBody PossibleFacets possibleFacets) {
        logger.debug("Saving possible facets: {}", possibleFacets);
        return possibleFacetsService.savePossibleFacets(possibleFacets);
    }

    // Endpoint to delete a PossibleFacets by ID
    @DeleteMapping("/{id}")
    public void deletePossibleFacets(@PathVariable Long id) {
        logger.debug("Deleting possible facets by ID: {}", id);
        possibleFacetsService.deletePossibleFacets(id);
    }
}
