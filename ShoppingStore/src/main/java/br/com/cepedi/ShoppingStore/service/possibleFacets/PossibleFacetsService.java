package br.com.cepedi.ShoppingStore.service.possibleFacets;



import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.details.DataPossibleFacetsDetails;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataRegisterPossibleFacets;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataUpdatePossibleFacets;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import br.com.cepedi.ShoppingStore.repository.PossibleFacetsRepository;

import br.com.cepedi.ShoppingStore.service.possibleFacets.validations.disabled.ValidationDisabledPossibleFacets;
import br.com.cepedi.ShoppingStore.service.possibleFacets.validations.register.ValidationRegisterPossibleFacets;
import br.com.cepedi.ShoppingStore.service.possibleFacets.validations.update.ValidationUpdatePossibleFacets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PossibleFacetsService {


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PossibleFacetsRepository possibleFacetsRepository;

    @Autowired
    private List<ValidationRegisterPossibleFacets> validationRegisterPossibleFacetsList;

    @Autowired
    private List<ValidationUpdatePossibleFacets> validationUpdatePossibleFacetsList;

    @Autowired
    private List<ValidationDisabledPossibleFacets> validationDisabledPossibleFacetsList;
    
    public Page<DataPossibleFacetsDetails> list(Pageable pageable) {
        return possibleFacetsRepository.findAll(pageable)
                .map(DataPossibleFacetsDetails::new);
    }

    public DataPossibleFacetsDetails register(DataRegisterPossibleFacets data) {
        validationRegisterPossibleFacetsList.forEach(v -> v.validation(data));
        Category category = categoryRepository.getReferenceById(data.idCategory());
        PossibleFacets possibleFacets = new PossibleFacets(data, category);
        possibleFacetsRepository.save(possibleFacets);
        return new DataPossibleFacetsDetails(possibleFacets);
    }

    public Page<DataPossibleFacetsDetails> listAll(Pageable pageable) {
        return possibleFacetsRepository.findAll(pageable)
                .map(DataPossibleFacetsDetails::new);
    }

    public DataPossibleFacetsDetails findById(Long id) {
        PossibleFacets possibleFacets = possibleFacetsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PossibleFacets not found"));
        return new  DataPossibleFacetsDetails(possibleFacets);
    }

    public Page<DataPossibleFacetsDetails> findByCategory(Category category, Pageable pageable) {
        return possibleFacetsRepository.findByCategory(category, pageable)
                .map(DataPossibleFacetsDetails::new);
    }


    public DataPossibleFacetsDetails update(DataUpdatePossibleFacets data) {
        validationUpdatePossibleFacetsList.forEach(v -> v.validation(data));
        PossibleFacets possibleFacets = possibleFacetsRepository.getReferenceById(data.id());
        Category category = null;
        if(data.categoryId()!=null){
            category = categoryRepository.getReferenceById(data.categoryId());
        }
        possibleFacets.updateDataPossibleFacets(data,category);
        return new  DataPossibleFacetsDetails(possibleFacets);
    }
    
    public void disable(Long id) {
        validationDisabledPossibleFacetsList.forEach(v -> v.validation(id));
        PossibleFacets possibleFacets = possibleFacetsRepository.getReferenceById(id);
        possibleFacets.disable();
    }
}

