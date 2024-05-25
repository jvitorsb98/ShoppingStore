package br.com.cepedi.ShoppingStore.service.brand.validations.update;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;

import br.com.cepedi.ShoppingStore.model.records.brand.input.DataUpdateBrand;
import br.com.cepedi.ShoppingStore.repository.BrandRepository;
import jakarta.validation.ValidationException;

@ExtendWith(MockitoExtension.class)
class ValidationBrandExistsForUpdateTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private ValidationBrandExistsForUpdate validation;

    private Faker faker;
    private long brandId;
    private DataUpdateBrand dataUpdateBrand;

    @BeforeEach
    void setup() {
        faker = new Faker();
        brandId = faker.number().randomNumber();
        dataUpdateBrand = new DataUpdateBrand(brandId, faker.company().name());
    }

    @Test
    void testValidation_BrandDoesNotExist() {
        // Arrange
        when(brandRepository.existsById(brandId)).thenReturn(false);

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            validation.validation(dataUpdateBrand);
        });

        assertEquals("The required branch does not exists", exception.getMessage());
    }

    @Test
    void testValidation_BrandExists() {
        // Arrange
        when(brandRepository.existsById(brandId)).thenReturn(true);

        // Act & Assert
        assertDoesNotThrow(() -> validation.validation(dataUpdateBrand));
    }
}
