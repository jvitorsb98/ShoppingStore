package br.com.cepedi.ShoppingStore.service.purchase.shoppingCart;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.model.records.purchase.register.DataRegisterPurchase;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.details.DataDetailsShoppingCart;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.input.DataRegisterShoppingCart;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import br.com.cepedi.ShoppingStore.service.purchase.shoppingCart.validations.disabled.ValidationsDisabledShoppingCart;
import br.com.cepedi.ShoppingStore.service.purchase.shoppingCartItem.ShoppingCartItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private ValidationsDisabledShoppingCart validationDisabledShoppingCart;


    @Mock
    private ShoppingCartItemService shoppingCartItemService;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
    }

    @Test
    void testRegister() {
        DataRegisterPurchase data = mock(DataRegisterPurchase.class);

        DataRegisterShoppingCart dataRegisterShoppingCart = mock(DataRegisterShoppingCart.class);
        when(data.dataRegisterShoppingCart()).thenReturn(dataRegisterShoppingCart);
        when(dataRegisterShoppingCart.userId()).thenReturn(1L);

        User user = new User(); 
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    
        ShoppingCart savedShoppingCart = new ShoppingCart(user);
        when(shoppingCartRepository.save(any())).thenReturn(savedShoppingCart);

    
        DataDetailsShoppingCart result = shoppingCartService.register(data);

        assertNotNull(result);
        assertEquals(user.getId(), result.UserId());
        verify(shoppingCartRepository, times(1)).save(any());
    }



    @Test
    void testList() {
        Pageable pageable = mock(Pageable.class);
        Page<ShoppingCart> shoppingCarts = new PageImpl<>(Collections.emptyList());
        when(shoppingCartRepository.findAll(pageable)).thenReturn(shoppingCarts);

        Page<DataDetailsShoppingCart> result = shoppingCartService.list(pageable);

        assertNotNull(result);
        assertEquals(shoppingCarts, result);
    }

    @Test
    void testListDeactivated() {
        Pageable pageable = mock(Pageable.class);
        Page<ShoppingCart> deactivatedShoppingCarts = new PageImpl<>(Collections.emptyList());
        when(shoppingCartRepository.findAllByDisabledTrue(pageable)).thenReturn(deactivatedShoppingCarts);

        Page<DataDetailsShoppingCart> result = shoppingCartService.listDeactivated(pageable);

        assertNotNull(result);
        assertEquals(deactivatedShoppingCarts, result);
    }

    @Test
    void testDetails() {
        Long cartId = 1L;
        
        ShoppingCart shoppingCart = new ShoppingCart(new User());
        shoppingCart.setId(cartId);

        when(shoppingCartRepository.findById(cartId)).thenReturn(Optional.of(shoppingCart));

        DataDetailsShoppingCart result = shoppingCartService.details(cartId);

        assertNotNull(result);

        assertEquals(cartId, result.id());
    }

    
    @Test
    void testDisabled() {
        // Given
        Long shoppingCartId = 1L;
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(shoppingCartId);

        List<ValidationsDisabledShoppingCart> validations = Collections.singletonList(validationDisabledShoppingCart);

        when(shoppingCartRepository.findById(shoppingCartId)).thenReturn(Optional.of(shoppingCart));
        
        when(shoppingCartRepository.findAllByShoppingCartId(shoppingCartId)).thenReturn(Collections.emptyList());

        ShoppingCartService shoppingCartService = new ShoppingCartService(shoppingCartRepository, validations, shoppingCartItemService, userRepository);

        // When
        shoppingCartService.disabled(shoppingCartId);

        // Then
        assertTrue(shoppingCart.getDisabled());
        verify(shoppingCartItemService, times(0)).disabled(anyLong()); // Nenhum item desabilitado
        verify(shoppingCartRepository, times(1)).save(shoppingCart); // Carrinho de compras salvo após desabilitar
        verify(validationDisabledShoppingCart, times(1)).validation(shoppingCartId); // Validação chamada uma vez
    }
    
    @Test
    void testListByUser() {
        // Mock do ID do usuário
        Long userId = 1L;
        
        ShoppingCart shoppingCart = new ShoppingCart(new User());
        Page<ShoppingCart> shoppingCartPage = new PageImpl<>(Collections.singletonList(shoppingCart));
        when(shoppingCartRepository.findAllByUser(any(Pageable.class), eq(userId))).thenReturn(shoppingCartPage);
        
        Page<DataDetailsShoppingCart> result = shoppingCartService.listByUser(mock(Pageable.class), userId);
       
        assertNotNull(result);
        
        verify(shoppingCartRepository).findAllByUser(any(Pageable.class), eq(userId));
    }
    
    @Test
    void testListByUserAndDisabledTrue() {
        // Mock do ID do usuário
        Long userId = 1L;

        ShoppingCart shoppingCart = new ShoppingCart(new User());
        Page<ShoppingCart> shoppingCartPage = new PageImpl<>(Collections.singletonList(shoppingCart));
        when(shoppingCartRepository.findAllByUserAndDisabledIsTrue(any(Pageable.class), eq(userId))).thenReturn(shoppingCartPage);

        Page<DataDetailsShoppingCart> result = shoppingCartService.listByUserAndDisabledTrue(mock(Pageable.class), userId);
        

        assertNotNull(result);
        
        verify(shoppingCartRepository).findAllByUserAndDisabledIsTrue(any(Pageable.class), eq(userId));
    }
}
