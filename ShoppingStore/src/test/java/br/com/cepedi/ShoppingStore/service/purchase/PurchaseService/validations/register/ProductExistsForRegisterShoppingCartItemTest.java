package br.com.cepedi.ShoppingStore.service.purchase.PurchaseService.validations.register;

import br.com.cepedi.ShoppingStore.model.records.ShoppingCartItem.input.DataRegisterShoppingCartItem;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import br.com.cepedi.ShoppingStore.service.purchase.PurharseService.validations.register.shoppingCartItem.ProductExistsForRegisterShoppingCartItem;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductExistsForRegisterShoppingCartItemTest {

    @InjectMocks
    private ProductExistsForRegisterShoppingCartItem productExistsForRegisterShoppingCartItem;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidation_ProductExists() {
        // Dados de teste
        DataRegisterShoppingCartItem data = new DataRegisterShoppingCartItem(BigInteger.ONE, 1L);

        // Comportamento simulado do ProductRepository
        when(productRepository.existsById(1L)).thenReturn(true);

        // Execução do teste
        productExistsForRegisterShoppingCartItem.validation(data);

        // Verificação do comportamento
        verify(productRepository).existsById(1L);
    }

    @Test
    public void testValidation_ProductNotExists() {
        // Dados de teste
        DataRegisterShoppingCartItem data = new DataRegisterShoppingCartItem(BigInteger.ONE, 2L); // Simula um ID de produto que não existe

        // Comportamento simulado do ProductRepository
        when(productRepository.existsById(2L)).thenReturn(false); // Simula que o produto não existe

        // Execução do teste e verificação da exceção
        assertThrows(ValidationException.class, () -> productExistsForRegisterShoppingCartItem.validation(data));
    }
}
