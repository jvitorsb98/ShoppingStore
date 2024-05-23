package br.com.cepedi.ShoppingStore.service.productAttribute.validations.disabled;

import br.com.cepedi.ShoppingStore.repository.ProductAttributeRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateIfProductAttributeExistDisabled implements ValidationDisabledProductAttribute{

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    public void validation(Long id){
        if (!productAttributeRepository.existsById(id)){
            throw new ValidationException("The required ProductAttribute is does not exists");
        }
    }
}
