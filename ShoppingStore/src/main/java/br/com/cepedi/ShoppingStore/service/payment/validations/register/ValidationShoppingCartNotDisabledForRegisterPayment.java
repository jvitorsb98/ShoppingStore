package br.com.cepedi.ShoppingStore.service.payment.validations.register;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.model.records.payments.input.DataRegisterPayment;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationShoppingCartNotDisabledForRegisterPayment implements ValidationsRegisterPayment {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Override
    public void validation(DataRegisterPayment dataRegisterPayment) {
        if(shoppingCartRepository.existsById(dataRegisterPayment.shoppingCartId())){
            ShoppingCart shoppingCart = shoppingCartRepository.getReferenceById(dataRegisterPayment.shoppingCartId());
            if (shoppingCart.getDisabled()) {
                throw new ValidationException("The required Shopping Cart is disabled");
            }
        }
    }
}
