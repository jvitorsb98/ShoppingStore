package br.com.cepedi.ShoppingStore.service.productRating.validation.disabled;

import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataDisabledProductRating;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.repository.ProductRatingRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationDisabledDataProductRatingSome implements ValidationProductRatingDisabled {

    @Autowired
    private ProductRatingRepository repositoryRatingProduct;


    @Override
    public void validation(Long id, DataDisabledProductRating data) {
        if (data.productId() != null && !repositoryRatingProduct.existsById(data.productId())) {
            throw new ValidationException("Product Id not exist");
        }
    }
}
