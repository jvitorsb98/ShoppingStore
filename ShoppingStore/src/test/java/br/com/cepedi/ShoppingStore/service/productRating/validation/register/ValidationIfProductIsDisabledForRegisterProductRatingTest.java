package br.com.cepedi.ShoppingStore.service.productRating.validation.register;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValidationIfProductIsDisabledForRegisterProductRatingTest {

    @InjectMocks
    private ValidationIfProductIsDisabledForRegisterProductRating validator;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void testValidationWithEnabledProduct() {
        long productId = 1L;
        Product product = new Product();
        product.setDisabled(false);
        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.getReferenceById(productId)).thenReturn(product);

        assertDoesNotThrow(() -> validator.validation(new DataRegisterProductRating(productId, BigDecimal.ONE, "Valid review", 1L)));
    }

    @Test
    public void testValidationWithDisabledProduct() {
        long productId = 2L;
        Product product = new Product();
        product.setDisabled(true);
        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.getReferenceById(productId)).thenReturn(product);

        assertThrows(ValidationException.class, () -> validator.validation(new DataRegisterProductRating(productId, BigDecimal.ONE, "Valid review", 1L)));
    }

    @Test
    public void testValidationWithNonExistingProduct() {
        long productId = 3L;
        when(productRepository.existsById(productId)).thenReturn(false);

        assertDoesNotThrow(() -> validator.validation(new DataRegisterProductRating(productId, BigDecimal.ONE, "Valid review", 1L)));
    }
}
