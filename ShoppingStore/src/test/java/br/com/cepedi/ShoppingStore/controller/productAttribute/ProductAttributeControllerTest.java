package br.com.cepedi.ShoppingStore.controller.productAttribute;

import br.com.cepedi.ShoppingStore.model.records.productAttribute.details.DataProductAttributeDetails;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataRegisterProductAttribute;
import br.com.cepedi.ShoppingStore.model.records.productAttribute.input.DataUpdateProductAttribute;
import br.com.cepedi.ShoppingStore.service.productAttribute.ProductAttributeService;
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductAttributeControllerTest {

    @Mock
    private ProductAttributeService productAttributeService;

    @InjectMocks
    private ProductAttributeController productAttributeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        DataRegisterProductAttribute requestData = new DataRegisterProductAttribute(
            "AttributeName",
            "AttributeValue",
            1L
        );

        DataProductAttributeDetails responseDetails = new DataProductAttributeDetails(
            1L,
            "AttributeName",
            "AttributeValue",
            1L,
            false
        );

        when(productAttributeService.register(any(DataRegisterProductAttribute.class)))
            .thenReturn(responseDetails);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        ResponseEntity<DataProductAttributeDetails> response = productAttributeController.register(requestData, uriBuilder);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDetails, response.getBody());
    }
    
    @Test
    void testList() {
        DataProductAttributeDetails details = new DataProductAttributeDetails(
                1L,
                "AttributeName",
                "AttributeValue",
                1L,
                false
        );

        Page<DataProductAttributeDetails> page = new PageImpl<>(List.of(details));
        when(productAttributeService.list(any(Pageable.class))).thenReturn(page);

        Pageable pageable = PageRequest.of(0, 5);
        ResponseEntity<Page<DataProductAttributeDetails>> response = productAttributeController.list(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
    }
    
    @Test
    void testListDeactivated() {
        DataProductAttributeDetails details = new DataProductAttributeDetails(
                1L,
                "AttributeName",
                "AttributeValue",
                1L,
                false
        );

        Page<DataProductAttributeDetails> page = new PageImpl<>(List.of(details));
        when(productAttributeService.listDeactivated(any(Pageable.class))).thenReturn(page);

        Pageable pageable = PageRequest.of(0, 5);
        ResponseEntity<Page<DataProductAttributeDetails>> response = productAttributeController.listDeactivated(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
    }

    @Test
    void testListByProductId() {
        DataProductAttributeDetails details = new DataProductAttributeDetails(
                1L,
                "AttributeName",
                "AttributeValue",
                1L,
                false
        );

        List<DataProductAttributeDetails> list = List.of(details);
        when(productAttributeService.listByProductId(anyLong())).thenReturn(list);

        ResponseEntity<List<DataProductAttributeDetails>> response = productAttributeController.listByProductId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void testListByProductIdDeactivated() {
        DataProductAttributeDetails details = new DataProductAttributeDetails(
                1L,
                "AttributeName",
                "AttributeValue",
                1L,
                false
        );

        List<DataProductAttributeDetails> list = List.of(details);
        when(productAttributeService.listByProductIdDeactivated(anyLong())).thenReturn(list);

        ResponseEntity<List<DataProductAttributeDetails>> response = productAttributeController.listByProductIdDeactivated(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void testGetDetails() {
        DataProductAttributeDetails details = new DataProductAttributeDetails(
                1L,
                "AttributeName",
                "AttributeValue",
                1L,
                false
        );

        when(productAttributeService.details(anyLong())).thenReturn(details);

        ResponseEntity<DataProductAttributeDetails> response = productAttributeController.getDetails(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(details, response.getBody());
    }
    
    @Test
    void testUpdate() {
        DataUpdateProductAttribute updateData = new DataUpdateProductAttribute(
                1L,
                "UpdatedName",
                "UpdatedValue"
        );

        DataProductAttributeDetails updatedDetails = new DataProductAttributeDetails(
                1L,
                "UpdatedName",
                "UpdatedValue",
                1L,
                false
        );

        when(productAttributeService.update(any(DataUpdateProductAttribute.class))).thenReturn(updatedDetails);

        ResponseEntity<DataProductAttributeDetails> response = productAttributeController.update(updateData);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDetails, response.getBody());
        verify(productAttributeService, times(1)).update(any(DataUpdateProductAttribute.class));
    }

    @Test
    void testDisabled() {
        Long id = 1L;

        doNothing().when(productAttributeService).disabled(anyLong());

        ResponseEntity<Void> response = productAttributeController.disabled(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productAttributeService, times(1)).disabled(anyLong());
    }
}
