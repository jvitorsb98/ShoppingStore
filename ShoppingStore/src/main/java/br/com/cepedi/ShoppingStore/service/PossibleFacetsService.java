package br.com.cepedi.ShoppingStore.service;

import br.com.cepedi.ShoppingStore.model.PossibleFacets;
import br.com.cepedi.ShoppingStore.repository.PossibleFacetsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PossibleFacetsService {

    private final PossibleFacetsRepository possibleFacetsRepository;
    private static final Logger logger = LoggerFactory.getLogger(PossibleFacetsService.class);

    @Autowired
    public PossibleFacetsService(PossibleFacetsRepository possibleFacetsRepository) {
        logger.debug("Initializing PossibleFacetsService");
        this.possibleFacetsRepository = possibleFacetsRepository;
    }

    // Method to get all PossibleFacets
    public List<PossibleFacets> getAllPossibleFacets() {
        logger.debug("Getting all PossibleFacets");
        return possibleFacetsRepository.findAll();
    }

    // Method to save a new PossibleFacets
    public PossibleFacets savePossibleFacets(PossibleFacets possibleFacets) {
        logger.debug("Saving PossibleFacets: {}", possibleFacets);
        return possibleFacetsRepository.save(possibleFacets);
    }

    // Method to get a PossibleFacets by ID
    public PossibleFacets getPossibleFacetsById(Long id) {
        logger.debug("Getting PossibleFacets by ID: {}", id);
        try {
            return possibleFacetsRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("PossibleFacets not found with ID: " + id));
        } catch (IllegalArgumentException e) {
            logger.error("Error getting PossibleFacets by ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    // Method to delete a PossibleFacets by ID
    public void deletePossibleFacets(Long id) {
        logger.debug("Deleting PossibleFacets with ID: {}", id);
        possibleFacetsRepository.deleteById(id);
    }
}
