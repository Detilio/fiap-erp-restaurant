package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class AddressResponseDTOTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Para lidar com LocalDateTime
    }

    @Nested
    @DisplayName("Testes de Getters e Setters")
    class GettersAndSettersTests {

        @Test
        @DisplayName("Deve definir e obter o ID corretamente")
        void shouldSetAndGetId() {
            AddressResponseDTO dto = new AddressResponseDTO();
            Long id = 1L;
            dto.setId(id);
            assertThat(dto.getId()).isEqualTo(id);
        }

        @Test
        @DisplayName("Deve definir e obter o estado corretamente")
        void shouldSetAndGetState() {
            AddressResponseDTO dto = new AddressResponseDTO();
            String state = "SP";
            dto.setState(state);
            assertThat(dto.getState()).isEqualTo(state);
        }

        @Test
        @DisplayName("Deve definir e obter a cidade corretamente")
        void shouldSetAndGetCity() {
            AddressResponseDTO dto = new AddressResponseDTO();
            String city = "São Paulo";
            dto.setCity(city);
            assertThat(dto.getCity()).isEqualTo(city);
        }

        @Test
        @DisplayName("Deve definir e obter a rua corretamente")
        void shouldSetAndGetStreet() {
            AddressResponseDTO dto = new AddressResponseDTO();
            String street = "Rua Fictícia";
            dto.setStreet(street);
            assertThat(dto.getStreet()).isEqualTo(street);
        }

        @Test
        @DisplayName("Deve definir e obter o número corretamente")
        void shouldSetAndGetNumber() {
            AddressResponseDTO dto = new AddressResponseDTO();
            int number = 123;
            dto.setNumber(number);
            assertThat(dto.getNumber()).isEqualTo(number);
        }

        @Test
        @DisplayName("Deve definir e obter o complemento corretamente")
        void shouldSetAndGetComplement() {
            AddressResponseDTO dto = new AddressResponseDTO();
            String complement = "Apto 101";
            dto.setComplement(complement);
            assertThat(dto.getComplement()).isEqualTo(complement);
        }

        @Test
        @DisplayName("Deve definir e obter o updatedAt corretamente")
        void shouldSetAndGetUpdatedAt() {
            AddressResponseDTO dto = new AddressResponseDTO();
            LocalDateTime now = LocalDateTime.now();
            dto.setUpdatedAt(now);
            assertThat(dto.getUpdatedAt()).isEqualTo(now);
        }

        @Test
        @DisplayName("Deve definir e obter o createdAt corretamente")
        void shouldSetAndGetCreatedAt() {
            AddressResponseDTO dto = new AddressResponseDTO();
            LocalDateTime now = LocalDateTime.now();
            dto.setCreatedAt(now);
            assertThat(dto.getCreatedAt()).isEqualTo(now);
        }
    }

    @Nested
    @DisplayName("Testes de Serialização/Desserialização (Jackson)")
    class JsonTests {

        @Test
        @DisplayName("Deve serializar AddressResponseDTO corretamente, ignorando nulos")
        void shouldSerializeAddressResponseDTOIgnoringNulls() throws Exception {
            AddressResponseDTO dto = new AddressResponseDTO();
            dto.setId(1L);
            dto.setState("PR");
            dto.setCity("Curitiba");
            dto.setStreet("Rua da Maçã");
            dto.setNumber(456);

            String json = objectMapper.writeValueAsString(dto);

            assertThat(json).contains("\"id\":1");
            assertThat(json).contains("\"state\":\"PR\"");
            assertThat(json).contains("\"city\":\"Curitiba\"");
            assertThat(json).contains("\"street\":\"Rua da Maçã\"");
            assertThat(json).contains("\"number\":456");
            assertThat(json).doesNotContain("complement"); // Deve ignorar se nulo
            assertThat(json).doesNotContain("updatedAt"); // Deve ignorar se nulo
            assertThat(json).doesNotContain("createdAt"); // Deve ignorar se nulo
        }

        @Test
        @DisplayName("Deve desserializar AddressResponseDTO corretamente, ignorando propriedades desconhecidas")
        void shouldDeserializeAddressResponseDTOIgnoringUnknownProperties() throws Exception {
            String json = "{\"id\":2,\"state\":\"SC\",\"city\":\"Florianópolis\",\"street\":\"Rua do Sol\",\"number\":789,\"complement\":\"Casa\",\"updatedAt\":\"2023-01-01T10:00:00\",\"createdAt\":\"2023-01-01T09:00:00\",\"unknownProperty\":\"valorDesconhecido\"}";

            AddressResponseDTO dto = objectMapper.readValue(json, AddressResponseDTO.class);

            assertThat(dto.getId()).isEqualTo(2L);
            assertThat(dto.getState()).isEqualTo("SC");
            assertThat(dto.getCity()).isEqualTo("Florianópolis");
            assertThat(dto.getStreet()).isEqualTo("Rua do Sol");
            assertThat(dto.getNumber()).isEqualTo(789);
            assertThat(dto.getComplement()).isEqualTo("Casa");
            assertThat(dto.getUpdatedAt()).isEqualTo(LocalDateTime.of(2023, 1, 1, 10, 0, 0));
            assertThat(dto.getCreatedAt()).isEqualTo(LocalDateTime.of(2023, 1, 1, 9, 0, 0));
        }
    }
}