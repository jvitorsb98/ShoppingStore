package br.com.cepedi.ShoppingStore.service;

import br.com.cepedi.ShoppingStore.model.PossibleFacets;
import br.com.cepedi.ShoppingStore.repository.PossibleFacetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PossibleFacetsService {

    private final PossibleFacetsRepository possibleFacetsRepository;

    @Autowired
    public PossibleFacetsService(PossibleFacetsRepository possibleFacetsRepository) {
        this.possibleFacetsRepository = possibleFacetsRepository;
    }

    // Method to get all PossibleFacets
    public List<PossibleFacets> getAllPossibleFacets() {
        return possibleFacetsRepository.findAll();
    }

    // Method to save a new PossibleFacets
    public PossibleFacets savePossibleFacets(PossibleFacets possibleFacets) {
        return possibleFacetsRepository.save(possibleFacets);
    }

    // Method to get a PossibleFacets by ID
    public PossibleFacets getPossibleFacetsById(Long id) {
        return possibleFacetsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PossibleFacets não encontrado com o ID: " + id));
    }

    // Method to delete a PossibleFacets by ID
    public void deletePossibleFacets(Long id) {
        possibleFacetsRepository.deleteById(id);
    }
}
