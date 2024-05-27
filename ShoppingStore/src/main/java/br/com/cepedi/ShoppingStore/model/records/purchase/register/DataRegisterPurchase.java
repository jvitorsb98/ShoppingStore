package br.com.cepedi.ShoppingStore.model.records.purchase.register;

import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input.DataRegisterShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.input.DataRegisterShoppingCart;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DataRegisterPurchase(

        @NotNull(message = "{notnull.purchase.shoppingCart}")
        @NotBlank(message = "{notblank.purchase.shoppingCart}")
        DataRegisterShoppingCart dataRegisterShoppingCart,

        @NotNull(message = "{notnull.purchase.items}")
        @NotBlank(message = "{notblank.purchase.items}")
        List<DataRegisterShoppingCartItem> items

) {
}
