package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AuthRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("Testes de Criação e Acesso aos Campos (Records)")
    class RecordBehaviorTests {

        @Test
        @DisplayName("Deve criar um AuthRequestDTO com login e password preenchidos corretamente")
        void shouldCreateAuthRequestDTOWithAllFields() {
            String login = "test@example.com";
            String password = "securepassword";

            AuthRequestDTO dto = new AuthRequestDTO(login, password);

            assertThat(dto.login()).isEqualTo(login);
            assertThat(dto.password()).isEqualTo(password);
        }

        @Test
        @DisplayName("Records devem ter equals e hashCode baseados em todos os componentes")
        void recordsShouldHaveCorrectEqualsAndHashCode() {
            AuthRequestDTO dto1 = new AuthRequestDTO("user@test.com", "password123");
            AuthRequestDTO dto2 = new AuthRequestDTO("user@test.com", "password123");
            AuthRequestDTO dto3 = new AuthRequestDTO("another@test.com", "password123"); // Diferente login

            assertThat(dto1).isEqualTo(dto2);
            assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
            assertThat(dto1).isNotEqualTo(dto3);
            assertThat(dto1.hashCode()).isNotEqualTo(dto3.hashCode());
        }
    }

    @Nested
    @DisplayName("Testes de Validação do AuthRequestDTO")
    class ValidationTests {

        @Test
        @DisplayName("Deve permitir um AuthRequestDTO válido")
        void shouldAllowValidAuthRequestDTO() {
            AuthRequestDTO dto = new AuthRequestDTO("valid@email.com", "validPassword123");
            Set<ConstraintViolation<AuthRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Não deve permitir login nulo")
        void shouldNotAllowNullLogin() {
            AuthRequestDTO dto = new AuthRequestDTO(null, "password123");
            Set<ConstraintViolation<AuthRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).hasSize(1);
            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("email is required");
        }

        @Test
        @DisplayName("Não deve permitir login vazio")
        void shouldNotAllowEmptyLogin() {
            AuthRequestDTO dto = new AuthRequestDTO("", "password123");
            Set<ConstraintViolation<AuthRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).hasSize(1);
            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("email is required");
        }

        @Test
        @DisplayName("Não deve permitir password nula")
        void shouldNotAllowNullPassword() {
            AuthRequestDTO dto = new AuthRequestDTO("test@email.com", null);
            Set<ConstraintViolation<AuthRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).hasSize(1);
            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("password is required");
        }

        @Test
        @DisplayName("Não deve permitir password vazia")
        void shouldNotAllowEmptyPassword() {
            AuthRequestDTO dto = new AuthRequestDTO("test@email.com", "");
            Set<ConstraintViolation<AuthRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).hasSize(1);
            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("password is required");
        }

        @Test
        @DisplayName("Deve capturar múltiplas violações")
        void shouldCaptureMultipleViolations() {
            AuthRequestDTO dto = new AuthRequestDTO("", null); // Login vazio e password nula
            Set<ConstraintViolation<AuthRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).hasSize(2); // Espera 2 violações
            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .containsExactlyInAnyOrder(
                            "email is required",
                            "password is required"
                    );
        }
    }
}