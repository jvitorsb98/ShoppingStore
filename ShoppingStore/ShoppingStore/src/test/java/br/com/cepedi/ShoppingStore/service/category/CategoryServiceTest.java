package br.com.cepedi.ShoppingStore.service.category;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.records.category.details.DataCategoryDetails;
import br.com.cepedi.ShoppingStore.model.records.category.input.DataRegisterCategory;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;
import br.com.cepedi.ShoppingStore.service.category.CategoryService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    @Test
    public void testRegisterCategory() {
        // Given
        String categoryName = faker.commerce().department();
        String categoryDescription = faker.lorem().sentence();
        DataRegisterCategory testData = new DataRegisterCategory(categoryName, categoryDescription);

        Category mockCategory = new Category();
        long categoryId = faker.number().randomNumber();
        mockCategory.setId(categoryId);
        mockCategory.setName(categoryName); // Definindo o nome manualmente
        mockCategory.setDescription(categoryDescription); // Definindo a descrição manualmente
        when(categoryRepository.save(any(Category.class))).thenReturn(mockCategory);

        // When
        DataCategoryDetails result = categoryService.registerCategory(testData);

        // Then
        assertEquals(categoryId, result.id());
        assertEquals(categoryName, result.name());
        assertEquals(categoryDescription, result.description());
    }


    @Test
    public void testListAllCategories() {
        // Given
        List<Category> mockCategories = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Category category = new Category();
            category.setId((long) faker.number().randomNumber());
            category.setName(faker.commerce().department());
            category.setDescription(faker.lorem().sentence());
            mockCategories.add(category);
        }
        Page<Category> mockPage = new PageImpl<>(mockCategories);
        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        // When
        Page<DataCategoryDetails> resultPage = categoryService.listAllCategories(Pageable.unpaged());

        // Then
        assertEquals(mockPage.getTotalElements(), resultPage.getTotalElements());
        assertEquals(mockPage.getContent().size(), resultPage.getContent().size());
    }

    @Test
    public void testGetCategoryById_ExistingCategory() {
        // Given
        Long categoryId = faker.number().randomNumber();
        DataRegisterCategory testData = new DataRegisterCategory(faker.commerce().department(), faker.lorem().sentence());

        Category mockCategory = new Category();
        mockCategory.setId(categoryId);
        mockCategory.setName(testData.name());
        mockCategory.setDescription(testData.description());
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(mockCategory));

        // When
        DataCategoryDetails result = categoryService.getCategoryById(categoryId);

        // Then
        assertEquals(mockCategory.getId(), result.id());
        assertEquals(mockCategory.getName(), result.name());
        assertEquals(mockCategory.getDescription(), result.description());
    }


    @Test
    public void testGetCategoryById_NonExistingCategory() {
        // Given
        Long categoryId = faker.number().randomNumber();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> categoryService.getCategoryById(categoryId));
    }
}

