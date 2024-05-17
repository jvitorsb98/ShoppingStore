package br.com.cepedi.ShoppingStore.model.records.shoppingCart.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DataRegisterShoppingCart(

        @NotNull
        @Positive
        BigDecimal price,

        @NotNull
        Long userId

) {
}
