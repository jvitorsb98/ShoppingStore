package br.com.cepedi.ShoppingStore.service.productRating.validation.register;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationIfUserIsDisabledForRegisterProductRating implements ValidationProductRatingRegister{

    @Autowired
    private UserRepository userRepository;

    @Override
    public void validation(DataRegisterProductRating data) {
        if (userRepository.existsById(data.Userid())) {
            User user = userRepository.getReferenceById(data.Userid());
            if(!user.getActivated()){
                throw new ValidationException("The provided user id is disabled");
            }
        }
    }
}
