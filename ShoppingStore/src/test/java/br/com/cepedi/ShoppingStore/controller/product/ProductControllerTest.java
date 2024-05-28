package br.com.cepedi.ShoppingStore.controller.product;

import br.com.cepedi.ShoppingStore.model.records.product.details.DataProductDetails;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataRegisterProduct;
import br.com.cepedi.ShoppingStore.model.records.product.input.DataUpdateProduct;
import br.com.cepedi.ShoppingStore.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void testRegister() {
        DataRegisterProduct requestData = new DataRegisterProduct(
                "Product Name",
                "Product Description",
                BigDecimal.valueOf(99.99),
                "SKU123",
                "http://image.url",
                1L,
                BigInteger.valueOf(10),
                1L,
                true
        );

        DataProductDetails responseData = new DataProductDetails(
                1L,
                "Product Name",
                "Product Description",
                BigDecimal.valueOf(99.99),
                "SKU123",
                "http://image.url",
                1L,
                BigInteger.valueOf(10),
                1L,
                true,true
        );

        when(productService.register(requestData)).thenReturn(responseData);

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        ResponseEntity<DataProductDetails> responseEntity = productController.register(requestData, uriComponentsBuilder);

        verify(productService, times(1)).register(requestData);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(responseData, responseEntity.getBody());
        assertEquals("/possible-facets/1", responseEntity.getHeaders().getLocation().getPath());
    }
    
    @Test
    void testList() {
        Page<DataProductDetails> page = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 5), 0);

        when(productService.list(any(Pageable.class))).thenReturn(page);

        ResponseEntity<Page<DataProductDetails>> responseEntity = productController.list(PageRequest.of(0, 5));

        verify(productService, times(1)).list(any(Pageable.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(page, responseEntity.getBody());
    }

    @Test
    void testDetails() {
        Long productId = 1L;
        DataProductDetails responseData = new DataProductDetails(
                1L,
                "Product Name",
                "Product Description",
                BigDecimal.valueOf(99.99),
                "SKU123",
                "http://image.url",
                1L,
                BigInteger.valueOf(10),
                1L,
                true,true
        );

        when(productService.detailsProduct(productId)).thenReturn(responseData);

        ResponseEntity<DataProductDetails> responseEntity = productController.details(productId);

        verify(productService, times(1)).detailsProduct(productId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseData, responseEntity.getBody());
    }

    @Test
    void testListByCategory() {
        Long categoryId = 1L;
        Page<DataProductDetails> page = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 5), 0);

        when(productService.detailsProductCategory(eq(categoryId), any(Pageable.class))).thenReturn(page);

        ResponseEntity<Page<DataProductDetails>> responseEntity = productController.listByCategory(categoryId, PageRequest.of(0, 5));

        verify(productService, times(1)).detailsProductCategory(eq(categoryId), any(Pageable.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(page, responseEntity.getBody());
    }
    
    @Test
    void testUpdate() {
        DataUpdateProduct requestData = new DataUpdateProduct(
                1L,
                "Product Name",
                "Product Description",
                BigDecimal.valueOf(199.99),
                "SKU123",
                "http://image.url",
                1L,
                BigInteger.valueOf(20),
                1L,
                true
		);

        DataProductDetails responseData = new DataProductDetails(
                1L,
                "Updated Name",
                "Updated Description",
                BigDecimal.valueOf(199.99),
                "SKU456",
                "http://newimage.url",
                1L,
                BigInteger.valueOf(20),
                1L,
                true,true
        );

        when(productService.updateProduct(requestData)).thenReturn(responseData);

        ResponseEntity<DataProductDetails> responseEntity = productController.update(requestData);

        verify(productService, times(1)).updateProduct(requestData);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseData, responseEntity.getBody());
    }
    
    @Test
    void testDelete() {
        Long productId = 1L;

        doNothing().when(productService).deleteProduct(productId);

        ResponseEntity<Void> responseEntity = productController.disabled(productId);

        verify(productService, times(1)).deleteProduct(productId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

}    