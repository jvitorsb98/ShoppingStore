package br.com.cepedi.ShoppingStore.service.purchase.PurharseService.validations.register.shoppingCartItem;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input.DataRegisterShoppingCartItem;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductWithQuantityEnough implements ValidationsRegisterShoppingCartItem {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void validation(DataRegisterShoppingCartItem data) {
        if (data.productId() != null && productRepository.existsById(data.productId())) {
            Product product = productRepository.getReferenceById(data.productId());
            if (product.getQuantity().compareTo(data.quantity()) <= 0) {
                throw new ValidationException("Requested quantity is not in stock.");
            }
        }
    }
}
