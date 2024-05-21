package br.com.cepedi.ShoppingStore.service.productAttribute.validations.disabled;

import br.com.cepedi.ShoppingStore.repository.ProductAttributeRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateIfProductAttributeAlreadyDisabled implements ValidationDisabledProductAttribute{

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Override
    public void validation(Long id){
        Boolean productAttributeDisabled = productAttributeRepository.findDisabledById(id);
        if (productAttributeDisabled){
            throw new ValidationException("The required ProductAttribute already disabled");
        }
    }
}
