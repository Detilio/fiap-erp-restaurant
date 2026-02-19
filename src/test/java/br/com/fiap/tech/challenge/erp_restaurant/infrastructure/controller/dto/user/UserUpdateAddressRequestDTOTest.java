package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user;

import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.address.AddressRequestDTO;
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

class UserUpdateAddressRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        // Configura o Validator para as anotações de validação
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // Método auxiliar para criar um AddressRequestDTO válido
    private AddressRequestDTO createValidAddressRequestDTO() {
        return new AddressRequestDTO("SP", "São Paulo", "Rua Principal", 123, "Apto 10");
    }

    // Método auxiliar para criar um AddressRequestDTO com dados diferentes (mas válidos)
    private AddressRequestDTO createDifferentValidAddressRequestDTO() {
        return new AddressRequestDTO("RJ", "Rio de Janeiro", "Av. Central", 456, null);
    }

    @Nested
    @DisplayName("Testes de Comportamento do Record (Criação e Acesso)")
    class RecordBehaviorTests {

        @Test
        @DisplayName("Deve criar um UserUpdateAddressRequestDTO com todos os campos válidos")
        void shouldCreateDtoWithAllValidFields() {
            int userId = 1;
            AddressRequestDTO address = createValidAddressRequestDTO();

            UserUpdateAddressRequestDTO dto = new UserUpdateAddressRequestDTO(userId, address);

            assertThat(dto.id()).isEqualTo(userId);
            assertThat(dto.address()).isEqualTo(address);
            assertThat(dto.address().street()).isEqualTo("Rua Principal");
            assertThat(dto.address().number()).isEqualTo(123);
        }

        @Test
        @DisplayName("Deve criar um UserUpdateAddressRequestDTO com o endereço nulo")
        void shouldCreateDtoWithNullAddress() {
            int userId = 2;
            AddressRequestDTO address = null;

            UserUpdateAddressRequestDTO dto = new UserUpdateAddressRequestDTO(userId, address);

            assertThat(dto.id()).isEqualTo(userId);
            assertThat(dto.address()).isNull();
        }

        @Test
        @DisplayName("Records devem ter equals e hashCode corretos baseados em todos os componentes, incluindo DTO aninhado")
        void recordsShouldHaveCorrectEqualsAndHashCode() {
            AddressRequestDTO address1 = createValidAddressRequestDTO();
            AddressRequestDTO address2 = createValidAddressRequestDTO(); // Mesmos dados, instância diferente
            AddressRequestDTO address3 = createDifferentValidAddressRequestDTO(); // Dados diferentes

            UserUpdateAddressRequestDTO dto1 = new UserUpdateAddressRequestDTO(1, address1);
            UserUpdateAddressRequestDTO dto2 = new UserUpdateAddressRequestDTO(1, address2); // Mesmo ID, endereço com dados iguais
            UserUpdateAddressRequestDTO dto3 = new UserUpdateAddressRequestDTO(2, address1); // ID diferente
            UserUpdateAddressRequestDTO dto4 = new UserUpdateAddressRequestDTO(1, address3); // Endereço diferente

            assertThat(dto1).isEqualTo(dto2);
            assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());

            assertThat(dto1).isNotEqualTo(dto3);
            assertThat(dto1.hashCode()).isNotEqualTo(dto3.hashCode());

            assertThat(dto1).isNotEqualTo(dto4);
            assertThat(dto1.hashCode()).isNotEqualTo(dto4.hashCode());
        }
    }

    @Nested
    @DisplayName("Testes de Validação (Considerando validações em AddressRequestDTO)")
    class ValidationTests {

        @Test
        @DisplayName("Deve passar na validação para um UserUpdateAddressRequestDTO com endereço válido")
        void shouldPassValidationForValidDto() {
            UserUpdateAddressRequestDTO dto = new UserUpdateAddressRequestDTO(1, createValidAddressRequestDTO());
            Set<ConstraintViolation<UserUpdateAddressRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Não deve permitir que o AddressRequestDTO aninhado seja inválido")
        void shouldNotAllowInvalidNestedAddressRequestDTO() {
            AddressRequestDTO invalidAddress = new AddressRequestDTO("", "", null, -5, null);

            UserUpdateAddressRequestDTO dto = new UserUpdateAddressRequestDTO(1, invalidAddress);
            Set<ConstraintViolation<UserUpdateAddressRequestDTO>> violations = validator.validate(dto);

            assertThat(violations).hasSize(4); // state, city, street (por ser null), number (positive)
            assertThat(extractProperty("message").from(violations))
                    .containsExactlyInAnyOrder(
                            "state must be not null or empty",
                            "city must be not null or empty",
                            "street must be not null or empty",
                            "number must be positive"
                    );
        }

        @Test
        @DisplayName("Deve permitir UserUpdateAddressRequestDTO com endereço nulo")
        void shouldAllowNullAddressIfNoNotNullAnnotationOnParent() {
            UserUpdateAddressRequestDTO dto = new UserUpdateAddressRequestDTO(1, null);
            Set<ConstraintViolation<UserUpdateAddressRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).isEmpty();
        }
    }
}