package br.com.cepedi.ShoppingStore.service.purchase.shoppingCart;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.details.DataProductAttributeDetails;
import br.com.cepedi.ShoppingStore.model.records.purchase.register.DataRegisterPurchase;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.details.DataDetailsShoppingCart;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import br.com.cepedi.ShoppingStore.service.purchase.shoppingCart.validations.disabled.ValidationsDisabledShoppingCart;
import br.com.cepedi.ShoppingStore.service.purchase.shoppingCartItem.ShoppingCartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private List<ValidationsDisabledShoppingCart> validationsDisabledShoppingCarts;

    @Autowired
    private ShoppingCartItemService shoppingCartItemService;

    
    @Autowired
    private UserRepository userRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
			List<ValidationsDisabledShoppingCart> validationsDisabledShoppingCarts,
			ShoppingCartItemService shoppingCartItemService, UserRepository userRepository) {
		super();
		this.shoppingCartRepository = shoppingCartRepository;
		this.validationsDisabledShoppingCarts = validationsDisabledShoppingCarts;
		this.shoppingCartItemService = shoppingCartItemService;
		this.userRepository = userRepository;
	}

	public DataDetailsShoppingCart register(DataRegisterPurchase data){
        User user = userRepository.findById(data.dataRegisterShoppingCart().userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + data.dataRegisterShoppingCart().userId()));

        ShoppingCart shoppingCart = new ShoppingCart(user);
        shoppingCartRepository.save(shoppingCart);
        return new DataDetailsShoppingCart(shoppingCart);
    }

    public Page<DataDetailsShoppingCart> list(Pageable pageable){
        return shoppingCartRepository.findAll(pageable).map(DataDetailsShoppingCart::new);
    }

    public Page<DataDetailsShoppingCart> listDeactivated(Pageable pageable){
        return shoppingCartRepository.findAllByDisabledTrue(pageable).map(DataDetailsShoppingCart::new);
    }

    public DataDetailsShoppingCart details(Long id){
        return new DataDetailsShoppingCart(shoppingCartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found with id: " + id)));
    }

    public Page<DataDetailsShoppingCart> listByUser(Pageable pageable , Long id){
        return shoppingCartRepository.findAllByUser(pageable,id).map(DataDetailsShoppingCart::new);
    }

    public Page<DataDetailsShoppingCart> listByUserAndDisabledTrue(Pageable pageable , Long id){
        return shoppingCartRepository.findAllByUserAndDisabledIsTrue(pageable,id).map(DataDetailsShoppingCart::new);
    }

    public void disabled(Long id){
        validationsDisabledShoppingCarts.forEach(v -> v.validation(id));
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Shopping cart not found with id: " + id));
        shoppingCart.disable();
        shoppingCartRepository.save(shoppingCart); // Salva o carrinho de compras desativado de volta no repositório
        List<ShoppingCartItem> shoppingCartItems = shoppingCartRepository.findAllByShoppingCartId(id);
        shoppingCartItems.forEach(s -> shoppingCartItemService.disabled(s.getId()));
    }

}

