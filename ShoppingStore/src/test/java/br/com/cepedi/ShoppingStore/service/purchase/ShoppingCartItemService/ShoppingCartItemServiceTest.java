package br.com.cepedi.ShoppingStore.service.purchase.ShoppingCartItemService;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.details.DataDetailsShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input.DataRegisterShoppingCartItem;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartItemRepository;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import br.com.cepedi.ShoppingStore.service.purchase.shoppingCartItem.ShoppingCartItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartItemServiceTest {

    @InjectMocks
    private ShoppingCartItemService shoppingCartItemService;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Mock
    private ProductRepository productRepository;

    private ShoppingCart shoppingCart;
    private List<DataRegisterShoppingCartItem> items;

    @BeforeEach
    public void setUp() {
        shoppingCart = new ShoppingCart();
        items = new ArrayList<>();
    }

    @Test
    public void testRegisterItems() {
        // Mock repository behavior
        when(shoppingCartRepository.getReferenceById(anyLong())).thenReturn(shoppingCart);

        // Mock data
        DataRegisterShoppingCartItem shoppingCartItemData = new DataRegisterShoppingCartItem(BigInteger.ONE, 1L);
        items.add(shoppingCartItemData);

        Product product = new Product();
        when(productRepository.getReferenceById(anyLong())).thenReturn(product);

        // Call the method
        List<DataDetailsShoppingCartItem> result = shoppingCartItemService.registerItems(items, 1L);

        // Verify the result
        assertEquals(1, result.size());

        // Verify interactions
        verify(shoppingCartRepository).getReferenceById(anyLong());
        verify(productRepository).getReferenceById(anyLong());
        verify(shoppingCartItemRepository, times(1)).save(any(ShoppingCartItem.class));
    }

    @Test
    public void testDisabled() {
        // Mock data
        ShoppingCartItem shoppingCartItemMock = mock(ShoppingCartItem.class);
        when(shoppingCartItemRepository.getReferenceById(anyLong())).thenReturn(shoppingCartItemMock);

        // Call the method
        shoppingCartItemService.disabled(1L);

        // Verify interactions
        verify(shoppingCartItemRepository).getReferenceById(anyLong());
        verify(shoppingCartItemMock).disable(); // Corrigido para verificar o mock
    }

}