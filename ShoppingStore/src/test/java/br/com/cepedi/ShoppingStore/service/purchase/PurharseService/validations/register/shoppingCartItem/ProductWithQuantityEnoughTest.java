package br.com.cepedi.ShoppingStore.service.purchase.PurharseService.validations.register.shoppingCartItem;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input.DataRegisterShoppingCartItem;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProductWithQuantityEnoughTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductWithQuantityEnough productWithQuantityEnough;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProductExistsWithEnoughQuantity() {
        // Given
        Long productId = 123L;
        BigInteger quantityInStock = BigInteger.TEN;
        BigInteger requestedQuantity = BigInteger.ONE;

        // Configure mock behavior
        Product product = new Product();
        product.setQuantity(quantityInStock);
        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.getReferenceById(productId)).thenReturn(product);

        // When/Then
        assertDoesNotThrow(() -> {
            productWithQuantityEnough.validation(new DataRegisterShoppingCartItem(requestedQuantity, productId));
        });
    }


    @Test
    public void testProductExistsWithInsufficientQuantity() {
        // Given
        Long productId = 123L;
        BigInteger requestedQuantity = BigInteger.TEN;

        // Mock ProductRepository behavior
        when(productRepository.existsById(productId)).thenReturn(true);
        Product product = new Product();
        product.setQuantity(BigInteger.ZERO); // Assuming quantity is zero or negative
        when(productRepository.getReferenceById(productId)).thenReturn(product);

        // When/Then
        assertThrows(ValidationException.class, () -> {
            productWithQuantityEnough.validation(new DataRegisterShoppingCartItem(requestedQuantity, productId));
        });
    }





  



    
}

