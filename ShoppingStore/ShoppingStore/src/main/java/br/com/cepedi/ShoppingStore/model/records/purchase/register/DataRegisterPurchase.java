package br.com.cepedi.ShoppingStore.model.records.purchase.register;

import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input.DataRegisterShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.input.DataRegisterShoppingCart;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DataRegisterPurchase(

        @NotNull
        @NotBlank
        DataRegisterShoppingCart dataRegisterShoppingCart,

        @NotNull
        @NotBlank
        List<DataRegisterShoppingCartItem> items

) {
}
