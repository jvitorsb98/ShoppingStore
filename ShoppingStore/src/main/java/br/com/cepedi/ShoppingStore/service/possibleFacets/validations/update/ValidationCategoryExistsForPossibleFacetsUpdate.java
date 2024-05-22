package br.com.cepedi.ShoppingStore.service.possibleFacets.validations.update;

import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataUpdatePossibleFacets;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationCategoryExistsForPossibleFacetsUpdate implements ValidationUpdatePossibleFacets{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void validation(DataUpdatePossibleFacets data) {
        if(data.categoryId() != null && categoryRepository.existsById(data.categoryId())){
            throw new ValidationException("Category not exists");
        }
    }
}
