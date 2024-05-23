package br.com.cepedi.ShoppingStore.service.productRating.validation.update;


import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.repository.ProductRatingRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationIfProductRatingIsExistsForUpdate implements ValidationUpdateProductRating{

    @Autowired
    private ProductRatingRepository productRatingRepository;

    @Override
    public void validation(DataUpdateProductRating data) {
        if(!productRatingRepository.existsById(data.id())){
            throw new ValidationException("The required product rating not exists");
        }
    }
}
