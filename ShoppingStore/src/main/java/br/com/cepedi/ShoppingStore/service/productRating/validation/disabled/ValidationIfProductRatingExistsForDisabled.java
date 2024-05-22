package br.com.cepedi.ShoppingStore.service.productRating.validation.disabled;


import br.com.cepedi.ShoppingStore.repository.ProductRatingRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationIfProductRatingExistsForDisabled implements ValidationDisabledProductRating{

    @Autowired
    private ProductRatingRepository productRatingRepository;

    @Override
    public void validation(Long id) {
        if(!productRatingRepository.existsById(id)){
            throw new ValidationException("The product rating required not exists");
        }
    }
}
