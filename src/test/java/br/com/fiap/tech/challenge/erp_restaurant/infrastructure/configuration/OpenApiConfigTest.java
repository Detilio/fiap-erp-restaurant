package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OpenApiConfigTest {

    @Mock
    private OpenAPI mockOpenAPI;

    @Value("${project.version}")
    private String appVersion = "1.0.0";

    private OpenApiConfig openApiConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        openApiConfig = new OpenApiConfig();
    }

    @Test
    void testOpenApiConfiguration() {
        OpenAPI openAPI = openApiConfig.customOpenAPI(appVersion);

        // Verificando se a configuração foi feita corretamente
        assertNotNull(openAPI, "O bean OpenAPI não deve ser nulo.");
        assertEquals("Fiap erp restaurant", openAPI.getInfo().getTitle(), "O título está incorreto.");
        assertEquals("1.0.0", openAPI.getInfo().getVersion(), "A versão está incorreta.");
        assertEquals("Fiap erp restaurant api server", openAPI.getInfo().getDescription(), "A descrição está incorreta.");
        assertEquals("Apache 2.0", openAPI.getInfo().getLicense().getName(), "O nome da licença está incorreto.");
        assertEquals("http://springdoc.org", openAPI.getInfo().getLicense().getUrl(), "A URL da licença está incorreta.");
    }
}