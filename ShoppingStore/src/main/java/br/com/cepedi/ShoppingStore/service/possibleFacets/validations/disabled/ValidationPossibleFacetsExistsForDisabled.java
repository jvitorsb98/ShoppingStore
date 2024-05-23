package br.com.cepedi.ShoppingStore.service.possibleFacets.validations.disabled;


import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataUpdatePossibleFacets;
import br.com.cepedi.ShoppingStore.repository.PossibleFacetsRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationPossibleFacetsExistsForDisabled implements ValidationDisabledPossibleFacets{

    @Autowired
    private PossibleFacetsRepository possibleFacetsRepository;

    @Override
    public void validation(Long id) {
        if(!possibleFacetsRepository.existsById(id)){
            throw new ValidationException("The required possible facets not exists");
        }
    }

}
