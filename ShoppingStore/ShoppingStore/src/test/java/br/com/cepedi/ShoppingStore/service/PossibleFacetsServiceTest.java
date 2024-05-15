package br.com.cepedi.ShoppingStore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.cepedi.ShoppingStore.model.PossibleFacets;
import br.com.cepedi.ShoppingStore.repository.PossibleFacetsRepository;
import br.com.cepedi.ShoppingStore.service.PossibleFacetsService;

@TestMethodOrder(OrderAnnotation.class)
public class PossibleFacetsServiceTest {

    @Mock
    private PossibleFacetsRepository possibleFacetsRepository;

    @InjectMocks
    private PossibleFacetsService possibleFacetsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test GetAllPossibleFacets: Should return all possible facets")
    public void testGetAllPossibleFacets() {
        PossibleFacets possibleFacets1 = new PossibleFacets();
        PossibleFacets possibleFacets2 = new PossibleFacets();
        List<PossibleFacets> expectedPossibleFacets = Arrays.asList(possibleFacets1, possibleFacets2);

        when(possibleFacetsRepository.findAll()).thenReturn(expectedPossibleFacets);

        List<PossibleFacets> actualPossibleFacets = possibleFacetsService.getAllPossibleFacets();

        assertEquals(expectedPossibleFacets, actualPossibleFacets);
    }



}
