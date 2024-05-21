package br.com.cepedi.ShoppingStore.service.category.validations.update;

import br.com.cepedi.ShoppingStore.model.records.category.input.DataUpdateCategory;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryNotExists implements CategoryValidatorUpdate {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void validation(DataUpdateCategory dataUpdateCategory) {
        if(!categoryRepository.existsById(dataUpdateCategory.id())){
            throw new ValidationException("The required category not exists");
        }
    }
}
