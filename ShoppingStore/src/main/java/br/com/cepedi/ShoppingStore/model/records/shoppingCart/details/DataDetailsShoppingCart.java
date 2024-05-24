package br.com.cepedi.ShoppingStore.model.records.shoppingCart.details;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
//import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCartItem;

import java.math.BigDecimal;
//import java.util.Set;

public record DataDetailsShoppingCart(

        Long id,

        BigDecimal totalPrice,

        Long UserId,

        Boolean disabled

) {

    public DataDetailsShoppingCart(ShoppingCart shoppingCart){
        this(shoppingCart.getId() , shoppingCart.getTotalPrice(),shoppingCart.getUser().getId(), shoppingCart.getDisabled());
    }

    public DataDetailsShoppingCart withTotalPrice(BigDecimal totalPrice) {
        return new DataDetailsShoppingCart(this.id, totalPrice, this.UserId, this.disabled);
    }


}
