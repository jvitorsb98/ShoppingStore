package br.com.cepedi.ShoppingStore.service.productRating.validation.update;

import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.repository.ProductRatingRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;


@Component
public class ValidationUpdateDataProductRatingSome implements ValidationProductRatingUpdate {

    @Autowired
    private ProductRatingRepository repositoryRatingProduct;



    @Override
    public void validation(Long id, DataUpdateProductRating data) {

        if (data.productId() != null && !repositoryRatingProduct.existsById(data.productId())) {
            throw new ValidationException("Product Id not exist");
        }
    }
}
