package br.com.cepedi.ShoppingStore.service.possibleFacets.validations.update;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataUpdatePossibleFacets;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryDisabledForPossibleFacetsUpdate implements ValidationUpdatePossibleFacets{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void validation(DataUpdatePossibleFacets data) {
        if(data.categoryId() != null && categoryRepository.existsById(data.categoryId())){
            Category category = categoryRepository.getReferenceById(data.categoryId());
            if(category.getDisabled()){
                throw new ValidationException("Category required is disabled");
            }
        }
    }
}
