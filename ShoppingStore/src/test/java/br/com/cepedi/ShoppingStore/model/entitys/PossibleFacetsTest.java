package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataRegisterPossibleFacets;
import com.github.javafaker.Faker;

import br.com.cepedi.ShoppingStore.repository.CategoryRepository;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.Random.class)
@DisplayName("Test entity possibleFacetsTest")
public class PossibleFacetsTest {

    @Test
    void testConstructorWithDataRegisterPossibleFacets() {
        // Mock data
        DataRegisterPossibleFacets data = new DataRegisterPossibleFacets("Test Facet");
        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");
        category.setDescription("Test Description");

        // Create PossibleFacets instance
        PossibleFacets possibleFacets = new PossibleFacets(data, category);

        // Assertions
        assertNotNull(possibleFacets);
        assertEquals("Test Facet", possibleFacets.getName());
        assertEquals(category, possibleFacets.getCategory());
    }

    @Test
    void testNoArgsConstructor() {
        // Create PossibleFacets instance using no-args constructor
        PossibleFacets possibleFacets = new PossibleFacets();

        // Assertions
        assertNotNull(possibleFacets);
        assertNull(possibleFacets.getName());
        assertNull(possibleFacets.getCategory());
    }

    @Test
    void testSettersAndGetters() {
        // Create PossibleFacets instance
        PossibleFacets possibleFacets = new PossibleFacets();

        // Create Category instance
        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");
        category.setDescription("Test Description");

        // Set values
        possibleFacets.setName("Test Facet");
        possibleFacets.setCategory(category);

        // Assertions
        assertEquals("Test Facet", possibleFacets.getName());
        assertEquals(category, possibleFacets.getCategory());
    }
}

