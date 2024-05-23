package br.com.cepedi.ShoppingStore.service.productRating.validation.update;

import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationUserExistsForUpdaterProductRating implements ValidationUpdateProductRating{

    @Autowired
    private UserRepository userRepository;


    @Override
    public void validation(DataUpdateProductRating data) {
        if (data.userId()!= null && !userRepository.existsById(data.userId())) {
            throw new ValidationException("The provided user id does not exist");
        }
    }
}
