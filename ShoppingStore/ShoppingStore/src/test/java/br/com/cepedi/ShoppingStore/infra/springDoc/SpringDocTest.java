package br.com.cepedi.ShoppingStore.infra.springDoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SpringDocTest {

    @Test
    public void testOpenAPI() {
        // Crie uma instância da classe SpringDoc
        SpringDoc springDoc = new SpringDoc();

        // Chame o método openAPI()
        OpenAPI openAPI = springDoc.openAPI();

        // Verifique se o OpenAPI não é nulo
        assertNotNull(openAPI);

        // Verifique se as informações do OpenAPI estão corretas
        Info info = openAPI.getInfo();
        assertNotNull(info);
        assertEquals("ShoppingStore", info.getTitle());
        assertEquals("v1", info.getVersion());
        assertEquals("REST API Shopping Store", info.getDescription());

        License license = info.getLicense();
        assertNotNull(license);
        assertEquals("apache 2.4", license.getName());
        assertEquals("https://springdoc.org/", license.getUrl());

        // Verifique se a documentação externa está correta
        assertNotNull(openAPI.getExternalDocs());
        assertEquals("link de acesso", openAPI.getExternalDocs().getDescription());
        assertEquals("https://shoppingstore.com", openAPI.getExternalDocs().getUrl());
    }

}
