package br.com.cepedi.ShoppingStore.controller.productRating;

import br.com.cepedi.ShoppingStore.model.records.productRating.details.DataProductRatingDetails;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataRegisterProductRating;
import br.com.cepedi.ShoppingStore.model.records.productRating.input.DataUpdateProductRating;
import br.com.cepedi.ShoppingStore.service.productRating.ProductRatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductRatingControllerTest {

    @Mock
    private ProductRatingService service;

    @Mock
    private UriComponentsBuilder uriComponentsBuilder;

    @InjectMocks
    private ProductRatingController controller;

    @Test
    void testRegistrarAvaliacaoProduto() {
        // Dados fictícios
        DataRegisterProductRating requestData = new DataRegisterProductRating(
                1L, // productId
                BigDecimal.valueOf(4.5), // ratingStars
                "Produto ótimo!", // review
				1L // userId
        );

        DataProductRatingDetails responseData = new DataProductRatingDetails(
                1L, // id
                1L, // productId
                BigDecimal.valueOf(4.5), // ratingStars
                "Produto ótimo!", // review
                1L, // userId,
            true
        );

        // Simulação da resposta do serviço
        when(service.register(requestData)).thenReturn(responseData);

        // Simulação da criação de URI
        UriComponents uriComponents = mock(UriComponents.class);
        when(uriComponents.toUri()).thenReturn(URI.create("/avaliacoes-produto/1"));
        when(uriComponentsBuilder.path(anyString())).thenReturn(uriComponentsBuilder);
        when(uriComponentsBuilder.buildAndExpand(responseData.id())).thenReturn(uriComponents);
        when(uriComponentsBuilder.build()).thenReturn(uriComponents);

        // Teste do método do controlador
        ResponseEntity<DataProductRatingDetails> responseEntity = controller.register(requestData, uriComponentsBuilder);

        // Verificação se o método do serviço foi chamado com os argumentos corretos
        verify(service, times(1)).register(requestData);

        // Verificação da resposta do controlador
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(responseData, responseEntity.getBody());
        assertEquals("/avaliacoes-produto/1", responseEntity.getHeaders().getLocation().getPath());
    }
    
    @Test
    void testListProductRatings() {
        // Dados fictícios
        List<DataProductRatingDetails> ratingsList = new ArrayList<>();
        ratingsList.add(new DataProductRatingDetails(1L, 1L, BigDecimal.valueOf(4.5), "Produto ótimo!", 1L,true));
        ratingsList.add(new DataProductRatingDetails(2L, 2L, BigDecimal.valueOf(3.5), "Produto razoável", 2L,true));
        Page<DataProductRatingDetails> page = new PageImpl<>(ratingsList);

        // Simulação da resposta do serviço
        when(service.list(any(Pageable.class))).thenReturn(page);

        // Teste do método do controlador
        ResponseEntity<Page<DataProductRatingDetails>> responseEntity = controller.list(Pageable.unpaged());

        // Verificação se o método do serviço foi chamado com os argumentos corretos
        verify(service, times(1)).list(any(Pageable.class));

        // Verificação da resposta do controlador
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(page, responseEntity.getBody());
    }
    
    @Test
    void testGetProductRatingDetails() {
        // Dados fictícios
        Long productId = 1L;
        DataProductRatingDetails details = new DataProductRatingDetails(1L, 1L, BigDecimal.valueOf(4.5), "Produto ótimo!", 1L,true);

        // Simulação da resposta do serviço
        when(service.detailsProduct(productId)).thenReturn(details);

        // Teste do método do controlador
        ResponseEntity<DataProductRatingDetails> responseEntity = controller.details(productId);

        // Verificação se o método do serviço foi chamado com o argumento correto
        verify(service, times(1)).detailsProduct(productId);

        // Verificação da resposta do controlador
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(details, responseEntity.getBody());
    }
    
    @Test
    void testListByProduct() {
        // Dados fictícios
        Long productId = 1L;
        List<DataProductRatingDetails> ratingsList = new ArrayList<>();
        ratingsList.add(new DataProductRatingDetails(1L, 1L, BigDecimal.valueOf(4.5), "Produto ótimo!", 1L,false));
        ratingsList.add(new DataProductRatingDetails(2L, 1L, BigDecimal.valueOf(3.5), "Produto razoável", 2L,false));
        Page<DataProductRatingDetails> page = new PageImpl<>(ratingsList);

        // Simulação da resposta do serviço
        when(service.getProductRatingsByProductId(productId, Pageable.unpaged())).thenReturn(page);

        // Teste do método do controlador
        ResponseEntity<Page<DataProductRatingDetails>> responseEntity = controller.listByProduct(productId, Pageable.unpaged());

        // Verificação se o método do serviço foi chamado com os argumentos corretos
        verify(service, times(1)).getProductRatingsByProductId(productId, Pageable.unpaged());

        // Verificação da resposta do controlador
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(page, responseEntity.getBody());
    }

    @Test
    void testListByUser() {
        // Dados fictícios
        Long userId = 1L;
        List<DataProductRatingDetails> ratingsList = new ArrayList<>();
        ratingsList.add(new DataProductRatingDetails(1L, 1L, BigDecimal.valueOf(4.5), "Produto ótimo!", 1L,false));
        ratingsList.add(new DataProductRatingDetails(2L, 2L, BigDecimal.valueOf(3.5), "Produto razoável", 1L,false));
        Page<DataProductRatingDetails> page = new PageImpl<>(ratingsList);

        // Simulação da resposta do serviço
        when(service.getProductRatingsByUserId(userId, Pageable.unpaged())).thenReturn(page);

        // Teste do método do controlador
        ResponseEntity<Page<DataProductRatingDetails>> responseEntity = controller.listByUser(userId, Pageable.unpaged());

        // Verificação se o método do serviço foi chamado com os argumentos corretos
        verify(service, times(1)).getProductRatingsByUserId(userId, Pageable.unpaged());

        // Verificação da resposta do controlador
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(page, responseEntity.getBody());
    }
    
    @Test
    void testUpdateProductRating() {
        // Dados fictícios
        DataUpdateProductRating requestData = new DataUpdateProductRating(
				1L, // productId
                1L, // id
                BigDecimal.valueOf(4.5), // ratingStars
                "Produto excelente!", // review
				1L // userId
        );

        DataProductRatingDetails responseData = new DataProductRatingDetails(
                1L, // id
                1L, // productId
                BigDecimal.valueOf(4.5), // ratingStars
                "Produto excelente!", // review
                1L, // userId
                false
        );

        // Simulação da resposta do serviço
        when(service.updateProductRating(requestData)).thenReturn(responseData);

        // Teste do método do controlador
        ResponseEntity<DataProductRatingDetails> responseEntity = controller.update(requestData);

        // Verificação se o método do serviço foi chamado com os argumentos corretos
        verify(service, times(1)).updateProductRating(requestData);

        // Verificação da resposta do controlador
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseData, responseEntity.getBody());
    }
    
    @Test
    void testDeleteProductRating() {
        // Dados fictícios
        Long productId = 1L;

        // Teste do método do controlador
        ResponseEntity<Void> responseEntity = controller.delete(productId);

        // Verificação se o método do serviço foi chamado com os argumentos corretos
        verify(service, times(1)).disableProductRating(productId);

        // Verificação da resposta do controlador
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody()); // No content expected in the response body
    }
}
