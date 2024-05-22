package br.com.cepedi.ShoppingStore.service.purchase.PurharseService.validations.register.shoppingCart;

import br.com.cepedi.ShoppingStore.model.records.shoppingCart.input.DataRegisterShoppingCart;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserExistsForRegisterShoppingCart  implements ValidationsRegisterShoppingCart {

    @Autowired
    private UserRepository repository;

    @Override
    public void validation(DataRegisterShoppingCart data) {
        if (data.userId() != null && !repository.existsById(data.userId())) {
            throw new ValidationException("The provided user id does not exist");
        }
    }
}
