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

import br.com.cepedi.ShoppingStore.repository.BrandRepository;
import jakarta.validation.ValidationException;

@ExtendWith(MockitoExtension.class)
class ValidationBrandExistsForDisabledTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private ValidationBrandExistsForDisabled validation;

    private Faker faker;
    private long brandId;

    @BeforeEach
    void setup() {
        faker = new Faker();
        brandId = faker.number().randomNumber();
    }

    @Test
    void testValidation_BrandDoesNotExist() {
        // Arrange
        when(brandRepository.existsById(brandId)).thenReturn(false);

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            validation.validation(brandId);
        });

        assertEquals("The required branch does not exists", exception.getMessage());
    }

    @Test
    void testValidation_BrandExists() {
        // Arrange
        when(brandRepository.existsById(brandId)).thenReturn(true);

        // Act & Assert
        assertDoesNotThrow(() -> validation.validation(brandId));
    }
}
