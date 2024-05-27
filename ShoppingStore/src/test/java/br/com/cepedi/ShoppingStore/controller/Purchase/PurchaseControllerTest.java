package br.com.cepedi.ShoppingStore.controller.Purchase;

import br.com.cepedi.ShoppingStore.service.purchase.PurharseService.PurchaseService;
import br.com.cepedi.ShoppingStore.service.purchase.shoppingCart.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PurchaseControllerTest {

    @InjectMocks
    private PurchaseController purchaseController;

    @Mock
    private PurchaseService purchaseService;

    @Mock
    private ShoppingCartService shoppingCartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDisable() {
        Long id = 1L;

        ResponseEntity<Void> response = purchaseController.disabled(id);

        assertEquals(ResponseEntity.noContent().build().getStatusCode(), response.getStatusCode());
        verify(shoppingCartService, times(1)).disabled(id);
    }
}
