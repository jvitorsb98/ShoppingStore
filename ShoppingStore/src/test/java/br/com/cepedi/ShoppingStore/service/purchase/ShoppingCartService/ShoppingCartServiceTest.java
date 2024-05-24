package br.com.cepedi.ShoppingStore.service.purchase.ShoppingCartService;

import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input.DataRegisterShoppingCartItem;
import br.com.cepedi.ShoppingStore.model.records.purchase.register.DataRegisterPurchase;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.details.DataDetailsShoppingCart;
import br.com.cepedi.ShoppingStore.model.records.shoppingCart.input.DataRegisterShoppingCart;
import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import br.com.cepedi.ShoppingStore.security.model.records.input.DataRegisterUser;
import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
import br.com.cepedi.ShoppingStore.service.purchase.shoppingCart.ShoppingCartService;
import br.com.cepedi.ShoppingStore.service.purchase.shoppingCart.validations.disabled.ValidationsDisabledShoppingCart;
import br.com.cepedi.ShoppingStore.service.purchase.shoppingCartItem.ShoppingCartItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTest {

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private List<ValidationsDisabledShoppingCart> validationsDisabledShoppingCarts;

    @Mock
    private ShoppingCartItemService shoppingCartItemService;

    @Mock
    private UserRepository userRepository;

    private User user;
    private ShoppingCart shoppingCart;
    private Pageable pageable;

    @BeforeEach
    public void setUp() {
        // Mock data
        DataRegisterUser dataRegisterUser = new DataRegisterUser("login", "email", "name", "password");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user = new User(dataRegisterUser, passwordEncoder);
        user.setId(1L);

        shoppingCart = new ShoppingCart(user);

        pageable = mock(Pageable.class);
    }

    @Test
    public void testRegister() {
        // Mock repository behavior
        when(userRepository.getReferenceById(anyLong())).thenReturn(user);
        when(shoppingCartRepository.save(any())).thenReturn(shoppingCart);

        // Mock data
        DataRegisterShoppingCart dataRegisterShoppingCart = new DataRegisterShoppingCart(1L);
        DataRegisterShoppingCartItem shoppingCartItem = new DataRegisterShoppingCartItem(BigInteger.ONE, 1L);
        DataRegisterShoppingCartItem shoppingCartItem2 = new DataRegisterShoppingCartItem(BigInteger.ONE, 2L);
        List<DataRegisterShoppingCartItem> items = List.of(shoppingCartItem, shoppingCartItem2);

        DataRegisterPurchase data = new DataRegisterPurchase(dataRegisterShoppingCart, items);

        // Call the method
        DataDetailsShoppingCart result = shoppingCartService.register(data);

        // Verify the result
        assertEquals(new DataDetailsShoppingCart(shoppingCart), result);

        // Verify interactions
        verify(userRepository).getReferenceById(anyLong());
        verify(shoppingCartRepository).save(any());
    }

    @Test
    public void testList() {
        // Mock repository behavior
        when(shoppingCartRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(shoppingCart)));

        // Call the method
        Page<DataDetailsShoppingCart> result = shoppingCartService.list(pageable);

        // Verify the result
        assertEquals(1, result.getContent().size());
        assertEquals(new DataDetailsShoppingCart(shoppingCart), result.getContent().get(0));

        // Verify interactions
        verify(shoppingCartRepository).findAll(pageable);
    }

    @Test
    public void testListDeactivated() {
        // Mock repository behavior
        shoppingCart.disable();
        when(shoppingCartRepository.findAllByDisabledTrue(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(shoppingCart)));

        // Call the method
        Page<DataDetailsShoppingCart> result = shoppingCartService.listDeactivated(pageable);

        // Verify the result
        assertEquals(1, result.getContent().size());
        assertEquals(new DataDetailsShoppingCart(shoppingCart), result.getContent().get(0));

        // Verify interactions
        verify(shoppingCartRepository).findAllByDisabledTrue(pageable);
    }

    @Test
    public void testDetails() {
        // Mock data
        Long id = 1L;

        // Mock repository behavior
        when(shoppingCartRepository.getReferenceById(id)).thenReturn(shoppingCart);

        // Call the method
        DataDetailsShoppingCart result = shoppingCartService.details(id);

        // Verify the result
        assertEquals(new DataDetailsShoppingCart(shoppingCart), result);

        // Verify interactions
        verify(shoppingCartRepository).getReferenceById(id);
    }

    @Test
    public void testListByUser() {
        // Mock data
        Long userId = 1L;

        // Mock repository behavior
        when(shoppingCartRepository.findAllByUser(pageable, userId)).thenReturn(new PageImpl<>(Collections.singletonList(shoppingCart)));

        // Call the method
        Page<DataDetailsShoppingCart> result = shoppingCartService.listByUser(pageable, userId);

        // Verify the result
        assertEquals(1, result.getContent().size());
        assertEquals(new DataDetailsShoppingCart(shoppingCart), result.getContent().get(0));

        // Verify interactions
        verify(shoppingCartRepository).findAllByUser(pageable, userId);
    }

    @Test
    public void testListByUserAndDisabledTrue() {
        // Mock data
        Long userId = 1L;

        // Mock repository behavior
        shoppingCart.disable();
        when(shoppingCartRepository.findAllByUserAndDisabledIsTrue(pageable, userId)).thenReturn(new PageImpl<>(Collections.singletonList(shoppingCart)));

        // Call the method
        Page<DataDetailsShoppingCart> result = shoppingCartService.listByUserAndDisabledTrue(pageable, userId);

        // Verify the result
        assertEquals(1, result.getContent().size());
        assertEquals(new DataDetailsShoppingCart(shoppingCart), result.getContent().get(0));

        // Verify interactions
        verify(shoppingCartRepository).findAllByUserAndDisabledIsTrue(pageable, userId);
    }

    @Test
    public void testDisabled() {
        // Mock data
        ShoppingCart shoppingCart = mock(ShoppingCart.class);
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setId(1L); // Definindo um ID válido para o item do carrinho de compras
        List<ShoppingCartItem> shoppingCartItems = List.of(shoppingCartItem);

        // Mock repository behavior
        when(shoppingCartRepository.getReferenceById(anyLong())).thenReturn(shoppingCart);
        when(shoppingCartRepository.findAllByShoppingCartId(anyLong())).thenReturn(shoppingCartItems);

        // Call the method
        shoppingCartService.disabled(1L);

        // Verify interactions
        verify(validationsDisabledShoppingCarts).forEach(any());
        verify(shoppingCart).disable();
        verify(shoppingCartRepository).findAllByShoppingCartId(anyLong());
        verify(shoppingCartItemService).disabled(1L); // Verifica se o método disabled é chamado com o ID correto
    }
}