package br.com.cepedi.ShoppingStore.service.purchase.shoppingCartItem;


import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.details.DataDetailsShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input.DataRegisterShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.details.DataDetailsShoppingCart;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.input.DataRegisterShoppingCart;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartItemRepository;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartItemService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Autowired
    private ProductRepository productRepository;


    public List<DataDetailsShoppingCartItem> registerItems(List<DataRegisterShoppingCartItem> items , Long idShoppingCart ){

        ShoppingCart shoppingCart = shoppingCartRepository.getReferenceById(idShoppingCart);
        List<DataDetailsShoppingCartItem> shoppingCartItemsDetailsList = new ArrayList<>();

        items.forEach(item -> {
            Product product = productRepository.getReferenceById(item.productId());
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem(item,shoppingCart,product);
            shoppingCartItemRepository.save(shoppingCartItem);
            shoppingCartItemsDetailsList.add(new DataDetailsShoppingCartItem(shoppingCartItem));
        });

        return shoppingCartItemsDetailsList;
    }

    public void disabled(Long id){
        ShoppingCartItem shoppingCartItem = shoppingCartItemRepository.getReferenceById(id);
        shoppingCartItem.disable();
    }

}
