package br.com.cepedi.ShoppingStore.model.records.purchase.details;

import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.details.DataDetailsShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.details.DataDetailsShoppingCart;

import java.util.List;

public record DataDetailsPurchase(
        DataDetailsShoppingCart detailsShoppingCart,
        List<DataDetailsShoppingCartItem> detailsShoppingCartItems
) {

    public DataDetailsPurchase(DataDetailsShoppingCart detailsShoppingCart, List<DataDetailsShoppingCartItem> detailsShoppingCartItems) {
        this.detailsShoppingCart = detailsShoppingCart;
        this.detailsShoppingCartItems = detailsShoppingCartItems;
    }
}
