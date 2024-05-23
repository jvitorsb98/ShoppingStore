package br.com.cepedi.ShoppingStore.service.productRating.validation.register;

import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidationUserExistsForRegisterProductRatingTest {

    @InjectMocks
    private ValidationProductExists validator;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void testValidationWithExistingProduct() {
        long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);

        // Verifica se nenhuma exceção é lançada quando um produto existente é fornecido
        assertDoesNotThrow(() -> validator.validation(new DataRegisterProductRating(productId, null, null, null)));
    }

    @Test
    public void testValidationWithNonExistingProduct() {
        long productId = 2L;
        when(productRepository.existsById(productId)).thenReturn(false);

        // Verifica se uma exceção é lançada quando um produto inexistente é fornecido
        assertThrows(ValidationException.class, () -> validator.validation(new DataRegisterProductRating(productId, null, null, null)));
    }
    
    //failure
    @Test
    public void testValidationWithNullProductId() {
        // Verifica se uma exceção é lançada quando um ID de produto nulo é fornecido
        assertThrows(ValidationException.class, () -> validator.validation(new DataRegisterProductRating(null, null, null, null)));
    }
}
