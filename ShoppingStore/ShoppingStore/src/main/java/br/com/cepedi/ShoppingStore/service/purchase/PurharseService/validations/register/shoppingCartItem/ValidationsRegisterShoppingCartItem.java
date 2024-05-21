package br.com.cepedi.ShoppingStore.service.purchase.PurharseService.validations.register.shoppingCartItem;

import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input.DataRegisterShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.input.DataRegisterShoppingCart;

public interface ValidationsRegisterShoppingCartItem {

    void validation(DataRegisterShoppingCartItem data);

}
