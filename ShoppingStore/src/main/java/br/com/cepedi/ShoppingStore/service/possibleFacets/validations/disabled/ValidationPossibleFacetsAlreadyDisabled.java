package br.com.cepedi.ShoppingStore.service.possibleFacets.validations.disabled;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataUpdatePossibleFacets;
import br.com.cepedi.ShoppingStore.repository.PossibleFacetsRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationPossibleFacetsAlreadyDisabled implements ValidationDisabledPossibleFacets{


    @Autowired
    private PossibleFacetsRepository possibleFacetsRepository;

    @Override
    public void validation(Long id) {
        if(possibleFacetsRepository.existsById(id)){
            PossibleFacets possibleFacets = possibleFacetsRepository.getReferenceById(id);
            if(possibleFacets.getDisabled()){
                throw new ValidationException("PossibleFacets required is disabled");
            }
        }
    }
}
