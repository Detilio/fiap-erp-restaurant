package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.extractProperty;

class UserRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("Testes de Comportamento do Record (Criação e Acesso)")
    class RecordBehaviorTests {

        @Test
        @DisplayName("Deve criar um UserRequestDTO com todos os campos válidos")
        void shouldCreateDtoWithAllValidFields() {
            int id = 1;
            String name = "John Doe";
            String email = "john.doe@example.com";
            String login = "johndoe";
            String password = "StrongPassword123!";
            Long addressId = 101L;

            UserRequestDTO dto = new UserRequestDTO(id, name, email, login, password, addressId);

            assertThat(dto.id()).isEqualTo(id);
            assertThat(dto.name()).isEqualTo(name);
            assertThat(dto.email()).isEqualTo(email);
            assertThat(dto.login()).isEqualTo(login);
            assertThat(dto.password()).isEqualTo(password);
            assertThat(dto.address()).isEqualTo(addressId);
        }

        @Test
        @DisplayName("Deve criar um UserRequestDTO com ID de endereço nulo")
        void shouldCreateDtoWithNullAddressId() {
            int id = 2;
            String name = "Jane Smith";
            String email = "jane.smith@example.com";
            String login = "janesmith";
            String password = "SecurePass456#";
            Long addressId = null; // O endereço pode ser nulo de acordo com a definição da DTO

            UserRequestDTO dto = new UserRequestDTO(id, name, email, login, password, addressId);

            assertThat(dto.id()).isEqualTo(id);
            assertThat(dto.address()).isNull();
        }

        @Test
        @DisplayName("Records devem ter equals e hashCode corretos baseados em todos os componentes")
        void recordsShouldHaveCorrectEqualsAndHashCode() {
            UserRequestDTO dto1 = new UserRequestDTO(1, "Nome", "email@teste.com", "login", "senha", 100L);
            UserRequestDTO dto2 = new UserRequestDTO(1, "Nome", "email@teste.com", "login", "senha", 100L);
            UserRequestDTO dto3 = new UserRequestDTO(2, "Nome", "email@teste.com", "login", "senha", 100L); // ID diferente
            UserRequestDTO dto4 = new UserRequestDTO(1, "Nome Diferente", "email@teste.com", "login", "senha", 100L); // Nome diferente
            UserRequestDTO dto5 = new UserRequestDTO(1, "Nome", "email@teste.com", "login", "senha", 200L); // Endereço diferente

            assertThat(dto1).isEqualTo(dto2);
            assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());

            assertThat(dto1).isNotEqualTo(dto3);
            assertThat(dto1.hashCode()).isNotEqualTo(dto3.hashCode());

            assertThat(dto1).isNotEqualTo(dto4);
            assertThat(dto1.hashCode()).isNotEqualTo(dto4.hashCode());

            assertThat(dto1).isNotEqualTo(dto5);
            assertThat(dto1.hashCode()).isNotEqualTo(dto5.hashCode());
        }
    }

    @Nested
    @DisplayName("Testes de Validação (Bean Validation)")
    class ValidationTests {

        @Test
        @DisplayName("Deve passar na validação para um UserRequestDTO completamente válido")
        void shouldPassValidationForValidDto() {
            UserRequestDTO dto = new UserRequestDTO(
                    1, "Usuário Válido", "usuario.valido@dominio.com", "loginvalido", "SenhaForte123!", 101L);
            Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Não deve permitir nome nulo ou vazio")
        void shouldNotAllowNullOrEmptyName() {
            UserRequestDTO dtoNull = new UserRequestDTO(1, null, "teste@email.com", "login", "senha", 101L);
            Set<ConstraintViolation<UserRequestDTO>> violationsNull = validator.validate(dtoNull);
            assertThat(violationsNull).hasSize(1);
            assertThat(extractProperty("message").from(violationsNull))
                    .contains("name must be not null or empty");

            UserRequestDTO dtoEmpty = new UserRequestDTO(1, "", "teste@email.com", "login", "senha", 101L);
            Set<ConstraintViolation<UserRequestDTO>> violationsEmpty = validator.validate(dtoEmpty);
            assertThat(violationsEmpty).hasSize(1);
            assertThat(extractProperty("message").from(violationsEmpty))
                    .contains("name must be not null or empty");
        }

        @Test
        @DisplayName("Não deve permitir email nulo ou vazio")
        void shouldNotAllowNullOrEmptyEmail() {
            UserRequestDTO dtoNull = new UserRequestDTO(1, "Nome", null, "login", "senha", 101L);
            Set<ConstraintViolation<UserRequestDTO>> violationsNull = validator.validate(dtoNull);
            assertThat(violationsNull).hasSize(1);
            assertThat(extractProperty("message").from(violationsNull))
                    .contains("email must be not null or empty");

            UserRequestDTO dtoEmpty = new UserRequestDTO(1, "Nome", "", "login", "senha", 101L);
            Set<ConstraintViolation<UserRequestDTO>> violationsEmpty = validator.validate(dtoEmpty);
            assertThat(violationsEmpty).hasSize(1);
            assertThat(extractProperty("message").from(violationsEmpty))
                    .contains("email must be not null or empty");
        }

        @Test
        @DisplayName("Não deve permitir formato de email inválido")
        void shouldNotAllowInvalidEmailFormat() {
            UserRequestDTO dto = new UserRequestDTO(1, "Nome", "email-invalido", "login", "senha", 101L);
            Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).hasSize(1);
            assertThat(extractProperty("message").from(violations))
                    .contains("email is not valid");

            dto = new UserRequestDTO(1, "Nome", "email@.com", "login", "senha", 101L);
            violations = validator.validate(dto);
            assertThat(violations).hasSize(1);
            assertThat(extractProperty("message").from(violations))
                    .contains("email is not valid");
        }

        @Test
        @DisplayName("Não deve permitir login nulo ou vazio")
        void shouldNotAllowNullOrEmptyLogin() {
            UserRequestDTO dtoNull = new UserRequestDTO(1, "Nome", "teste@email.com", null, "senha", 101L);
            Set<ConstraintViolation<UserRequestDTO>> violationsNull = validator.validate(dtoNull);
            assertThat(violationsNull).hasSize(1);
            assertThat(extractProperty("message").from(violationsNull))
                    .contains("login must be not null or empty");

            UserRequestDTO dtoEmpty = new UserRequestDTO(1, "Nome", "teste@email.com", "", "senha", 101L);
            Set<ConstraintViolation<UserRequestDTO>> violationsEmpty = validator.validate(dtoEmpty);
            assertThat(violationsEmpty).hasSize(1);
            assertThat(extractProperty("message").from(violationsEmpty))
                    .contains("login must be not null or empty");
        }

        @Test
        @DisplayName("Não deve permitir senha nula ou vazia")
        void shouldNotAllowNullOrEmptyPassword() {
            UserRequestDTO dtoNull = new UserRequestDTO(1, "Nome", "teste@email.com", "login", null, 101L);
            Set<ConstraintViolation<UserRequestDTO>> violationsNull = validator.validate(dtoNull);
            assertThat(violationsNull).hasSize(1);
            assertThat(extractProperty("message").from(violationsNull))
                    .contains("password must be not null or empty");

            UserRequestDTO dtoEmpty = new UserRequestDTO(1, "Nome", "teste@email.com", "login", "", 101L);
            Set<ConstraintViolation<UserRequestDTO>> violationsEmpty = validator.validate(dtoEmpty);
            assertThat(violationsEmpty).hasSize(1);
            assertThat(extractProperty("message").from(violationsEmpty))
                    .contains("password must be not null or empty");
        }

        @Test
        @DisplayName("Deve capturar múltiplas violações simultaneamente")
        void shouldCaptureMultipleViolations() {
            UserRequestDTO dto = new UserRequestDTO(
                    1, "", "email-invalido", null, "", null); // Nome vazio, email inválido, login nulo, senha vazia
            Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).hasSize(4); // Espera-se 4 violações
            assertThat(extractProperty("message").from(violations))
                    .containsExactlyInAnyOrder(
                            "name must be not null or empty",
                            "email is not valid",
                            "login must be not null or empty",
                            "password must be not null or empty"
                    );
        }
    }
}