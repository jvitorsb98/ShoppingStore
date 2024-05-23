package br.com.cepedi.ShoppingStore.service.product.validations.register;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationCategoryIsNotDisabled implements ValidationRegisterProduct{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void validation(DataRegisterProduct data) {
        if (categoryRepository.existsById(data.categoryId())) {
            Category category = categoryRepository.getReferenceById(data.categoryId());
            if(category.getDisabled()){
                throw new ValidationException("The provided category id is disabled");
            }
        }
    }
}
