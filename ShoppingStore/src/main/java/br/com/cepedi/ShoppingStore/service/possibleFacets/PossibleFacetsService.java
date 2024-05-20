package br.com.cepedi.ShoppingStore.service.possibleFacets;



import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.details.DataPossibleFacetsDetails;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataRegisterPossibleFacets;
import br.com.cepedi.ShoppingStore.repository.PossibleFacetsRepository;
import br.com.cepedi.ShoppingStore.service.possibleFacets.validations.BasicPossibleFacetsValidator;
import br.com.cepedi.ShoppingStore.service.possibleFacets.validations.PossibleFacetsValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PossibleFacetsService {

    @Autowired
    private PossibleFacetsRepository possibleFacetsRepository;

    @Autowired
    private List<PossibleFacetsValidator> validators;
    
    @Autowired
    private BasicPossibleFacetsValidator basicPossibleFacetsValidator;

    public DataPossibleFacetsDetails register(DataRegisterPossibleFacets data, Category category) {
        PossibleFacets possibleFacets = new PossibleFacets(data, category);
        validators.forEach(validator -> validator.validateRegister(possibleFacets));
        possibleFacetsRepository.save(possibleFacets);
        return new DataPossibleFacetsDetails(possibleFacets.getId(), possibleFacets.getName(), possibleFacets.getCategory().getId());
    }

    public Page<DataPossibleFacetsDetails> listAll(Pageable pageable) {
        return possibleFacetsRepository.findAll(pageable)
                .map(this::convertToDataPossibleFacetsDetails);
    }

    public DataPossibleFacetsDetails findById(Long id) {
        PossibleFacets possibleFacets = possibleFacetsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PossibleFacets not found"));
        return convertToDataPossibleFacetsDetails(possibleFacets);
    }

    public Page<DataPossibleFacetsDetails> findByCategory(Category category, Pageable pageable) {
        return possibleFacetsRepository.findByCategory(category, pageable)
                .map(this::convertToDataPossibleFacetsDetails);
    }

    private DataPossibleFacetsDetails convertToDataPossibleFacetsDetails(PossibleFacets possibleFacets) {
        return new DataPossibleFacetsDetails(possibleFacets.getId(), possibleFacets.getName(), possibleFacets.getCategory().getId());
    }
    
    public DataPossibleFacetsDetails update(Long id, DataRegisterPossibleFacets newData, Category newCategory) {
        PossibleFacets possibleFacets = possibleFacetsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PossibleFacets not found"));
        possibleFacets.setName(newData.name());
        possibleFacets.setCategory(newCategory);
        basicPossibleFacetsValidator.validateUpdate(possibleFacets);
        possibleFacetsRepository.save(possibleFacets);
        return new DataPossibleFacetsDetails(possibleFacets.getId(), possibleFacets.getName(), possibleFacets.getCategory().getId());
    }
    
    public DataPossibleFacetsDetails disable(Long id) {
        PossibleFacets possibleFacets = possibleFacetsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PossibleFacets not found"));
        basicPossibleFacetsValidator.validateDisable(possibleFacets);
        possibleFacets.disable();
        possibleFacetsRepository.save(possibleFacets);
        return new DataPossibleFacetsDetails(possibleFacets.getId(), possibleFacets.getName(), possibleFacets.getCategory().getId());
    }
}

