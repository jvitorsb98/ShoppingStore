package br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.details;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input.DataRegisterShoppingCartItem;

import java.math.BigDecimal;
import java.math.BigInteger;

public record DataDetailsShoppingCartItem (

        String name,

        BigDecimal price,

        BigInteger quantity,

        Long idProduct
) {

    public DataDetailsShoppingCartItem(ShoppingCartItem shoppingCartItem){
        this(shoppingCartItem.getName(),shoppingCartItem.getPricePurchase(),shoppingCartItem.getQuantity(),shoppingCartItem.getProduct().getId());
    }

}
