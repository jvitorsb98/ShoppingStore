package br.com.cepedi.ShoppingStore.service.productRating.validation.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.repository.ProductRatingRepository;
import jakarta.validation.ValidationException;

@Component
public class ValidationUpdateProductRatingSome implements ValidationUpdateProductRating {

    @Autowired
    private ProductRatingRepository repositoryProductRating;

    @Override
    public void validation(Long id, DataUpdateProductRating data) {
        if (data.productId() != null && !repositoryProductRating.existsById(data.productId())) {
            throw new ValidationException("The provided product id does not exist");
        }
    }
}
