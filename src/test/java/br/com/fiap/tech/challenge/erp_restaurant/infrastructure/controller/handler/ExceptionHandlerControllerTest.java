package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.handler;

import br.com.fiap.tech.challenge.erp_restaurant.application.exception.AuthenticationException;
import br.com.fiap.tech.challenge.erp_restaurant.application.exception.NotFoundException;
import br.com.fiap.tech.challenge.erp_restaurant.application.exception.NotUniqueException;
import br.com.fiap.tech.challenge.erp_restaurant.application.exception.RoleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlerControllerTest {

    @InjectMocks
    private ExceptionHandlerController exceptionHandlerController;

    @Mock
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        // Nada específico para configurar antes de cada teste no controller em si,
        // mas é um bom lugar para inicializar coisas se necessário.
    }

    @Nested
    @DisplayName("Testes para Tratamento de Exceções Genéricas")
    class GenericExceptionHandlingTests {

        @Test
        @DisplayName("Deve lidar com Exception.class e retornar INTERNAL_SERVER_ERROR")
        void shouldHandleGenericException() {
            Exception ex = new Exception("Erro genérico");

            ResponseEntity<ErrorMessage> response = exceptionHandlerController.handleException(ex);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getMessage()).isEqualTo("Somethig here gone wrong");
        }

        @Test
        @DisplayName("Deve lidar com RuntimeException.class e retornar BAD_REQUEST")
        void shouldHandleRuntimeException() {
            RuntimeException ex = new RuntimeException("Erro em tempo de execução");

            ResponseEntity<ErrorMessage> response = exceptionHandlerController.handleRuntimeException(ex);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getMessage()).isEqualTo("Somethig here gone wrong");
        }
    }

    @Nested
    @DisplayName("Testes para Tratamento de Exceções de Recurso/Negócio")
    class ResourceAndBusinessExceptionHandlingTests {

        @Test
        @DisplayName("Deve lidar com NoResourceFoundException e retornar NOT_FOUND com mensagem específica")
        void shouldHandleNoResourceFoundException() {
            NoResourceFoundException ex = new NoResourceFoundException(GET, "/nonexistent");

            ResponseEntity<ErrorMessage> response = exceptionHandlerController.handleValidationExceptions(ex);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getMessage()).isEqualTo("No signs of humanity here");
        }

        @Test
        @DisplayName("Deve lidar com NotFoundException e retornar NOT_FOUND com a mensagem da exceção")
        void shouldHandleCustomNotFoundException() {
            String errorMessage = "Recurso não encontrado pelo ID 123";
            NotFoundException ex = new NotFoundException(errorMessage);

            ResponseEntity<ErrorMessage> response = exceptionHandlerController.handleValidationExceptions(ex);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getMessage()).isEqualTo(errorMessage);
        }

        @Test
        @DisplayName("Deve lidar com AuthenticationException e retornar UNAUTHORIZED com a mensagem da exceção")
        void shouldHandleAuthenticationException() {
            String errorMessage = "Credenciais inválidas";
            AuthenticationException ex = new AuthenticationException(errorMessage);

            ResponseEntity<ErrorMessage> response = exceptionHandlerController.handleAuthenticationException(ex);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getMessage()).isEqualTo(errorMessage);
        }

        @Test
        @DisplayName("Deve lidar com RoleException e retornar NOT_FOUND com a mensagem da exceção")
        void shouldHandleRoleException() {
            String errorMessage = "Papel de usuário inválido";
            RoleException ex = new RoleException(errorMessage);

            ResponseEntity<ErrorMessage> response = exceptionHandlerController.handleRoleException(ex);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getMessage()).isEqualTo(errorMessage);
        }

        @Test
        @DisplayName("Deve lidar com NotUniqueException e retornar BAD_REQUEST com a mensagem da exceção")
        void shouldHandleNotUniqueException() {
            String errorMessage = "Campo já existente no sistema";
            NotUniqueException ex = new NotUniqueException(errorMessage);

            ResponseEntity<ErrorMessage> response = exceptionHandlerController.handleRoleException(ex); // Nome do método é handleRoleException, mas trata NotUniqueException

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getMessage()).isEqualTo(errorMessage);
        }
    }

    @Nested
    @DisplayName("Testes para Tratamento de Validação de Argumentos")
    class ValidationExceptionHandlingTests {

        @Test
        @DisplayName("Deve lidar com MethodArgumentNotValidException e retornar BAD_REQUEST com lista de erros de campo")
        void shouldHandleMethodArgumentNotValidException() {
            // Cria um mock para BindingResult
            BindingResult bindingResult = mock(BindingResult.class);

            // Simula alguns FieldErrors
            FieldError fieldError1 = new FieldError("objectName", "field1", "Mensagem de erro 1");
            FieldError fieldError2 = new FieldError("objectName", "field2", "Mensagem de erro 2");

            when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

            // Cria a exceção MethodArgumentNotValidException com o BindingResult mockado
            MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

            ResponseEntity<List<String>> response = exceptionHandlerController.handleValidationExceptions(ex);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody()).hasSize(2);
            assertThat(response.getBody()).containsExactlyInAnyOrder("field1: Mensagem de erro 1", "field2: Mensagem de erro 2");
        }

        @Test
        @DisplayName("Deve lidar com MethodArgumentNotValidException sem erros de campo")
        void shouldHandleMethodArgumentNotValidExceptionWithNoFieldErrors() {
            BindingResult bindingResult = mock(BindingResult.class);
            when(bindingResult.getFieldErrors()).thenReturn(Collections.emptyList());

            MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

            ResponseEntity<List<String>> response = exceptionHandlerController.handleValidationExceptions(ex);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody()).isEmpty();
        }
    }
}