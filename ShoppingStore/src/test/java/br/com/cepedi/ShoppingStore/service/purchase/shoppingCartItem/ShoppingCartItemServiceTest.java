package br.com.cepedi.ShoppingStore.service.purchase.shoppingCartItem;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input.DataRegisterShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.details.DataDetailsShoppingCart;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartItemRepository;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShoppingCartItemServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ShoppingCartItemService shoppingCartItemService;

    public ShoppingCartItemServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterItems() {
        // Given
        User user = new User();
        user.setId(1L);

        DataDetailsShoppingCart dataDetailsShoppingCart = new DataDetailsShoppingCart(1L, BigDecimal.ZERO, user.getId());

        DataRegisterShoppingCartItem item1 = new DataRegisterShoppingCartItem(BigInteger.TEN, 1L);
        DataRegisterShoppingCartItem item2 = new DataRegisterShoppingCartItem(BigInteger.ONE, 2L);

        List<DataRegisterShoppingCartItem> items = Arrays.asList(item1, item2);

        ShoppingCart shoppingCart = new ShoppingCart(user);
        shoppingCart.setTotalPrice(BigDecimal.ZERO);

        when(shoppingCartRepository.getReferenceById(dataDetailsShoppingCart.id())).thenReturn(shoppingCart);

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setPrice(BigDecimal.TEN);
        product1.setQuantity(BigInteger.valueOf(20));
        when(productRepository.getReferenceById(1L)).thenReturn(product1);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setPrice(BigDecimal.ONE);
        product2.setQuantity(BigInteger.TEN);
        when(productRepository.getReferenceById(2L)).thenReturn(product2);

        ShoppingCartItem shoppingCartItem1 = new ShoppingCartItem(item1, shoppingCart, product1);
        ShoppingCartItem shoppingCartItem2 = new ShoppingCartItem(item2, shoppingCart, product2);
        when(shoppingCartItemRepository.save(any())).thenReturn(shoppingCartItem1, shoppingCartItem2);

        // When
        shoppingCartItemService.registerItems(items, dataDetailsShoppingCart);

        // Then
        assertEquals(BigDecimal.valueOf(101), shoppingCart.getTotalPrice());
        assertEquals(BigInteger.valueOf(10), product1.getQuantity());
        assertEquals(BigInteger.valueOf(9), product2.getQuantity());
    }

    @Test
    public void testDisabled() {
        // Given
        Long shoppingCartItemId = 1L;
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setId(shoppingCartItemId);
        shoppingCartItem.setQuantity(BigInteger.TEN);
        Product product = new Product();
        product.setId(1L);
        product.setQuantity(BigInteger.valueOf(20));
        shoppingCartItem.setProduct(product);
        when(shoppingCartItemRepository.getReferenceById(shoppingCartItemId)).thenReturn(shoppingCartItem);
        when(productRepository.save(product)).thenReturn(product);

        // When
        shoppingCartItemService.disabled(shoppingCartItemId);

        // Then
        verify(shoppingCartItemRepository, times(1)).getReferenceById(shoppingCartItemId);
        verify(productRepository, times(1)).save(product);
        verify(shoppingCartItemRepository, times(1)).save(shoppingCartItem);  // Ensure shoppingCartItem is saved as well
        assertEquals(BigInteger.valueOf(30), product.getQuantity());
        assertEquals(true, shoppingCartItem.getDisabled());
    }
}

