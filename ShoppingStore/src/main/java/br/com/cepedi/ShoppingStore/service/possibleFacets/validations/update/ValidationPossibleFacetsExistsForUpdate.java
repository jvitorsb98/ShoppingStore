package br.com.cepedi.ShoppingStore.service.possibleFacets.validations.update;

import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataUpdatePossibleFacets;
import br.com.cepedi.ShoppingStore.repository.PossibleFacetsRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationPossibleFacetsExistsForUpdate implements ValidationUpdatePossibleFacets{

    @Autowired
    private PossibleFacetsRepository possibleFacetsRepository;

    @Override
    public void validation(DataUpdatePossibleFacets data) {
        if(!possibleFacetsRepository.existsById(data.id())){
            throw new ValidationException("The required possible facets not exists");
        }
    }
}
