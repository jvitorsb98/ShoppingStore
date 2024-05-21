package br.com.cepedi.ShoppingStore.service.purchase.shoppingCart.validations.disabled;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationShoppingCartAlreadyDisabled implements ValidationsDisabledShoppingCart{

    @Autowired
    private ShoppingCartRepository repository;

    @Override
    public void validation(Long id) {
        if(repository.existsById(id)){
            ShoppingCart shoppingCart = repository.getReferenceById(id);
            if(shoppingCart.getDisabled()){
                throw  new ValidationException("The required shopping cart already disabled");
            }
        }
    }
}
