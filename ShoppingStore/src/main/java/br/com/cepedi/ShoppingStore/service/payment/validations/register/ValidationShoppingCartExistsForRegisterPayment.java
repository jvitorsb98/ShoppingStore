package br.com.cepedi.ShoppingStore.service.payment.validations.register;

import br.com.cepedi.ShoppingStore.model.records.payments.input.DataRegisterPayment;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationShoppingCartExistsForRegisterPayment implements ValidationsRegisterPayment{

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Override
    public void validation(DataRegisterPayment dataRegisterPayment) {
        if(!shoppingCartRepository.existsById(dataRegisterPayment.shoppingCartId())){
            throw new ValidationException("The required Shopping Cart Not Exists");
        }
    }
}
