package br.com.cepedi.ShoppingStore.repository;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @AfterEach
    public void deleteAllCategory() {
        categoryRepository.deleteAll();
    }


    // Test to verify if a category can be saved correctly in the database
    @Test
    public void testSaveCategory() {
        // Create a new category instance
        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test Description");

        // Save the category in the database and verify if the ID was generated
        Category savedCategory = categoryRepository.save(category);
        assertNotNull(savedCategory.getId());
    }

    // Test to verify if all categories can be retrieved correctly from the database
    @Test
    public void testFindAllCategories() {

        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test Description");

        // Save the category in the database and verify if the ID was generated
        Category savedCategory = categoryRepository.save(category);

        // Retrieve all categories from the database
        List<Category> categories = categoryRepository.findAll();

        // Verify if the number of retrieved categories matches the expected number
        assertEquals(1, categories.size()); 
    }

    // Add more tests as needed for other repository methods
}
