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

import br.com.cepedi.ShoppingStore.model.entitys.Brand;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import br.com.cepedi.ShoppingStore.repository.BrandRepository;
import jakarta.validation.ValidationException;

@ExtendWith(MockitoExtension.class)
public class ValidationIfBrandIsNotDisabledForRegisterTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private ValidationIfBrandIsNotDisabledForRegister validationIfBrandIsNotDisabledForRegister;

    private Faker faker;
    private DataRegisterProduct dataRegisterProduct;
    private Brand brand;

    @BeforeEach
    public void setup() {
        faker = new Faker();
        dataRegisterProduct = mock(DataRegisterProduct.class);
        brand = new Brand();
    }

    @Test
    public void testValidationThrowsExceptionWhenBrandIsDisabled() {
        // Arrange
        Long brandId = faker.number().randomNumber();
        when(dataRegisterProduct.brandId()).thenReturn(brandId);
        when(brandRepository.existsById(brandId)).thenReturn(true);
        brand.setDisabled(true);
        when(brandRepository.getReferenceById(brandId)).thenReturn(brand);

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            validationIfBrandIsNotDisabledForRegister.validation(dataRegisterProduct);
        });

        assertEquals("The provided brand id is disabled", exception.getMessage());
    }

    @Test
    public void testValidationDoesNotThrowExceptionWhenBrandIsNotDisabled() {
        // Arrange
        Long brandId = faker.number().randomNumber();
        when(dataRegisterProduct.brandId()).thenReturn(brandId);
        when(brandRepository.existsById(brandId)).thenReturn(true);
        brand.setDisabled(false);
        when(brandRepository.getReferenceById(brandId)).thenReturn(brand);

        // Act & Assert
        assertDoesNotThrow(() -> {
            validationIfBrandIsNotDisabledForRegister.validation(dataRegisterProduct);
        });
    }

    @Test
    public void testValidationDoesNotThrowExceptionWhenBrandDoesNotExist() {
        // Arrange
        Long brandId = faker.number().randomNumber();
        when(dataRegisterProduct.brandId()).thenReturn(brandId);
        when(brandRepository.existsById(brandId)).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> {
            validationIfBrandIsNotDisabledForRegister.validation(dataRegisterProduct);
        });
    }
}
