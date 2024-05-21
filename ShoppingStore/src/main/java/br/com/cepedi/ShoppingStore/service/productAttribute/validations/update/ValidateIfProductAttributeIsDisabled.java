package br.com.cepedi.ShoppingStore.service.productAttribute.validations.update;

import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataUpdateProductAttribute;
import br.com.cepedi.ShoppingStore.repository.ProductAttributeRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateIfProductAttributeIsDisabled implements ValidationUpdateProductAttribute{

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Override
    public void validation(DataUpdateProductAttribute data){
        Boolean productAttributeDisabled = productAttributeRepository.findDisabledById(data.id());
        if (productAttributeDisabled){
            throw new ValidationException("The required ProductAttribute is disabled");
        }
    }
}
