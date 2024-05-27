package br.com.cepedi.ShoppingStore.service.product.validations.cancel;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;

import br.com.cepedi.ShoppingStore.model.entitys.Product;
import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;

@ExtendWith(MockitoExtension.class)
public class ValidationProductAlreadyDisabledForDisabledTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ValidationProductAlreadyDisabledForDisabled validationProductAlreadyDisabledForDisabled;

    private Faker faker;
    private Product product;

    @BeforeEach
    public void setup() {
        faker = new Faker();
        product = new Product();
    }

    @Test
    public void testValidationThrowsExceptionWhenProductIsDisabled() {
        // Arrange
        Long productId = faker.number().randomNumber();
        when(productRepository.existsById(productId)).thenReturn(true);
        product.setDisabled(true);
        when(productRepository.getReferenceById(productId)).thenReturn(product);

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            validationProductAlreadyDisabledForDisabled.validation(productId);
        });

        assertEquals("The required product already disabled", exception.getMessage());
    }

    @Test
    public void testValidationDoesNotThrowExceptionWhenProductIsNotDisabled() {
        // Arrange
        Long productId = faker.number().randomNumber();
        when(productRepository.existsById(productId)).thenReturn(true);
        product.setDisabled(false);
        when(productRepository.getReferenceById(productId)).thenReturn(product);

        // Act & Assert
        assertDoesNotThrow(() -> {
            validationProductAlreadyDisabledForDisabled.validation(productId);
        });
    }

    @Test
    public void testValidationDoesNotThrowExceptionWhenProductDoesNotExist() {
        // Arrange
        Long productId = faker.number().randomNumber();
        when(productRepository.existsById(productId)).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> {
            validationProductAlreadyDisabledForDisabled.validation(productId);
        });
    }
}
