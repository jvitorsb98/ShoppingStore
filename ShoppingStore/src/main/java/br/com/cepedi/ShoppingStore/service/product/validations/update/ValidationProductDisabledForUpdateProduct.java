package br.com.cepedi.ShoppingStore.service.product.validations.update;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationProductDisabledForUpdateProduct implements ValidationUpdateProduct{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void validation(DataUpdateProduct data) {
        if (!productRepository.existsById(data.id())) {
            Product product = productRepository.getReferenceById(data.id());
            if(product.getDisabled()){
                throw new ValidationException("The provided product is disabled");
            }
        }
    }
}
