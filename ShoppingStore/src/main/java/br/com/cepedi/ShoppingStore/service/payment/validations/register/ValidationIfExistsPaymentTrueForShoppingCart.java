package br.com.cepedi.ShoppingStore.service.payment.validations.register;

import br.com.cepedi.ShoppingStore.model.records.payments.input.DataRegisterPayment;
import br.com.cepedi.ShoppingStore.repository.PaymentRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationIfExistsPaymentTrueForShoppingCart implements ValidationsRegisterPayment{

    @Autowired
    private PaymentRepository paymentRepository;


    @Override
    public void validation(DataRegisterPayment dataRegisterPayment) {
        if(paymentRepository.existsEnabledPaymentByShoppingCartId(dataRegisterPayment.shoppingCartId())){
            throw new ValidationException("the shopping cart already contains an active payment");
        }
    }
}
