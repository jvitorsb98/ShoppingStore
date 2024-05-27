package br.com.cepedi.ShoppingStore.model.records.shoppingCart.input;

import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.PositiveOrZero;
//
//import java.math.BigDecimal;

public record DataRegisterShoppingCart(

        @NotBlank(message = "{notblank.shoppingCart.userId}")
        Long userId

) {
}
