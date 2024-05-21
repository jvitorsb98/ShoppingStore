package br.com.cepedi.ShoppingStore.service.purchase.shoppingCart.validations.disabled;

import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import br.com.cepedi.ShoppingStore.service.purchase.shoppingCart.ShoppingCartService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationShoppingCartExists implements ValidationsDisabledShoppingCart {

    @Autowired
    private ShoppingCartRepository repository;

    @Override
    public void validation(Long id) {
        if(!repository.existsById(id)){
            throw new ValidationException("The required shopping cart id not exists");
        }
    }
}
