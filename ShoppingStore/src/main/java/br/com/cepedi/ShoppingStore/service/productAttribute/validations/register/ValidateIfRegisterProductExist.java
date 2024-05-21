package br.com.cepedi.ShoppingStore.service.productAttribute.validations.register;

import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataRegisterProductAttribute;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateIfRegisterProductExist implements ValidationRegisterProductAttribute {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void validation(DataRegisterProductAttribute data) {
        if (data.productId() != null && !productRepository.existsById(data.productId())){
            throw new ValidationException("The provided product id does not exist");
        }
    }
}
