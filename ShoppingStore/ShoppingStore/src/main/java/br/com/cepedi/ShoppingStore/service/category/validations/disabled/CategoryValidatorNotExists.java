package br.com.cepedi.ShoppingStore.service.category.validations.disabled;

import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidatorNotExists implements CategoryValidatorDisabled{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void validation(Long id) {
        if(!categoryRepository.existsById(id)){
            throw new ValidationException("The required category not exists");
        }
    }
}
