package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.address;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat; // Usando AssertJ para asserções mais legíveis

class AddressRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Nested
    @DisplayName("Teste de Validação do AddressRequestDTO")
    class ValidationTests {

        @Test
        @DisplayName("Deve permitir um AddressRequestDTO válido")
        void shouldAllowValidAddressRequestDTO() {
            AddressRequestDTO dto = new AddressRequestDTO("SP", "São Paulo", "Rua Fictícia", 123, "Apto 101");
            Set<ConstraintViolation<AddressRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Não deve permitir estado nulo ou vazio")
        void shouldNotAllowNullOrEmptyState() {
            AddressRequestDTO dto = new AddressRequestDTO("", "São Paulo", "Rua Fictícia", 123, null);
            Set<ConstraintViolation<AddressRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).hasSize(1);
            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("state must be not null or empty");
        }

        @Test
        @DisplayName("Não deve permitir cidade nula ou vazia")
        void shouldNotAllowNullOrEmptyCity() {
            AddressRequestDTO dto = new AddressRequestDTO("SP", "", "Rua Fictícia", 123, null);
            Set<ConstraintViolation<AddressRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).hasSize(1);
            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("city must be not null or empty");
        }

        @Test
        @DisplayName("Não deve permitir rua nula ou vazia")
        void shouldNotAllowNullOrEmptyStreet() {
            AddressRequestDTO dto = new AddressRequestDTO("SP", "São Paulo", "", 123, null);
            Set<ConstraintViolation<AddressRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).hasSize(1);
            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("street must be not null or empty");
        }

        @Test
        @DisplayName("Deve permitir complemento nulo ou vazio")
        void shouldAllowNullOrEmptyComplement() {
            AddressRequestDTO dto = new AddressRequestDTO("SP", "São Paulo", "Rua Fictícia", 123, ""); // Vazio
            Set<ConstraintViolation<AddressRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).isEmpty(); // Complemento pode ser vazio ou nulo

            AddressRequestDTO dto2 = new AddressRequestDTO("SP", "São Paulo", "Rua Fictícia", 123, null); // Nulo
            Set<ConstraintViolation<AddressRequestDTO>> violations2 = validator.validate(dto2);
            assertThat(violations2).isEmpty(); // Complemento pode ser vazio ou nulo
        }

        @Test
        @DisplayName("Deve capturar múltiplas violações")
        void shouldCaptureMultipleViolations() {
            AddressRequestDTO dto = new AddressRequestDTO("", "", "", 123, null);
            Set<ConstraintViolation<AddressRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).hasSize(3); // Espera 3 violações: state, city, street
            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .containsExactlyInAnyOrder(
                            "state must be not null or empty",
                            "city must be not null or empty",
                            "street must be not null or empty"
                    );
        }
    }
}