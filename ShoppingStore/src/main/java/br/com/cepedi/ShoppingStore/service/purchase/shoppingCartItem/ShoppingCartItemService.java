package br.com.cepedi.ShoppingStore.service.purchase.shoppingCartItem;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.details.DataDetailsShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input.DataRegisterShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.purchase.details.DataDetailsPurchase;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.details.DataDetailsShoppingCart;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartItemRepository;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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


    public DataDetailsPurchase registerItems(List<DataRegisterShoppingCartItem> items, DataDetailsShoppingCart dataDetailsShoppingCart) {
        ShoppingCart shoppingCart = shoppingCartRepository.getReferenceById(dataDetailsShoppingCart.id());
        BigDecimal totalPrice = shoppingCart.getTotalPrice() != null ? shoppingCart.getTotalPrice() : BigDecimal.ZERO;

        List<DataDetailsShoppingCartItem> shoppingCartItemsDetailsList = new ArrayList<>();
        for (DataRegisterShoppingCartItem item : items) {
            Product product = productRepository.getReferenceById(item.productId());

            if (product.getQuantity().compareTo(item.quantity()) >= 0) {
                product.setQuantity(product.getQuantity().subtract(item.quantity()));
                ShoppingCartItem shoppingCartItem = new ShoppingCartItem(item, shoppingCart, product);
                shoppingCartItemRepository.save(shoppingCartItem);

                totalPrice = totalPrice.add(product.getPrice().multiply(new BigDecimal(item.quantity())));
                shoppingCartItemsDetailsList.add(new DataDetailsShoppingCartItem(shoppingCartItem));
            } else {
                throw new IllegalArgumentException("Quantidade de produto insuficiente para " + product.getName());
            }
        }

        shoppingCart.setTotalPrice(totalPrice);
        shoppingCartRepository.save(shoppingCart);

        return new DataDetailsPurchase(dataDetailsShoppingCart, shoppingCartItemsDetailsList);
    }



    public void disabled(Long id) {
        ShoppingCartItem shoppingCartItem = shoppingCartItemRepository.getReferenceById(id);
        if (shoppingCartItem == null) {
            return;
        }

        Product product = shoppingCartItem.getProduct();
        if (product == null) {
            throw new IllegalArgumentException("Product associated with shopping cart item is null.");
        }

        shoppingCartItem.disable();
        product.setQuantity(product.getQuantity().add(shoppingCartItem.getQuantity()));
        productRepository.save(product);
        shoppingCartItemRepository.save(shoppingCartItem); // Save the updated shopping cart item
    }
}





