package br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.math.BigInteger;

public record DataRegisterShoppingCartItem(

        @NotBlank
        String name,

        @NotNull
        @Positive
        BigDecimal price,

        @NotNull
        @Positive
        BigInteger quantity

) {
}
