package br.com.cepedi.ShoppingStore.service.possibleFacets.validations.register;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataRegisterPossibleFacets;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationCategoryIsDisabled implements ValidationRegisterPossibleFacets{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void validation(DataRegisterPossibleFacets data) {
        if(categoryRepository.existsById(data.idCategory())){
            Category category = categoryRepository.getReferenceById(data.idCategory());
            if(category.getDisabled()){
                throw new ValidationException("The required category is disabled");
            }
        }
    }
}
