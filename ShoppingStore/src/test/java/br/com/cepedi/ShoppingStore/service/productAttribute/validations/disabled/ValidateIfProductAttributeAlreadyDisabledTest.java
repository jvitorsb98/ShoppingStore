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
public class ValidateIfProductAttributeAlreadyDisabledTest {

    @Mock
    private ProductAttributeRepository productAttributeRepository;

    @InjectMocks
    private ValidateIfProductAttributeAlreadyDisabled validateIfProductAttributeAlreadyDisabled;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testValidation_ProductAttributeAlreadyDisabled() {
        Long productId = 1L;
        when(productAttributeRepository.findDisabledById(productId)).thenReturn(true);
        
        assertThrows(ValidationException.class, () -> {
            validateIfProductAttributeAlreadyDisabled.validation(productId);
        });
    }

    @Test
    public void testValidation_ProductAttributeNotDisabled() {
        Long productId = 2L;

        when(productAttributeRepository.findDisabledById(productId)).thenReturn(false);

        validateIfProductAttributeAlreadyDisabled.validation(productId);
    }
}

