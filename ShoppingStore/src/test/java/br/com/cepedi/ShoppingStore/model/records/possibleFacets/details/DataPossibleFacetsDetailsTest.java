package br.com.cepedi.ShoppingStore.model.records.possibleFacets.details;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import br.com.cepedi.ShoppingStore.model.entitys.Category;
import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;

public class DataPossibleFacetsDetailsTest {

    @Test
    public void testDataPossibleFacetsDetailsConstructor() {
        // Mocking PossibleFacets
        PossibleFacets possibleFacets = mock(PossibleFacets.class);
        when(possibleFacets.getId()).thenReturn(1L);
        when(possibleFacets.getName()).thenReturn("Test Possible Facet");
        
        // Mocking Category
        Category category = mock(Category.class);
        when(category.getId()).thenReturn(10L);
        when(possibleFacets.getCategory()).thenReturn(category);
        
        // Creating DataPossibleFacetsDetails object using constructor
        DataPossibleFacetsDetails dataPossibleFacetsDetails = new DataPossibleFacetsDetails(possibleFacets);
        
        // Assertions
        assertEquals(1L, dataPossibleFacetsDetails.id());
        assertEquals("Test Possible Facet", dataPossibleFacetsDetails.name());
        assertEquals(10L, dataPossibleFacetsDetails.idCategory());
    }
}

