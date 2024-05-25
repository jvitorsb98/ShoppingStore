package br.com.cepedi.ShoppingStore.service.purchase.PurharseService.validations.register.shoppingCartItem;

import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input.DataRegisterShoppingCartItem;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProductExistsForRegisterShoppingCartItemTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductExistsForRegisterShoppingCartItem productExistsForRegisterShoppingCartItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProductExists() {
        Long productId = 123L;
        BigInteger quantity = BigInteger.TEN;

        when(productRepository.existsById(productId)).thenReturn(true);

        productExistsForRegisterShoppingCartItem.validation(new DataRegisterShoppingCartItem(quantity, productId));

        verify(productRepository).existsById(productId);
    }

    @Test
    public void testProductDoesNotExist() {
        Long productId = 456L;
        BigInteger quantity = BigInteger.TEN;

        when(productRepository.existsById(productId)).thenReturn(false);

        assertThrows(ValidationException.class, () -> {
            productExistsForRegisterShoppingCartItem.validation(new DataRegisterShoppingCartItem(quantity, productId));
        });

        verify(productRepository).existsById(productId);
    }
}

