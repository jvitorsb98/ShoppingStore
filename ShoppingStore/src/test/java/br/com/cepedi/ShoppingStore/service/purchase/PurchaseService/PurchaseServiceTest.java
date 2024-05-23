package br.com.cepedi.ShoppingStore.service.purchase.PurchaseService;

import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.details.DataDetailsShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.purchase.details.DataDetailsPurchase;
import br.com.cepedi.ShoppingStore.model.records.purchase.register.DataRegisterPurchase;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.details.DataDetailsShoppingCart;

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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
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
        DataDetailsShoppingCart dataDetailsShoppingCart = mock(DataDetailsShoppingCart.class);
        DataDetailsShoppingCartItem dataDetailsShoppingCartItem = mock(DataDetailsShoppingCartItem.class);

        when(shoppingCartService.register(dataRegisterPurchase)).thenReturn(dataDetailsShoppingCart);
        when(shoppingCartItemService.registerItems(anyList(), anyLong()))
                .thenReturn(Collections.singletonList(dataDetailsShoppingCartItem));

        DataDetailsPurchase result = purchaseService.register(dataRegisterPurchase);

        assertNotNull(result);
        verify(shoppingCartService).register(dataRegisterPurchase);
        verify(shoppingCartItemService).registerItems(anyList(), anyLong());
    }



}
