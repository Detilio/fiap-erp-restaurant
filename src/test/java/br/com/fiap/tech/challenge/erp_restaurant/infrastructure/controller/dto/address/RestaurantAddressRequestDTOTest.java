package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RestaurantAddressRequestDTOTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        // Não é necessário JavaTimeModule aqui, pois não há campos LocalDateTime
    }

    @Nested
    @DisplayName("Testes de Criação e Acesso aos Campos (Records)")
    class RecordBehaviorTests {

        @Test
        @DisplayName("Deve criar um RestaurantAddressRequestDTO com todos os campos preenchidos corretamente")
        void shouldCreateRestaurantAddressRequestDTOWithAllFields() {
            Long id = 1L;
            String state = "MG";
            String city = "Belo Horizonte";
            String street = "Rua das Flores";
            int number = 100;
            String complement = "Loja 01";
            Long restaurantId = 10L;

            RestaurantAddressRequestDTO dto = new RestaurantAddressRequestDTO(
                    id, state, city, street, number, complement, restaurantId);

            assertThat(dto.id()).isEqualTo(id);
            assertThat(dto.state()).isEqualTo(state);
            assertThat(dto.city()).isEqualTo(city);
            assertThat(dto.street()).isEqualTo(street);
            assertThat(dto.number()).isEqualTo(number);
            assertThat(dto.complement()).isEqualTo(complement);
            assertThat(dto.restaurantId()).isEqualTo(restaurantId);
        }

        @Test
        @DisplayName("Deve criar um RestaurantAddressRequestDTO com complemento nulo")
        void shouldCreateRestaurantAddressRequestDTOWithNullComplement() {
            Long id = 2L;
            String state = "RS";
            String city = "Porto Alegre";
            String street = "Avenida Principal";
            int number = 200;
            String complement = null; // Complemento nulo
            Long restaurantId = 20L;

            RestaurantAddressRequestDTO dto = new RestaurantAddressRequestDTO(
                    id, state, city, street, number, complement, restaurantId);

            assertThat(dto.id()).isEqualTo(id);
            assertThat(dto.complement()).isNull();
            assertThat(dto.restaurantId()).isEqualTo(restaurantId);
        }

        @Test
        @DisplayName("Records devem ter equals e hashCode baseados em todos os componentes")
        void recordsShouldHaveCorrectEqualsAndHashCode() {
            RestaurantAddressRequestDTO dto1 = new RestaurantAddressRequestDTO(1L, "SP", "São Paulo", "Rua A", 1, "A", 10L);
            RestaurantAddressRequestDTO dto2 = new RestaurantAddressRequestDTO(1L, "SP", "São Paulo", "Rua A", 1, "A", 10L);
            RestaurantAddressRequestDTO dto3 = new RestaurantAddressRequestDTO(2L, "SP", "São Paulo", "Rua A", 1, "A", 10L);

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
        @DisplayName("Deve serializar RestaurantAddressRequestDTO corretamente, ignorando campos nulos")
        void shouldSerializeRestaurantAddressRequestDTOIgnoringNulls() throws Exception {
            // Criando DTO com complemento nulo para testar JsonInclude.NON_NULL
            RestaurantAddressRequestDTO dto = new RestaurantAddressRequestDTO(
                    3L, "RJ", "Rio de Janeiro", "Rua da Praia", 300, null, 30L);

            String json = objectMapper.writeValueAsString(dto);

            // Verifica se os campos não nulos estão presentes
            assertThat(json).contains("\"id\":3");
            assertThat(json).contains("\"state\":\"RJ\"");
            assertThat(json).contains("\"city\":\"Rio de Janeiro\"");
            assertThat(json).contains("\"street\":\"Rua da Praia\"");
            assertThat(json).contains("\"number\":300");
            assertThat(json).contains("\"restaurantId\":30");

            // Verifica se o campo nulo (complement) foi ignorado
            assertThat(json).doesNotContain("complement");
        }

        @Test
        @DisplayName("Deve desserializar RestaurantAddressRequestDTO corretamente, ignorando propriedades desconhecidas")
        void shouldDeserializeRestaurantAddressRequestDTOIgnoringUnknownProperties() throws Exception {
            String json = "{\"id\":4,\"state\":\"BA\",\"city\":\"Salvador\",\"street\":\"Ladeira do Carmo\",\"number\":400,\"complement\":\"Apt 40\",\"restaurantId\":40,\"extraField\":\"someValue\"}";

            RestaurantAddressRequestDTO dto = objectMapper.readValue(json, RestaurantAddressRequestDTO.class);

            assertThat(dto.id()).isEqualTo(4L);
            assertThat(dto.state()).isEqualTo("BA");
            assertThat(dto.city()).isEqualTo("Salvador");
            assertThat(dto.street()).isEqualTo("Ladeira do Carmo");
            assertThat(dto.number()).isEqualTo(400);
            assertThat(dto.complement()).isEqualTo("Apt 40");
            assertThat(dto.restaurantId()).isEqualTo(40L);
        }
    }
}