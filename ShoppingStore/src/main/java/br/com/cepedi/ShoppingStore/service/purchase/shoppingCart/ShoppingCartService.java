package br.com.cepedi.ShoppingStore.service.purchase.shoppingCart;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.model.records.purchase.register.DataRegisterPurchase;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.details.DataDetailsShoppingCart;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import br.com.cepedi.ShoppingStore.service.purchase.shoppingCartItem.ShoppingCartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;



    @Autowired
    private UserRepository userRepository;




    public DataDetailsShoppingCart register(DataRegisterPurchase data){
        User user = userRepository.getReferenceById(data.dataRegisterShoppingCart().userId());
        ShoppingCart shoppingCart = new ShoppingCart(user);
        shoppingCartRepository.save(shoppingCart);
        return new DataDetailsShoppingCart(shoppingCart);
    }

    public Page<DataDetailsShoppingCart> list(Pageable pageable){
        return shoppingCartRepository.findAll(pageable).map(DataDetailsShoppingCart::new);
    }

    public  DataDetailsShoppingCart details(Long id){
        return new DataDetailsShoppingCart(shoppingCartRepository.getReferenceById(id));
    }

    public Page<DataDetailsShoppingCart> listByUser(Pageable pageable , Long id){
        return shoppingCartRepository.findAllByUser(pageable,id).map(DataDetailsShoppingCart::new);
    }


}
