package br.com.cepedi.ShoppingStore.model.entitys;



import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.repository.CategoryRepository;

@TestMethodOrder(MethodOrderer.Random.class)
@DisplayName("Test entity Category")
@ExtendWith(SpringExtension.class)
@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;
    
    @DisplayName(" Test save category")
    @Test
    void testSaveCategory() {
        // Arrange
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("All kinds of electronic items");

        // Act
        Category savedCategory = categoryRepository.save(category);

        // Assert
        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getId()).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo("Electronics");
    }
    
    @DisplayName(" Test find category by id")
    @Test
    void testFindById() {
        // Arrange
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("All kinds of electronic items");
        category = categoryRepository.save(category);

        // Act
        Optional<Category> foundCategory = categoryRepository.findById(category.getId());

        // Assert
        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get().getName()).isEqualTo("Electronics");
    }
    
	@DisplayName(" Test find all categories")
    @Test
    void testFindAll() {
        // Arrange
        Category category1 = new Category();
        category1.setName("Electronics");
        category1.setDescription("All kinds of electronic items");

        Category category2 = new Category();
        category2.setName("Books");
        category2.setDescription("Various genres of books");

        categoryRepository.save(category1);
        categoryRepository.save(category2);

        // Act
        Iterable<Category> categories = categoryRepository.findAll();

        // Assert
        assertThat(categories).hasSize(2);
    }
	
	@DisplayName(" Test delete category")
    @Test
    void testDeleteCategory() {
        // Arrange
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("All kinds of electronic items");
        category = categoryRepository.save(category);

        // Act
        categoryRepository.delete(category);
        Optional<Category> deletedCategory = categoryRepository.findById(category.getId());

        // Assert
        assertThat(deletedCategory).isNotPresent();
    }
}
