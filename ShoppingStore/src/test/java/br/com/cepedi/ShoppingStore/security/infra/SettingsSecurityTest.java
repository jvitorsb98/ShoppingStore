package br.com.cepedi.ShoppingStore.security.infra;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.Random.class)
public class SettingsSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPublicEndpoints_AccessibleWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/v3/api-docs")).andExpect(status().isOk());
    }

    @Test
    public void testRestrictedEndpoints_RequiresAuthentication() throws Exception {
        mockMvc.perform(get("/api/some-private-endpoint")).andExpect(status().isForbidden());

    }

    @Test
    public void testAdminEndpoints_RequiresAdminRole() throws Exception {
        mockMvc.perform(post("/auth/register")).andExpect(status().isForbidden());

    }
}