package br.com.cepedi.ShoppingStore.service.product.validations.cancel;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationProductAlreadyDisabledForDisabled implements ValidationDisabledProduct{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void validation(Long id) {
        if(productRepository.existsById(id)){
            Product product = productRepository.getReferenceById(id);
            if(product.getDisabled()){
                throw new ValidationException("The required product already disabled");
            }
        }
    }
}
