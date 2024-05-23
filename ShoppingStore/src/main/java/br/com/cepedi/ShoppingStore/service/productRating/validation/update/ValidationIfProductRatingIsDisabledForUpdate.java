package br.com.cepedi.ShoppingStore.service.productRating.validation.update;

import br.com.cepedi.ShoppingStore.model.entitys.ProductRating;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.repository.ProductRatingRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationIfProductRatingIsDisabledForUpdate implements ValidationUpdateProductRating{

    @Autowired
    private ProductRatingRepository productRatingRepository;

    @Override
    public void validation(DataUpdateProductRating data) {
        if(productRatingRepository.existsById(data.id())){
            ProductRating productRating = productRatingRepository.getReferenceById(data.id());
            if(productRating.getDisabled()){
                throw new ValidationException("The required product rating is disabled");
            }
        }
    }
}
