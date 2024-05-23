package br.com.cepedi.ShoppingStore.service.category.validations.disabled;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.records.category.input.DataUpdateCategory;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryAlreadyDisabledForDisabled implements CategoryValidatorDisabled{

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public void validation(Long id) {
        if(categoryRepository.existsById(id)){
            Category category = categoryRepository.getReferenceById(id);
            if(category.getDisabled()){
                throw new ValidationException("The required category is disabled");
            }
        }
    }
}
