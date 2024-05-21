package br.com.cepedi.ShoppingStore.service.product.validations.update;


import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationCategoryExistsForUpdateProduct implements ValidationUpdateProduct {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public void validation(DataUpdateProduct data) {
        if (!categoryRepository.existsById(data.categoryId())) {
            throw new ValidationException("The provided category id does not exist");
        }
    }
}
