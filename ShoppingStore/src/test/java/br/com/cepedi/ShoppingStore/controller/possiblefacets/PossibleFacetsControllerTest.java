package br.com.cepedi.ShoppingStore.controller.possiblefacets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.awt.print.Pageable;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.cepedi.ShoppingStore.model.records.possibleFacets.input.DataRegisterPossibleFacets;
import br.com.cepedi.ShoppingStore.service.possibleFacets.PossibleFacetsService; 

@SpringBootTest
@AutoConfigureMockMvc
public class PossibleFacetsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PossibleFacetsService possibleFacetsService;

    private static final Faker faker = new Faker();

    @BeforeEach
    void setup() {
        // Setting up mock service behavior
        when(possibleFacetsService.register(any(DataRegisterPossibleFacets.class))).thenReturn(null);
    }

    @Test
    @DisplayName("Test for registering a possible facet with valid data")
    @WithMockUser(username = "user", roles = {"USER"})
    void createPossibleFacetWithValidData() throws Exception {
        // Setting up data for the test
        DataRegisterPossibleFacets data = new DataRegisterPossibleFacets(null, null);

        // Making HTTP request and checking status
        mockMvc.perform(post("/v2/possible-facets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test for registering a possible facet with invalid data")
    @WithMockUser(username = "user", roles = {"USER"})
    void createPossibleFacetWithInvalidData() throws Exception {
        // Setting up invalid data for the test
        DataRegisterPossibleFacets data = new DataRegisterPossibleFacets(null, null);

        // Making HTTP request and checking status for BadRequest
        mockMvc.perform(post("/v2/possible-facets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test for getting all activated possible facets")
    @WithMockUser
    void getAllActivatedPossibleFacets() throws Exception {
        // Simulating data and mock service behavior
        List<DataRegisterPossibleFacets> expectedDetails = ;

        Page<DataRegisterPossibleFacets> simulatedPage = new PageImpl<>(expectedDetails);
        when(possibleFacetsService.listAll((org.springframework.data.domain.Pageable) any(Pageable.class))).thenReturn(simulatedPage);

        // Making HTTP request and checking status and content
        mockMvc.perform(get("/v2/possible-facets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(expectedDetails.size())));
    }

	private ResultMatcher jsonPath(String string, Object hasSize) {
		// TODO Auto-generated method stub
		return null;
	}

	private Object hasSize(int size) {
		// TODO Auto-generated method stub
		return null;
	}

	private MockHttpServletRequestBuilder get(String string) {
		// TODO Auto-generated method stub
		return null;
	}

    // Additional tests for other controller methods
}
