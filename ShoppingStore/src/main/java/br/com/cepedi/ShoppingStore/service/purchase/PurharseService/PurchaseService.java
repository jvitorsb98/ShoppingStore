package br.com.cepedi.ShoppingStore.service.purchase.PurharseService;

import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.details.DataDetailsShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.purchase.details.DataDetailsPurchase;
import br.com.cepedi.ShoppingStore.model.records.purchase.register.DataRegisterPurchase;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.details.DataDetailsShoppingCart;
import br.com.cepedi.ShoppingStore.service.purchase.PurharseService.validations.register.shoppingCart.ValidationsRegisterShoppingCart;
import br.com.cepedi.ShoppingStore.service.purchase.PurharseService.validations.register.shoppingCartItem.ValidationsRegisterShoppingCartItem;
import br.com.cepedi.ShoppingStore.service.purchase.shoppingCart.ShoppingCartService;
import br.com.cepedi.ShoppingStore.service.purchase.shoppingCartItem.ShoppingCartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShoppingCartItemService shoppingCartItemService;

    @Autowired
    private List<ValidationsRegisterShoppingCart> validationsRegisterShoppingCarts;

    @Autowired
    private List<ValidationsRegisterShoppingCartItem> validationsRegisterShoppingCartItems;


    public DataDetailsPurchase register(DataRegisterPurchase data){
        // Validar o carrinho de compras
        validationsRegisterShoppingCarts.forEach(validations -> validations.validation(data.dataRegisterShoppingCart()));
        // Validar cada item do carrinho de compras
        data.items().forEach(item ->
                validationsRegisterShoppingCartItems.forEach(validation ->
                        validation.validation(item)
                )
        );

        DataDetailsShoppingCart dataDetailsShoppingCart = shoppingCartService.register(data);
        List<DataDetailsShoppingCartItem> items = shoppingCartItemService.registerItems(data.items(),dataDetailsShoppingCart.id());
        return new DataDetailsPurchase(dataDetailsShoppingCart, items);
    }

}
