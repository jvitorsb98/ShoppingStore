package br.com.cepedi.ShoppingStore.service.brand.validations.disabled;

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
import br.com.cepedi.ShoppingStore.repository.BrandRepository;
import jakarta.validation.ValidationException;

@ExtendWith(MockitoExtension.class)
class ValidationBrandAlreadyDisabledForDisabledTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private ValidationBrandAlreadyDisabledForDisabled validation;

    private Faker faker;
    private long brandId;
    private Brand brand;

    @BeforeEach
    void setup() {
        faker = new Faker();
        brandId = faker.number().randomNumber();
        brand = new Brand();
        brand.setId(brandId);
    }

    @Test
    void testValidation_BrandIsDisabled() {
        // Arrange
        brand.setDisabled(true); // Marca desativada
        when(brandRepository.existsById(brandId)).thenReturn(true);
        when(brandRepository.getReferenceById(brandId)).thenReturn(brand);

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            validation.validation(brandId);
        });

        assertEquals("The brand is already disabled", exception.getMessage());
    }

    @Test
    void testValidation_BrandIsNotDisabled() {
        // Arrange
        brand.setDisabled(false); // Marca não desativada
        when(brandRepository.existsById(brandId)).thenReturn(true);
        when(brandRepository.getReferenceById(brandId)).thenReturn(brand);

        // Act & Assert
        assertDoesNotThrow(() -> validation.validation(brandId));
    }

    @Test
    void testValidation_BrandDoesNotExist() {
        // Arrange
        when(brandRepository.existsById(brandId)).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> validation.validation(brandId));
    }
}
