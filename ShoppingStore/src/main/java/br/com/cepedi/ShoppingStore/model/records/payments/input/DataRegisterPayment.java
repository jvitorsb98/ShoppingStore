package br.com.cepedi.ShoppingStore.model.records.payments.input;

import br.com.cepedi.ShoppingStore.model.enums.PaymentType;
import jakarta.validation.constraints.NotNull;

public record DataRegisterPayment (

        @NotNull(message = "{notnull.payment.shoppingCartId}")
        Long shoppingCartId,

        @NotNull(message = "{notnull.payment.paymentType}")
        PaymentType paymentType

){


}
