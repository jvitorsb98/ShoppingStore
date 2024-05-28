package br.com.cepedi.ShoppingStore.controller.possiblefacets;


import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;

import br.com.cepedi.ShoppingStore.model.records.possibleFacets.details.DataPossibleFacetsDetails;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataRegisterPossibleFacets;
import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataUpdatePossibleFacets;
import br.com.cepedi.ShoppingStore.service.possibleFacets.PossibleFacetsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PossibleFacetsControllerTest {

    @InjectMocks
    private PossibleFacetsController possibleFacetsController;

    @Mock
    private PossibleFacetsService possibleFacetsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        DataRegisterPossibleFacets data = new DataRegisterPossibleFacets("Facet 1", 1L);
        DataPossibleFacetsDetails details = new DataPossibleFacetsDetails(1L, "Facet 1", 1L, false);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        when(possibleFacetsService.register(data)).thenReturn(details);

        ResponseEntity<DataPossibleFacetsDetails> response = possibleFacetsController.register(data, uriBuilder);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(details, response.getBody());
        verify(possibleFacetsService, times(1)).register(data);
    }

    @Test
    void testGetById() {
        Long id = 1L;
        DataPossibleFacetsDetails details = new DataPossibleFacetsDetails(id, "Facet 1", 1L, false);

        when(possibleFacetsService.findById(id)).thenReturn(details);

        ResponseEntity<DataPossibleFacetsDetails> response = possibleFacetsController.findById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(details, response.getBody());
        verify(possibleFacetsService, times(1)).findById(id);
    }

    @Test
    void testUpdate() {
        DataUpdatePossibleFacets data = new DataUpdatePossibleFacets(1L, "Updated Facet", 1L);
        DataPossibleFacetsDetails updatedDetails = new DataPossibleFacetsDetails(1L, "Updated Facet", 1L, false);

        when(possibleFacetsService.update(data)).thenReturn(updatedDetails);

        ResponseEntity<DataPossibleFacetsDetails> response = possibleFacetsController.update(data);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDetails, response.getBody());
        verify(possibleFacetsService, times(1)).update(data);
    }

    @Test
    void testDisable() {
        Long id = 1L;

        doNothing().when(possibleFacetsService).disable(id);

        ResponseEntity<Object> response = possibleFacetsController.disabled(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(possibleFacetsService, times(1)).disable(id);
    }

    @Test
    void testListAll() {
        Page<DataPossibleFacetsDetails> facetsPage = new PageImpl<>(List.of(
                new DataPossibleFacetsDetails(1L, "Facet 1", 1L, false),
                new DataPossibleFacetsDetails(2L, "Facet 2", 1L, false)
        ));
        Pageable pageable = PageRequest.of(0, 10);

        when(possibleFacetsService.listAll(pageable)).thenReturn(facetsPage);

        ResponseEntity<Page<DataPossibleFacetsDetails>> response = possibleFacetsController.list(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(facetsPage, response.getBody());
        verify(possibleFacetsService, times(1)).listAll(pageable);
    }
}
