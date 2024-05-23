package br.com.cepedi.ShoppingStore.service.payment.validations.disabled;


import br.com.cepedi.ShoppingStore.model.entitys.Payment;
import br.com.cepedi.ShoppingStore.repository.PaymentRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ValidationPaymentAlreadyDisabled implements ValidationsDisabledPayment{


    @Autowired
    private PaymentRepository paymentRepository;


    @Override
    public void validation(Long id) {
        if(paymentRepository.existsById(id)){
            Payment payment = paymentRepository.getReferenceById(id);
            if(payment.isDisabled()){
                throw new ValidationException("Payment already disabled");
            }
        }
    }
}
