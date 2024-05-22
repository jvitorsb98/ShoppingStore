package br.com.cepedi.ShoppingStore.service.productRating.validation.update;


import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationIfProductDisabledForUpdateProductRating implements ValidationUpdateProductRating{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void validation(DataUpdateProductRating data) {
        if (productRepository.existsById(data.productId())) {
            Product product = productRepository.getReferenceById(data.productId());
            if(product.getDisabled()){
                throw new ValidationException("The provided product id is disabled");
            }
        }
    }
}
