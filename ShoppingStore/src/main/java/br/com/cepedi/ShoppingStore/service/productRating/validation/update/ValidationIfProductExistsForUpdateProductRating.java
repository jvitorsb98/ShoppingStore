package br.com.cepedi.ShoppingStore.service.productRating.validation.update;

import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationIfProductExistsForUpdateProductRating implements ValidationUpdateProductRating {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void validation(DataUpdateProductRating data) {
        if (!productRepository.existsById(data.productId())) {
            throw new ValidationException("The provided product id does not exist");
        }

    }
}
