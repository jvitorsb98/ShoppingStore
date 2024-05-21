package br.com.cepedi.ShoppingStore.model.entitys;

import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataRegisterPossibleFacets;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataUpdatePossibleFacets;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.Random.class)
@DisplayName("Test entity PossibleFacets")
public class PossibleFacetsTest {

    private Category mockCategory;
    private DataRegisterPossibleFacets mockDataRegister;
    private PossibleFacets possibleFacets;

    @BeforeEach
    void setUp() {
        // Initialize the mock data
        mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName("Test Category");
        mockCategory.setDescription("Test Description");

        mockDataRegister = new DataRegisterPossibleFacets("Test Facet", 1L);

        // Initialize PossibleFacets instance with mock data
        possibleFacets = new PossibleFacets(mockDataRegister, mockCategory);
    }

    @Test
    @DisplayName("Test constructor with DataRegisterPossibleFacets")
    void testConstructorWithDataRegisterPossibleFacets() {
        // Assertions
        assertNotNull(possibleFacets);
        assertEquals("Test Facet", possibleFacets.getName());
        assertEquals(mockCategory, possibleFacets.getCategory());
        assertFalse(possibleFacets.getDisabled());
    }

    @Test
    @DisplayName("Test no-args constructor")
    void testNoArgsConstructor() {
        // Create PossibleFacets instance using no-args constructor
        PossibleFacets possibleFacetsNoArgs = new PossibleFacets();

        // Assertions
        assertNotNull(possibleFacetsNoArgs);
        assertNull(possibleFacetsNoArgs.getName());
        assertNull(possibleFacetsNoArgs.getCategory());
        assertNull(possibleFacetsNoArgs.getDisabled());
    }

    @Test
    @DisplayName("Test setters and getters")
    void testSettersAndGetters() {
        // Create PossibleFacets instance
        PossibleFacets possibleFacetsSetters = new PossibleFacets();

        // Set values
        possibleFacetsSetters.setName("Updated Facet");
        possibleFacetsSetters.setCategory(mockCategory);
        possibleFacetsSetters.setDisabled(true);

        // Assertions
        assertEquals("Updated Facet", possibleFacetsSetters.getName());
        assertEquals(mockCategory, possibleFacetsSetters.getCategory());
        assertTrue(possibleFacetsSetters.getDisabled());
    }

    @Test
    @DisplayName("Test update data PossibleFacets")
    void testUpdateDataPossibleFacets() {
        // Create DataUpdatePossibleFacets instance
        DataUpdatePossibleFacets updateData = new DataUpdatePossibleFacets(1L, "Updated Facet", 1L);

        // Update PossibleFacets instance
        possibleFacets.updateDataPossibleFacets(updateData, mockCategory);

        // Assertions
        assertEquals("Updated Facet", possibleFacets.getName());
        assertEquals(mockCategory, possibleFacets.getCategory());
    }

    @Test
    @DisplayName("Test disable and enable methods")
    void testDisableAndEnable() {
        // Disable the PossibleFacets instance
        possibleFacets.disable();
        assertTrue(possibleFacets.getDisabled());

        // Enable the PossibleFacets instance
        possibleFacets.enable();
        assertFalse(possibleFacets.getDisabled());
    }
}
