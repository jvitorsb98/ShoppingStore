package br.com.cepedi.ShoppingStore.service.product.validations.register;

import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import jakarta.validation.ValidationException;

@Component
public class ValidationCategoryExists implements ValidationRegisterProduct {

	@Autowired
	private CategoryRepository categoryRepository;
	
    @Override
    public void validation(DataRegisterProduct data) {
    	 if (!categoryRepository.existsById(data.categoryId())) {
             throw new ValidationException("The provided category id does not exist");
         }
    }
}
