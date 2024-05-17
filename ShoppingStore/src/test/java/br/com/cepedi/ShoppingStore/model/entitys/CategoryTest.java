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

        Assertions.assertEquals(category1, category2);
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
}


