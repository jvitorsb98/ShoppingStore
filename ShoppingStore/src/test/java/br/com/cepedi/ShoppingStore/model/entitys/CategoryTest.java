package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.model.records.category.input.DataRegisterCategory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.Random.class)
@DisplayName("Test entity Category")
@ExtendWith(SpringExtension.class)
class CategoryTest {

    @Test
    @DisplayName("Teste de criação básico")
    public void testCategoryCreation() {
        Category category = new Category();
        Assertions.assertNotNull(category);
    }

    @Test
    @DisplayName("Teste de igualdade")
    public void testCategoryEquality() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Eletrônicos");

        Category category2 = new Category();
        category2.setId(1L);
        category2.setName("Eletrônicos");

        assertEquals(category1, category2);
    }

    @Test
    @DisplayName("Teste de desigualdade")
    public void testCategoryInequality() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Eletrônicos");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Roupas");

        Assertions.assertNotEquals(category1, category2);
    }

    @Test
    @DisplayName("Test constructor and getters")
    void testConstructorAndGetters() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("Electronic devices and accessories");

        assertEquals(1L, category.getId());
        assertEquals("Electronics", category.getName());
        assertEquals("Electronic devices and accessories", category.getDescription());
    }

    @Test
    @DisplayName("Teste de hash code - igualdade")
    public void testCategoryHashCodeEquality() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Eletrônicos");

        Category category2 = new Category();
        category2.setId(1L);
        category2.setName("Eletrônicos");

        assertEquals(category1.hashCode(), category2.hashCode());
    }

    @Test
    @DisplayName("Teste de hash code - desigualdade")
    public void testCategoryHashCodeInequality() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Eletrônicos");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Roupas");

        Assertions.assertNotEquals(category1.hashCode(), category2.hashCode());
    }

    @Test
    @DisplayName("Teste do construtor com DataRegisterCategory")
    public void testConstructorWithDataRegisterCategory() {
        DataRegisterCategory data = new DataRegisterCategory("Livros", "Categoria de livros");
        Category category = new Category(data);

        assertEquals("Livros", category.getName());
        assertEquals("Categoria de livros", category.getDescription());
    }
}
