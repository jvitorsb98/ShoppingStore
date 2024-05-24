package br.com.cepedi.ShoppingStore.service.productAttribute.validations.disabled;

import br.com.cepedi.ShoppingStore.repository.ProductAttributeRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidateIfProductAttributeExistDisabledTest {

    @Mock
    private ProductAttributeRepository productAttributeRepository;

    @InjectMocks
    private ValidateIfProductAttributeExistDisabled validateIfProductAttributeExistDisabled;

    @BeforeEach
    public void setUp() {
        // Setup any common test data or configurations here
    }

    @Test
    public void testValidation_ProductAttributeDoesNotExist() {
        Long productId = 1L;

        // Mock the repository method to return false (attribute does not exist)
        when(productAttributeRepository.existsById(productId)).thenReturn(false);

        // Assert that a ValidationException is thrown
        assertThrows(ValidationException.class, () -> {
            validateIfProductAttributeExistDisabled.validation(productId);
        });
    }

    @Test
    public void testValidation_ProductAttributeExists() {
        Long productId = 2L;

        // Mock the repository method to return true (attribute exists)
        when(productAttributeRepository.existsById(productId)).thenReturn(true);

        // Assert that no exception is thrown
        validateIfProductAttributeExistDisabled.validation(productId);
    }
}

