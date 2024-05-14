package br.com.cepedi.ShoppingStore.infra.springDoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDoc {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("ShoppingStore")
						.version("v1")
						.description("REST API Shopping Store")
						.license(new License()
								.name("apache 2.4")
								.url("https://springdoc.org/")
								)
						)
					.externalDocs(new ExternalDocumentation()
							.description("link de acesso")
							.url("https://shoppingstore.com"));
	}

}
