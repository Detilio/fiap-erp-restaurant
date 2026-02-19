package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.v1;

import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.auth.AuthUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.auth.AuthRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.handler.ExceptionHandlerController;
import br.com.fiap.tech.challenge.erp_restaurant.mapper.UserMapper;
import br.com.fiap.tech.challenge.erp_restaurant.shared.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(AuthenticationController.class)
@Import({AuthenticationControllerTest.Config.class, ExceptionHandlerController.class})
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthUseCase authUseCase;

    @TestConfiguration
    static class Config {
        @Bean
        public AuthUseCase authUseCase() {
            return mock(AuthUseCase.class);
        }
    }

    // Método auxiliar para criar um usuário de domínio simulado (se AuthUseCase.authenticate retornar User)
    private User createSampleUser(String login, String password) {
        // Supondo que sua classe User tenha um construtor ou builder adequado para criar um objeto
        // que o mapper geraria. Pode ser uma nova instância simples ou um mock.
        return new User(1L, "RAFAEL", "rafael@email.com", login, password, Role.OWNER); // Exemplo, ajuste conforme seu construto de User
    }

    @Nested
    @DisplayName("POST /v1/auth")
    class AuthEndpointTests {

        @Test
        @DisplayName("Deve retornar status 200 OK e chamar o use case para autenticação de credenciais válidas")
        void shouldReturnOkAndAuthenticateValidCredentials() throws Exception {
            // Arrange
            AuthRequestDTO authRequestDTO = new AuthRequestDTO("test_login", "test_password");
            User userDomain = UserMapper.INSTANCE.authDtoToDomain(authRequestDTO);

            // Cria um objeto User simulado para o retorno do mock
            User authenticatedUser = createSampleUser("test_login", "test_password");

            // Configura o mock do use case para retornar 'authenticatedUser' quando 'authenticate' for chamado
            // com qualquer objeto do tipo User
            when(authUseCase.authenticate(any(User.class))).thenReturn(authenticatedUser); //

            // Act & Assert
            mockMvc.perform(post("/v1/auth")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(authRequestDTO)))
                    .andExpect(status().isOk());

            // Verifica se o metodo authenticate do use case foi chamado com o objeto de domínio correto
            verify(authUseCase, times(1)).authenticate(eq(userDomain));
        }

        @Test
        @DisplayName("Deve retornar status 400 BAD REQUEST para AuthRequestDTO com login vazio")
        void shouldReturnBadRequestForEmptyLogin() throws Exception {
            AuthRequestDTO invalidDto = new AuthRequestDTO("", "password123");

            mockMvc.perform(post("/v1/auth")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidDto)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$[0]").value("login: email is required"));

            verify(authUseCase, never()).authenticate(any(User.class));
        }

        @Test
        @DisplayName("Deve retornar status 400 BAD REQUEST para AuthRequestDTO com senha vazia")
        void shouldReturnBadRequestForEmptyPassword() throws Exception {
            AuthRequestDTO invalidDto = new AuthRequestDTO("test_login", "");

            mockMvc.perform(post("/v1/auth")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidDto)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$[0]").value("password: password is required"));

            verify(authUseCase, never()).authenticate(any(User.class));
        }

        @Test
        @DisplayName("Deve retornar status 400 BAD REQUEST para AuthRequestDTO com login e senha vazios")
        void shouldReturnBadRequestForEmptyLoginAndPassword() throws Exception {
            AuthRequestDTO invalidDto = new AuthRequestDTO("", "");

            String responseContent = mockMvc.perform(post("/v1/auth")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidDto)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$").isArray())
                    .andReturn().getResponse().getContentAsString();

            List<String> errors = objectMapper.readValue(responseContent, new TypeReference<List<String>>() {});

            assertThat(errors)
                    .containsExactlyInAnyOrder(
                            "login: email is required",
                            "password: password is required"
                    );

            verify(authUseCase, never()).authenticate(any(User.class));
        }
    }
}