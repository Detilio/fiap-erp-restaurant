package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@OpenAPIDefinition
public class OpenApiConfig {

	@Bean
	OpenAPI customOpenAPI(@Value("${project.version}") String appVersion) {
	 return new OpenAPI()
	        .info(new Info()
	        .title("Fiap erp restaurant")
	        .version(appVersion)
	        .description("Fiap erp restaurant api server")
	        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}
}
