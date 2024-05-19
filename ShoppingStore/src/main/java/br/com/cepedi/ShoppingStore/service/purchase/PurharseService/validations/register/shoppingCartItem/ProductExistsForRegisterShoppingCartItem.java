package br.com.cepedi.ShoppingStore.service.purchase.PurharseService.validations.register.shoppingCartItem;

import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input.DataRegisterShoppingCartItem;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductExistsForRegisterShoppingCartItem implements ValidationsRegisterShoppingCartItem{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void validation(DataRegisterShoppingCartItem data) {
        if (data.productId() != null && !productRepository.existsById(data.productId())) {
            throw new ValidationException("The provided product id does not exist");
        }
    }
}
