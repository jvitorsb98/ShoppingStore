/*package br.com.cepedi.ShoppingStore.controller.category;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.cepedi.ShoppingStore.model.records.category.details.DataCategoryDetails;
import br.com.cepedi.ShoppingStore.service.category.CategoryService;

import java.util.Collections;

@WebMvcTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    void testRegisterCategory() throws Exception {
        String requestBody = "{\"name\":\"Test Category\",\"description\":\"Test Description\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v2/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testListAllCategories() throws Exception {
        Page<DataCategoryDetails> categories = new PageImpl<>(Collections.emptyList());
        when(categoryService.listAllCategories(any())).thenReturn(categories);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v2/categories"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testListCategoriesByName() throws Exception {
        Page<DataCategoryDetails> categories = new PageImpl<>(Collections.emptyList());
        when(categoryService.listCategoriesByName(any(), any())).thenReturn(categories);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v2/categories/search?name=test"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetCategoryById() throws Exception {
        DataCategoryDetails category = new DataCategoryDetails(1L, "Test Category", "Test Description");
        when(categoryService.getCategoryById(any())).thenReturn(category);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v2/categories/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void testUpdateCategory() throws Exception {
        String requestBody = "{\"name\":\"Updated Test Category\",\"description\":\"Updated Test Description\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v2/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDisableCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v2/categories/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

*/
