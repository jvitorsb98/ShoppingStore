//package br.com.cepedi.ShoppingStore.service.purchase.ShoppingCartItemService;
//
//import br.com.cepedi.ShoppingStore.model.entitys.Product;
//import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCart;
//import br.com.cepedi.ShoppingStore.model.entitys.ShoppingCartItem;
//import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input.DataRegisterShoppingCartItem;
//import br.com.cepedi.ShoppingStore.model.records.purchase.details.DataDetailsPurchase;
//import br.com.cepedi.ShoppingStore.model.records.shoppingCart.details.DataDetailsShoppingCart;
//import br.com.cepedi.ShoppingStore.repository.ProductRepository;
//import br.com.cepedi.ShoppingStore.repository.ShoppingCartItemRepository;
//import br.com.cepedi.ShoppingStore.repository.ShoppingCartRepository;
//import br.com.cepedi.ShoppingStore.security.model.entitys.User;
//import br.com.cepedi.ShoppingStore.security.repository.UserRepository;
//import br.com.cepedi.ShoppingStore.service.purchase.shoppingCartItem.ShoppingCartItemService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ShoppingCartItemServiceTest {
//
//    @InjectMocks
//    private ShoppingCartItemService shoppingCartItemService;
//
//    @Mock
//    private ShoppingCartRepository shoppingCartRepository;
//
//    @Mock
//    private ShoppingCartItemRepository shoppingCartItemRepository;
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    private ShoppingCart shoppingCart;
//    private List<DataRegisterShoppingCartItem> items;
//
//    @BeforeEach
//    public void setUp() {
//        shoppingCart = new ShoppingCart();
//        shoppingCart.setUser(new User());
//        items = new ArrayList<>();
//
//        // Inicializando os mocks
//        shoppingCartRepository = mock(ShoppingCartRepository.class);
//        shoppingCartItemRepository = mock(ShoppingCartItemRepository.class);
//        productRepository = mock(ProductRepository.class);
//        userRepository = mock(UserRepository.class);
//    }
//
//    @Test
//    public void testRegisterItems() {
//        // Mock ShoppingCart
//        ShoppingCart shoppingCartMock = mock(ShoppingCart.class);
//
//        // Configurar comportamento do mock
//        when(shoppingCartRepository.getReferenceById(anyLong())).thenReturn(shoppingCartMock);
//
//        // Mock do objeto Product
//        Product productMock = mock(Product.class);
//        // Configurar comportamento do mock
//        when(productMock.getPrice()).thenReturn(BigDecimal.TEN);
//        when(productMock.getQuantity()).thenReturn(BigInteger.TEN);
//        // Configurar comportamento do mock do repository
//        when(productRepository.getReferenceById(anyLong())).thenReturn(productMock);
//
//        DataRegisterShoppingCartItem shoppingCartItemData = new DataRegisterShoppingCartItem(BigInteger.ONE, 1L);
//        items.add(shoppingCartItemData);
//
//        // Mock UserRepository
//        User userMock = mock(User.class);
//        when(userRepository.getReferenceById(anyLong())).thenReturn(userMock);
//
//        // Mock DataDetailsShoppingCart
//        DataDetailsShoppingCart dataDetailsShoppingCart = new DataDetailsShoppingCart(shoppingCartMock);
//
//        // Call the method
//        DataDetailsPurchase result = shoppingCartItemService.registerItems(items, dataDetailsShoppingCart);
//
//        // Verify the result
//        assertEquals(1, result.detailsShoppingCartItems().size());
//    }
//
//    @Test
//    public void testDisabled() {
//        // Mock data
//        ShoppingCartItem shoppingCartItemMock = mock(ShoppingCartItem.class);
//        Product productMock = mock(Product.class);
//        when(productMock.getQuantity()).thenReturn(BigInteger.TEN);
//
//        when(shoppingCartItemRepository.getReferenceById(anyLong())).thenReturn(shoppingCartItemMock);
//        when(productRepository.getReferenceById(anyLong())).thenReturn(productMock);
//
//        // Call the method
//        shoppingCartItemService.disabled(1L);
//    }
//
//}
