package br.com.cepedi.ShoppingStore.model.records.shoppingCart.details;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
//import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCartItem;

import java.math.BigDecimal;
//import java.util.Set;

public record DataDetailsShoppingCart(

        BigDecimal totalPrice,

        Long UserId

) {

    public DataDetailsShoppingCart(ShoppingCart shoppingCart){
        this(shoppingCart.getTotalPrice(),shoppingCart.getUser().getId());
    }

}
