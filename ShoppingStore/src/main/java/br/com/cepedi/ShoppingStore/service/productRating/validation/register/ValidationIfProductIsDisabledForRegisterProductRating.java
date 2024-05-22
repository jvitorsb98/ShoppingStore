package br.com.cepedi.ShoppingStore.service.productRating.validation.register;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationIfProductIsDisabledForRegisterProductRating implements ValidationProductRatingRegister{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void validation(DataRegisterProductRating data) {
        if (productRepository.existsById(data.productId())) {
            Product product = productRepository.getReferenceById(data.productId());
            if(product.getDisabled()){
                throw new ValidationException("The provided product id is disabled");
            }
        }
    }
}
