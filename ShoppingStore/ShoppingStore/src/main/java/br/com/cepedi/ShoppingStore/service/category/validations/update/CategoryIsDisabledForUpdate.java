package br.com.cepedi.ShoppingStore.service.category.validations.update;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.records.category.input.DataUpdateCategory;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryIsDisabledForUpdate implements CategoryValidatorUpdate{

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public void validation(DataUpdateCategory dataUpdateCategory) {
        if(categoryRepository.existsById(dataUpdateCategory.id())){
            Category category = categoryRepository.getReferenceById(dataUpdateCategory.id());
                if(category.getDisabled()){
                    throw new ValidationException("The required category is disabled");
                }
            }
        }

}
