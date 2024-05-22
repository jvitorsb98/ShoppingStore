package br.com.cepedi.ShoppingStore.repository;


import br.com.cepedi.ShoppingStore.model.entitys.PossibleFacets;
import com.github.javafaker.Faker;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;


import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.Random.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PossibleFacetsRepositoryTest {

    @Mock
    private PossibleFacetsRepository possibleFacetsRepository;

    private Faker faker = new Faker();

    @Test
    public void testSavePossibleFacets() {
        // Given
        PossibleFacets possibleFacets = createMockPossibleFacets();

        // When
        when(possibleFacetsRepository.save(possibleFacets)).thenReturn(possibleFacets);
        PossibleFacets savedPossibleFacets = possibleFacetsRepository.save(possibleFacets);

        // Then
        assertEquals(possibleFacets, savedPossibleFacets);
    }

    @Test
    public void testFindAllPossibleFacets() {
        // Given
        List<PossibleFacets> mockPossibleFacetsList = List.of(createMockPossibleFacets(), createMockPossibleFacets());

        // When
        when(possibleFacetsRepository.findAll()).thenReturn(mockPossibleFacetsList);
        List<PossibleFacets> possibleFacetsList = possibleFacetsRepository.findAll();

        // Then
        assertEquals(2, possibleFacetsList.size());
    }

    @Test
    public void testFindPossibleFacetsById() {
        // Given
        PossibleFacets possibleFacets = createMockPossibleFacets();
        Long id = possibleFacets.getId();

        // When
        when(possibleFacetsRepository.findById(id)).thenReturn(Optional.of(possibleFacets));
        Optional<PossibleFacets> foundPossibleFacets = possibleFacetsRepository.findById(id);

        // Then
        assertEquals(id, foundPossibleFacets.get().getId());
    }

    private PossibleFacets createMockPossibleFacets() {
        PossibleFacets possibleFacets = new PossibleFacets();
        possibleFacets.setId(faker.number().randomNumber());
        possibleFacets.setName(faker.lorem().word());
        // Populate other fields as needed
        return possibleFacets;
    }
}

