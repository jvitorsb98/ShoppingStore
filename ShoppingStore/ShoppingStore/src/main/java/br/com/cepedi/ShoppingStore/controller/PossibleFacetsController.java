package br.com.cepedi.ShoppingStore.controller;

import br.com.cepedi.ShoppingStore.model.PossibleFacets;
import br.com.cepedi.ShoppingStore.service.PossibleFacetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/possible-facets")
public class PossibleFacetsController {

    private final PossibleFacetsService possibleFacetsService;

    @Autowired
    public PossibleFacetsController(PossibleFacetsService possibleFacetsService) {
        this.possibleFacetsService = possibleFacetsService;
    }

    // Endpoint to get all PossibleFacets
    @GetMapping
    public List<PossibleFacets> getAllPossibleFacets() {
        return possibleFacetsService.getAllPossibleFacets();
    }

    // Endpoint to get a PossibleFacets by ID
    @GetMapping("/{id}")
    public PossibleFacets getPossibleFacetsById(@PathVariable Long id) {
        return possibleFacetsService.getPossibleFacetsById(id);
    }

    // Endpoint to save a new PossibleFacets
    @PostMapping
    public PossibleFacets savePossibleFacets(@RequestBody PossibleFacets possibleFacets) {
        return possibleFacetsService.savePossibleFacets(possibleFacets);
    }

    // Endpoint to delete a PossibleFacets by ID
    @DeleteMapping("/{id}")
    public void deletePossibleFacets(@PathVariable Long id) {
        possibleFacetsService.deletePossibleFacets(id);
    }
}
