package br.com.cepedi.ShoppingStore.model.records.payments.input;

import br.com.cepedi.ShoppingStore.model.enums.PaymentType;
import jakarta.validation.constraints.NotNull;

public record DataRegisterPayment (

        @NotNull(message = "Shopping cart ID cannot be null")
        Long shoppingCartId,

        @NotNull(message = "Payment type cannot be null")
        PaymentType paymentType

){


}
