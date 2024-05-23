package br.com.cepedi.ShoppingStore.service.purchase.PurchaseService;

import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.details.DataDetailsShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input.DataRegisterShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.purchase.details.DataDetailsPurchase;
import br.com.cepedi.ShoppingStore.model.records.purchase.register.DataRegisterPurchase;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.details.DataDetailsShoppingCart;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.input.DataRegisterShoppingCart;


import br.com.cepedi.ShoppingStore.service.purchase.PurharseService.PurchaseService;
import br.com.cepedi.ShoppingStore.service.purchase.PurharseService.validations.register.shoppingCart.ValidationsRegisterShoppingCart;
import br.com.cepedi.ShoppingStore.service.purchase.PurharseService.validations.register.shoppingCartItem.ValidationsRegisterShoppingCartItem;
import br.com.cepedi.ShoppingStore.service.purchase.shoppingCart.ShoppingCartService;
import br.com.cepedi.ShoppingStore.service.purchase.shoppingCartItem.ShoppingCartItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {

    @InjectMocks
    private PurchaseService purchaseService;

    @Mock
    private ShoppingCartService shoppingCartService;

    @Mock
    private ShoppingCartItemService shoppingCartItemService;

    @Mock
    private List<ValidationsRegisterShoppingCart> validationsRegisterShoppingCarts;

    @Mock
    private List<ValidationsRegisterShoppingCartItem> validationsRegisterShoppingCartItems;

    private DataRegisterPurchase dataRegisterPurchase;

    @BeforeEach
    void setup() {
        dataRegisterPurchase = mock(DataRegisterPurchase.class);
    }

    @Test
    public void testRegister() {
        // Mock the return data
        DataDetailsShoppingCart dataDetailsShoppingCart = mock(DataDetailsShoppingCart.class);
        DataDetailsShoppingCartItem dataDetailsShoppingCartItem = mock(DataDetailsShoppingCartItem.class);
        DataDetailsPurchase dataDetailsPurchase = new DataDetailsPurchase(dataDetailsShoppingCart, Collections.singletonList(dataDetailsShoppingCartItem));

        // Configure the mocks and input data
        lenient().when(dataRegisterPurchase.dataRegisterShoppingCart()).thenReturn(mock(DataRegisterShoppingCart.class)); // Potential unnecessary stubbing
        lenient().when(dataRegisterPurchase.items()).thenReturn(Collections.singletonList(mock(DataRegisterShoppingCartItem.class))); // Potential unnecessary stubbing
        when(shoppingCartService.register(any())).thenReturn(dataDetailsShoppingCart);
        when(shoppingCartItemService.registerItems(anyList(), any())).thenReturn(dataDetailsPurchase);

        // Call the method under test
        DataDetailsPurchase result = purchaseService.register(dataRegisterPurchase);

        // Verify the results
        assertNotNull(result);
        assertEquals(dataDetailsPurchase, result);

        // Verify the method calls
        verify(shoppingCartService).register(any());
        verify(shoppingCartItemService).registerItems(anyList(), any());

        // Verify the validation calls
        validationsRegisterShoppingCarts.forEach(validation -> verify(validation).validation(any(DataRegisterShoppingCart.class)));
        validationsRegisterShoppingCartItems.forEach(validation -> verify(validation).validation(any(DataRegisterShoppingCartItem.class)));
    }
}
