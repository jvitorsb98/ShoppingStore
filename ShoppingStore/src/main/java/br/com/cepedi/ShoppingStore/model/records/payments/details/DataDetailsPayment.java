package br.com.cepedi.ShoppingStore.model.records.payments.details;

import br.com.cepedi.ShoppingStore.model.entitys.Payment;
import br.com.cepedi.ShoppingStore.model.enums.PaymentType;

public record DataDetailsPayment(

        Long id,

        Long idShoppingCart,

        PaymentType paymentType


) {

    public DataDetailsPayment(Payment payment){
        this(payment.getId(),payment.getShoppingCart().getId(),payment.getPaymentType());
    }

}
