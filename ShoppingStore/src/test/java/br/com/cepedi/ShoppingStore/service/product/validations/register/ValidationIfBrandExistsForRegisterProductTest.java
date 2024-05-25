package br.com.cepedi.ShoppingStore.service.product.validations.register;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;

import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import br.com.cepedi.ShoppingStore.repository.BrandRepository;
import jakarta.validation.ValidationException;

@ExtendWith(MockitoExtension.class)
public class ValidationIfBrandExistsForRegisterProductTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private ValidationIfBrandExistsForRegisterProduct validationIfBrandExistsForRegisterProduct;

    private Faker faker;
    private DataRegisterProduct dataRegisterProduct;

    @BeforeEach
    public void setup() {
        faker = new Faker();
        dataRegisterProduct = mock(DataRegisterProduct.class);
    }

    @Test
    public void testValidationThrowsExceptionWhenBrandDoesNotExist() {
        // Arrange
        Long brandId = faker.number().randomNumber();
        when(dataRegisterProduct.brandId()).thenReturn(brandId);
        when(brandRepository.existsById(brandId)).thenReturn(false);

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            validationIfBrandExistsForRegisterProduct.validation(dataRegisterProduct);
        });

        assertEquals("The provided brand id does not exist", exception.getMessage());
    }

    @Test
    public void testValidationDoesNotThrowExceptionWhenBrandExists() {
        // Arrange
        Long brandId = faker.number().randomNumber();
        when(dataRegisterProduct.brandId()).thenReturn(brandId);
        when(brandRepository.existsById(brandId)).thenReturn(true);

        // Act & Assert
        assertDoesNotThrow(() -> {
            validationIfBrandExistsForRegisterProduct.validation(dataRegisterProduct);
        });
    }
}
