package br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.details;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input.DataRegisterShoppingCartItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.math.BigInteger;

public record DataDetailsShoppingCartItem (

        @NotBlank(message = "Name cannot be blank")
        String name,

        BigDecimal price,

        @NotNull(message = "Quantity cannot be null")
        @Positive(message = "Quantity must be a positive value")
        BigInteger quantity,

        @NotNull(message = "Product ID cannot be null")
        Long idProduct
) {

    public DataDetailsShoppingCartItem(ShoppingCartItem shoppingCartItem){
        this(shoppingCartItem.getName(),shoppingCartItem.getPricePurchase(),shoppingCartItem.getQuantity(),shoppingCartItem.getProduct().getId());
    }

}
