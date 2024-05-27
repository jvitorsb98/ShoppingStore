package br.com.cepedi.ShoppingStore.controller.category;

import br.com.cepedi.ShoppingStore.model.records.category.details.DataCategoryDetails;
import br.com.cepedi.ShoppingStore.model.records.category.input.DataRegisterCategory;
import br.com.cepedi.ShoppingStore.model.records.category.input.DataUpdateCategory;
import br.com.cepedi.ShoppingStore.service.category.CategoryService;
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

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        DataRegisterCategory data = new DataRegisterCategory("Category Name", "Category Description");
        DataCategoryDetails details = new DataCategoryDetails(1L, "Category Name", "Category Description");
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        when(categoryService.registerCategory(data)).thenReturn(details);

        ResponseEntity<DataCategoryDetails> response = categoryController.registerCategory(data, uriBuilder);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(details, response.getBody());
        verify(categoryService, times(1)).registerCategory(data);
    }
    
    @Test
    void testGetCategoryById() {
        Long id = 1L;
        DataCategoryDetails categoryDetails = new DataCategoryDetails(id, "Category Name", "Category Description");

        when(categoryService.getCategoryById(id)).thenReturn(categoryDetails);

        ResponseEntity<DataCategoryDetails> response = categoryController.getCategoryById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryDetails, response.getBody());
        verify(categoryService, times(1)).getCategoryById(id);
    }

    @Test
    void testDisable() {
        Long id = 1L;

        ResponseEntity<Void> response = categoryController.disableCategory(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(categoryService, times(1)).disableCategory(id);
    }
    
    @Test
    void testUpdate() {
        Long id = 1L;
        DataUpdateCategory data = new DataUpdateCategory(id, "Updated Category Name", "Updated Category Description");
        DataCategoryDetails updatedCategory = new DataCategoryDetails(id, "Updated Category Name", "Updated Category Description");

        when(categoryService.updateCategory(data)).thenReturn(updatedCategory);

        ResponseEntity<DataCategoryDetails> response = categoryController.updateCategory(data);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCategory, response.getBody());
        verify(categoryService, times(1)).updateCategory(data);
    }
    
    @Test
    void testListAllCategories() {
        Page<DataCategoryDetails> categoryPage = new PageImpl<>(List.of(
                new DataCategoryDetails(1L, "Category 1", "Description 1"),
                new DataCategoryDetails(2L, "Category 2", "Description 2")
        ));
        Pageable pageable = PageRequest.of(0, 10);

        when(categoryService.listAllCategories(pageable)).thenReturn(categoryPage);

        ResponseEntity<Page<DataCategoryDetails>> response = categoryController.listAllCategories(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryPage, response.getBody());
        verify(categoryService, times(1)).listAllCategories(pageable);
    }
    
    @Test
    void testListCategoriesByName() {
        String name = "Category";
        Page<DataCategoryDetails> categoryPage = new PageImpl<>(List.of(
                new DataCategoryDetails(1L, "Category 1", "Description 1"),
                new DataCategoryDetails(2L, "Category 2", "Description 2")
        ));
        Pageable pageable = PageRequest.of(0, 10);

        when(categoryService.listCategoriesByName(name, pageable)).thenReturn(categoryPage);

        ResponseEntity<Page<DataCategoryDetails>> response = categoryController.listCategoriesByName(name, pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryPage, response.getBody());
        verify(categoryService, times(1)).listCategoriesByName(name, pageable);
    }
}
