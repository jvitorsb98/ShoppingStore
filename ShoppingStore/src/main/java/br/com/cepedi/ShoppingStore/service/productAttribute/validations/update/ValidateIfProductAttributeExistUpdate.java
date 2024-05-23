package br.com.cepedi.ShoppingStore.service.productAttribute.validations.update;

import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataUpdateProductAttribute;
import br.com.cepedi.ShoppingStore.repository.ProductAttributeRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateIfProductAttributeExistUpdate implements ValidationUpdateProductAttribute{

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    public void validation(DataUpdateProductAttribute data){
        if (!productAttributeRepository.existsById(data.id())){
            throw new ValidationException("The required ProductAttribute is does not exists");
        }
    }
}
