package br.com.cepedi.ShoppingStore.service.productAttribute.validations.register;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataRegisterProductAttribute;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateIfProductIsDisabled implements ValidationRegisterProductAttribute{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void validation(DataRegisterProductAttribute data) {
        Product product = productRepository.getReferenceById(data.productId());
        if (product.getDisabled()){
            throw new ValidationException("The provided product is disabled so can't assign a ProductAttribute");
        }
    }
}
