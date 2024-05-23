package br.com.cepedi.ShoppingStore.service.productRating.validation.disabled;

import br.com.cepedi.ShoppingStore.model.entitys.ProductRating;
import br.com.cepedi.ShoppingStore.repository.ProductRatingRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationIfProductRatingAlreadyDisabled implements ValidationDisabledProductRating{

    @Autowired
    private ProductRatingRepository productRatingRepository;

    @Override
    public void validation(Long id) {
        if(productRatingRepository.existsById(id)){
            ProductRating productRating = productRatingRepository.getReferenceById(id);
            if(productRating.getDisabled()){
                throw  new ValidationException("The required product rating already disabled");
            }
        }

    }
}
