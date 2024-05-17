package br.com.cepedi.ShoppingStore.model.records.shoppingCart.details;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;

import java.math.BigDecimal;

public record DataDetailsShoppingCart(

        BigDecimal totalPrice,

        Long UserId

) {

    public DataDetailsShoppingCart(ShoppingCart shoppingCart){
        this(shoppingCart.getTotalPrice(),shoppingCart.getUser().getId());
    }

}
