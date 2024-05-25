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

import br.com.cepedi.ShoppingStore.repository.ProductRepository;
import jakarta.validation.ValidationException;

@ExtendWith(MockitoExtension.class)
public class ValidationProductExistsForDisabledTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ValidationProductExistsForDisabled validationProductExistsForDisabled;

    private Faker faker;

    @BeforeEach
    public void setup() {
        faker = new Faker();
    }

    @Test
    public void testValidationThrowsExceptionWhenProductDoesNotExist() {
        // Arrange
        Long productId = faker.number().randomNumber();
        when(productRepository.existsById(productId)).thenReturn(false);

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            validationProductExistsForDisabled.validation(productId);
        });

        assertEquals("The required product not exists", exception.getMessage());
    }

    @Test
    public void testValidationDoesNotThrowExceptionWhenProductExists() {
        // Arrange
        Long productId = faker.number().randomNumber();
        when(productRepository.existsById(productId)).thenReturn(true);

        // Act & Assert
        assertDoesNotThrow(() -> {
            validationProductExistsForDisabled.validation(productId);
        });
    }
}
