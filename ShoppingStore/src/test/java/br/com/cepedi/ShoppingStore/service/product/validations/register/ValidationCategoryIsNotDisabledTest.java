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

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import jakarta.validation.ValidationException;

@ExtendWith(MockitoExtension.class)
public class ValidationCategoryIsNotDisabledTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ValidationCategoryIsNotDisabled validationCategoryIsNotDisabled;

    private Faker faker;
    private DataRegisterProduct dataRegisterProduct;
    private Category category;

    @BeforeEach
    public void setup() {
        faker = new Faker();
        dataRegisterProduct = mock(DataRegisterProduct.class);
        category = new Category();
    }

    @Test
    public void testValidationThrowsExceptionWhenCategoryIsDisabled() {
        // Arrange
        Long categoryId = faker.number().randomNumber();
        when(dataRegisterProduct.categoryId()).thenReturn(categoryId);
        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        category.setDisabled(true);
        when(categoryRepository.getReferenceById(categoryId)).thenReturn(category);

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            validationCategoryIsNotDisabled.validation(dataRegisterProduct);
        });

        assertEquals("The provided category id is disabled", exception.getMessage());
    }

    @Test
    public void testValidationDoesNotThrowExceptionWhenCategoryIsNotDisabled() {
        // Arrange
        Long categoryId = faker.number().randomNumber();
        when(dataRegisterProduct.categoryId()).thenReturn(categoryId);
        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        category.setDisabled(false);
        when(categoryRepository.getReferenceById(categoryId)).thenReturn(category);

        // Act & Assert
        assertDoesNotThrow(() -> {
            validationCategoryIsNotDisabled.validation(dataRegisterProduct);
        });
    }

    @Test
    public void testValidationDoesNotThrowExceptionWhenCategoryDoesNotExist() {
        // Arrange
        Long categoryId = faker.number().randomNumber();
        when(dataRegisterProduct.categoryId()).thenReturn(categoryId);
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> {
            validationCategoryIsNotDisabled.validation(dataRegisterProduct);
        });
    }
}

