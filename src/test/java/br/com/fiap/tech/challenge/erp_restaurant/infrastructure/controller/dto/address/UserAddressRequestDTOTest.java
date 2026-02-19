package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserAddressRequestDTOTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Nested
    @DisplayName("Testes de Criação e Acesso aos Campos (Records)")
    class RecordBehaviorTests {

        @Test
        @DisplayName("Deve criar um UserAddressRequestDTO com todos os campos preenchidos corretamente")
        void shouldCreateUserAddressRequestDTOWithAllFields() {
            Long id = 101L;
            String state = "RJ";
            String city = "Niterói";
            String street = "Rua das Pedras";
            int number = 50;
            String complement = "Cobertura";
            Long userId = 1001L;

            UserAddressRequestDTO dto = new UserAddressRequestDTO(
                    id, state, city, street, number, complement, userId);

            assertThat(dto.id()).isEqualTo(id);
            assertThat(dto.state()).isEqualTo(state);
            assertThat(dto.city()).isEqualTo(city);
            assertThat(dto.street()).isEqualTo(street);
            assertThat(dto.number()).isEqualTo(number);
            assertThat(dto.complement()).isEqualTo(complement);
            assertThat(dto.userId()).isEqualTo(userId);
        }

        @Test
        @DisplayName("Deve criar um UserAddressRequestDTO com complemento nulo")
        void shouldCreateUserAddressRequestDTOWithNullComplement() {
            Long id = 102L;
            String state = "DF";
            String city = "Brasília";
            String street = "Setor Sul";
            int number = 10;
            String complement = null; // Complemento nulo
            Long userId = 1002L;

            UserAddressRequestDTO dto = new UserAddressRequestDTO(
                    id, state, city, street, number, complement, userId);

            assertThat(dto.id()).isEqualTo(id);
            assertThat(dto.complement()).isNull();
            assertThat(dto.userId()).isEqualTo(userId);
        }

        @Test
        @DisplayName("Records devem ter equals e hashCode baseados em todos os componentes")
        void recordsShouldHaveCorrectEqualsAndHashCode() {
            UserAddressRequestDTO dto1 = new UserAddressRequestDTO(1L, "SP", "São Paulo", "Rua A", 1, "A", 100L);
            UserAddressRequestDTO dto2 = new UserAddressRequestDTO(1L, "SP", "São Paulo", "Rua A", 1, "A", 100L);
            UserAddressRequestDTO dto3 = new UserAddressRequestDTO(2L, "SP", "São Paulo", "Rua A", 1, "A", 100L); // Diferente ID

            assertThat(dto1).isEqualTo(dto2);
            assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
            assertThat(dto1).isNotEqualTo(dto3);
            assertThat(dto1.hashCode()).isNotEqualTo(dto3.hashCode());
        }
    }

    @Nested
    @DisplayName("Testes de Serialização/Desserialização (Jackson)")
    class JsonTests {

        @Test
        @DisplayName("Deve serializar UserAddressRequestDTO corretamente, ignorando campos nulos")
        void shouldSerializeUserAddressRequestDTOIgnoringNulls() throws Exception {
            UserAddressRequestDTO dto = new UserAddressRequestDTO(
                    103L, "ES", "Vitória", "Avenida Beira Mar", 60, null, 1003L);

            String json = objectMapper.writeValueAsString(dto);

            // Verifica se os campos não nulos estão presentes
            assertThat(json).contains("\"id\":103");
            assertThat(json).contains("\"state\":\"ES\"");
            assertThat(json).contains("\"city\":\"Vitória\"");
            assertThat(json).contains("\"street\":\"Avenida Beira Mar\"");
            assertThat(json).contains("\"number\":60");
            assertThat(json).contains("\"userId\":1003");

            // Verifica se o campo nulo (complement) foi ignorado
            assertThat(json).doesNotContain("complement");
        }

        @Test
        @DisplayName("Deve desserializar UserAddressRequestDTO corretamente, ignorando propriedades desconhecidas")
        void shouldDeserializeUserAddressRequestDTOIgnoringUnknownProperties() throws Exception {
            String json = "{\"id\":104,\"state\":\"MG\",\"city\":\"Uberlândia\",\"street\":\"Rua Central\",\"number\":120,\"complement\":\"Fundos\",\"userId\":1004,\"randomField\":\"abc\"}";

            UserAddressRequestDTO dto = objectMapper.readValue(json, UserAddressRequestDTO.class);

            assertThat(dto.id()).isEqualTo(104L);
            assertThat(dto.state()).isEqualTo("MG");
            assertThat(dto.city()).isEqualTo("Uberlândia");
            assertThat(dto.street()).isEqualTo("Rua Central");
            assertThat(dto.number()).isEqualTo(120);
            assertThat(dto.complement()).isEqualTo("Fundos");
            assertThat(dto.userId()).isEqualTo(1004L);
            // 'randomField' deve ser ignorado devido a @JsonIgnoreProperties(ignoreUnknown = true)
        }
    }
}