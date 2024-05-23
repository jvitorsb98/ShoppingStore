package br.com.cepedi.ShoppingStore.service.product.validations.register;

import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidationCategoryExistsTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ValidationCategoryExists validationCategoryExists;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidationWithExistingCategory() {
        // Setup
        long categoryId = 1L;
        DataRegisterProduct dataRegisterProduct = new DataRegisterProduct(
                "Product Name",
                "Product Description",
                BigDecimal.valueOf(100.00),
                "SKU123",
                "http://example.com/image.jpg",
                categoryId,
                BigInteger.valueOf(10),
                1L, // Using brandId directly
                true
        );

        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        // Validation
        assertDoesNotThrow(() -> {
            validationCategoryExists.validation(dataRegisterProduct);
        });
    }

    @Test
    void testValidationWithNonExistingCategory() {
        // Setup
        long categoryId = 2L;
        DataRegisterProduct dataRegisterProduct = new DataRegisterProduct(
                "Product Name",
                "Product Description",
                BigDecimal.valueOf(100.00),
                "SKU123",
                "http://example.com/image.jpg",
                categoryId,
                BigInteger.valueOf(10),
                1L, // Using brandId directly
                true
        );

        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        // Validation
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            validationCategoryExists.validation(dataRegisterProduct);
        });

        // Verify
        assertEquals("The provided category id does not exist", exception.getMessage());
    }


}
