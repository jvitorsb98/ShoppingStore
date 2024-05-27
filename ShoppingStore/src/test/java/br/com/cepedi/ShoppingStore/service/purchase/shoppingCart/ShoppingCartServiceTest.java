package br.com.cepedi.ShoppingStore.service.purchase.shoppingCart;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.model.records.purchase.register.DataRegisterPurchase;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.details.DataDetailsShoppingCart;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.input.DataRegisterShoppingCart;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import br.com.cepedi.ShoppingStore.service.purchase.shoppingCartItem.ShoppingCartItemService;
import br.com.cepedi.ShoppingStore.service.purchase.shoppingCart.validations.disabled.ValidationsDisabledShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.Random.class)
class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private List<ValidationsDisabledShoppingCart> validationDisabledShoppingCart;

    @Mock
    private ShoppingCartItemService shoppingCartItemService;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        // Given
        DataRegisterPurchase data = mock(DataRegisterPurchase.class);
        DataDetailsShoppingCart expected = mock(DataDetailsShoppingCart.class);
        User user = new User();
        user.setId(1L);
        when(data.dataRegisterShoppingCart()).thenReturn(mock(DataRegisterShoppingCart.class));
        when(data.dataRegisterShoppingCart().userId()).thenReturn(1L);
        when(userRepository.getReferenceById(1L)).thenReturn(user);
        when(shoppingCartRepository.save(any())).thenReturn(new ShoppingCart(user));
        when(expected.UserId()).thenReturn(user.getId());

        // When
        DataDetailsShoppingCart result = shoppingCartService.register(data);

        // Then
        assertNotNull(result);
        assertEquals(expected.UserId(), result.UserId());
        verify(shoppingCartRepository, times(1)).save(any());
    }

    @Test
    void testList() {
        // Given
        Pageable pageable = mock(Pageable.class);
        Page<ShoppingCart> shoppingCarts = new PageImpl<>(Collections.emptyList());
        when(shoppingCartRepository.findAll(pageable)).thenReturn(shoppingCarts);

        // When
        Page<DataDetailsShoppingCart> result = shoppingCartService.list(pageable);

        // Then
        assertNotNull(result);
        assertEquals(shoppingCarts, result);
    }

    @Test
    void testListDeactivated() {
        // Given
        Pageable pageable = mock(Pageable.class);
        Page<ShoppingCart> deactivatedShoppingCarts = new PageImpl<>(Collections.emptyList());
        when(shoppingCartRepository.findAllByDisabledTrue(pageable)).thenReturn(deactivatedShoppingCarts);

        // When
        Page<DataDetailsShoppingCart> result = shoppingCartService.listDeactivated(pageable);

        // Then
        assertNotNull(result);
        assertEquals(deactivatedShoppingCarts, result);
    }

    @Test
    void testDetails() {
        // Given
        Long cartId = 1L;
        ShoppingCart shoppingCart = new ShoppingCart(new User());
        shoppingCart.setId(cartId);
        when(shoppingCartRepository.getReferenceById(cartId)).thenReturn(shoppingCart);
        DataDetailsShoppingCart expected = new DataDetailsShoppingCart(shoppingCart);

        // When
        DataDetailsShoppingCart result = shoppingCartService.details(cartId);

        // Then
        assertNotNull(result);
        assertEquals(expected.id(), result.id());
    }

    @Test
    void testListByUser() {
        // Given
        Long userId = 1L;
        Pageable pageable = mock(Pageable.class);
        ShoppingCart shoppingCart = new ShoppingCart(new User());
        Page<ShoppingCart> shoppingCartPage = new PageImpl<>(Collections.singletonList(shoppingCart));
        when(shoppingCartRepository.findAllByUser(pageable, userId)).thenReturn(shoppingCartPage);
        Page<DataDetailsShoppingCart> expected = shoppingCartPage.map(DataDetailsShoppingCart::new);

        // When
        Page<DataDetailsShoppingCart> result = shoppingCartService.listByUser(pageable, userId);

        // Then
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void testListByUserAndDisabledTrue() {
        // Given
        Long userId = 1L;
        Pageable pageable = mock(Pageable.class);
        ShoppingCart shoppingCart = new ShoppingCart(new User());
        Page<ShoppingCart> shoppingCartPage = new PageImpl<>(Collections.singletonList(shoppingCart));
        when(shoppingCartRepository.findAllByUserAndDisabledIsTrue(pageable, userId)).thenReturn(shoppingCartPage);
        Page<DataDetailsShoppingCart> expected = shoppingCartPage.map(DataDetailsShoppingCart::new);

        // When
        Page<DataDetailsShoppingCart> result = shoppingCartService.listByUserAndDisabledTrue(pageable, userId);

        // Then
        assertNotNull(result);
        assertEquals(expected, result);
        assertEquals(expected, result);
    }

    @Test
    void testDisabled() {
        // Given
        Long shoppingCartId = 1L;
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(shoppingCartId);
        when(shoppingCartRepository.getReferenceById(shoppingCartId)).thenReturn(shoppingCart);
        when(shoppingCartRepository.findAllByShoppingCartId(shoppingCartId)).thenReturn(Collections.emptyList());

        // When
        shoppingCartService.disabled(shoppingCartId);

        // Then
        assertTrue(shoppingCart.getDisabled());
        verify(shoppingCartItemService, times(0)).disabled(anyLong());
        verify(shoppingCartRepository, times(1)).save(shoppingCart);
    }

}
