package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.restaurant;

import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.address.AddressResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class RestaurantResponseDTOTest {

    private ObjectMapper objectMapper;
    private static final LocalDateTime FIXED_CREATED_AT = LocalDateTime.of(2024, 1, 1, 10, 0, 0);
    private static final LocalDateTime FIXED_UPDATED_AT = LocalDateTime.of(2024, 1, 1, 11, 0, 0);

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Para lidar com LocalTime e LocalDateTime
    }

    // Método auxiliar para criar um AddressResponseDTO para os testes
    private AddressResponseDTO createSampleAddressResponseDTO() {
        AddressResponseDTO address = new AddressResponseDTO();
        address.setId(1L);
        address.setState("SP");
        address.setCity("São Paulo");
        address.setStreet("Av. Paulista");
        address.setNumber(1578);
        address.setComplement("Andar 10");
        address.setCreatedAt(FIXED_CREATED_AT);
        address.setUpdatedAt(FIXED_UPDATED_AT);
        return address;
    }

    @Nested
    @DisplayName("Testes de Criação e Acesso aos Campos (Lombok)")
    class CreationAndAccessTests {

        @Test
        @DisplayName("Deve criar um RestaurantResponseDTO usando o builder com todos os campos")
        void shouldCreateDtoUsingBuilderWithAllFields() {
            AddressResponseDTO address = createSampleAddressResponseDTO();
            LocalTime openingAt = LocalTime.of(9, 0);
            LocalTime closingAt = LocalTime.of(23, 0);

            RestaurantResponseDTO dto = RestaurantResponseDTO.builder()
                    .id(1L)
                    .name("Restaurante do Zé")
                    .kitchenType("Brasileira")
                    .openingAt(openingAt)
                    .closingAt(closingAt)
                    .address(address)
                    .owner("João Silva")
                    .build();

            assertThat(dto.getId()).isEqualTo(1L);
            assertThat(dto.getName()).isEqualTo("Restaurante do Zé");
            assertThat(dto.getKitchenType()).isEqualTo("Brasileira");
            assertThat(dto.getOpeningAt()).isEqualTo(openingAt);
            assertThat(dto.getClosingAt()).isEqualTo(closingAt);
            assertThat(dto.getAddress()).isEqualTo(address);
            assertThat(dto.getOwner()).isEqualTo("João Silva");
        }

        @Test
        @DisplayName("Deve criar um RestaurantResponseDTO usando o builder com campos nulos opcionais")
        void shouldCreateDtoUsingBuilderWithOptionalNullFields() {
            LocalTime openingAt = LocalTime.of(8, 0);
            LocalTime closingAt = LocalTime.of(20, 0);

            RestaurantResponseDTO dto = RestaurantResponseDTO.builder()
                    .id(2L)
                    .name("Lanchonete Express")
                    .kitchenType("Fast Food")
                    .openingAt(openingAt)
                    .closingAt(closingAt)
                    // address e owner são nulos por padrão se não forem setados no builder
                    .build();

            assertThat(dto.getId()).isEqualTo(2L);
            assertThat(dto.getName()).isEqualTo("Lanchonete Express");
            assertThat(dto.getKitchenType()).isEqualTo("Fast Food");
            assertThat(dto.getOpeningAt()).isEqualTo(openingAt);
            assertThat(dto.getClosingAt()).isEqualTo(closingAt);
            assertThat(dto.getAddress()).isNull();
            assertThat(dto.getOwner()).isNull();
        }

        @Test
        @DisplayName("Deve permitir definir e obter valores usando setters")
        void shouldSetAndGetValuesUsingSetters() {
            RestaurantResponseDTO dto = RestaurantResponseDTO.builder()
                    .id(3L)
                    .name("Pizzaria do Bairro")
                    .kitchenType("Italiana")
                    .openingAt(LocalTime.of(18, 0))
                    .closingAt(LocalTime.of(23, 59))
                    .build();

            AddressResponseDTO address = new AddressResponseDTO();
            address.setStreet("Rua das Pizzas");
            dto.setAddress(address);
            dto.setOwner("Maria Clara");

            assertThat(dto.getId()).isEqualTo(3L);
            assertThat(dto.getName()).isEqualTo("Pizzaria do Bairro");
            assertThat(dto.getKitchenType()).isEqualTo("Italiana");
            assertThat(dto.getOpeningAt()).isEqualTo(LocalTime.of(18, 0));
            assertThat(dto.getClosingAt()).isEqualTo(LocalTime.of(23, 59));
            assertThat(dto.getAddress()).isEqualTo(address);
            assertThat(dto.getOwner()).isEqualTo("Maria Clara");
        }

        @Test
        @DisplayName("Lombok's equals and hashCode should work correctly")
        void lombokEqualsAndHashCodeShouldWorkCorrectly() {
            LocalTime time1 = LocalTime.of(10, 0);
            LocalTime time2 = LocalTime.of(10, 0); // Same value, different instance
            AddressResponseDTO address1 = createSampleAddressResponseDTO();
            AddressResponseDTO address2 = createSampleAddressResponseDTO(); // Same data, different instance

            RestaurantResponseDTO dto1 = RestaurantResponseDTO.builder()
                    .id(1L).name("A").kitchenType("X").openingAt(time1).closingAt(LocalTime.of(22,0))
                    .owner("Owner1").build();

            RestaurantResponseDTO dto2 = RestaurantResponseDTO.builder()
                    .id(1L).name("A").kitchenType("X").openingAt(time2).closingAt(LocalTime.of(22,0))
                    .owner("Owner1").build();

            RestaurantResponseDTO dto3 = RestaurantResponseDTO.builder()
                    .id(2L).name("A").kitchenType("X").openingAt(time1).closingAt(LocalTime.of(22,0))
                    .owner("Owner1").build(); // Different ID

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
        @DisplayName("Deve serializar RestaurantResponseDTO corretamente, ignorando campos nulos")
        void shouldSerializeDtoIgnoringNullFields() throws Exception {
            LocalTime openingAt = LocalTime.of(7, 30);
            LocalTime closingAt = LocalTime.of(19, 0);

            // Crie um DTO com 'address' e 'owner' nulos para testar JsonInclude.NON_NULL
            RestaurantResponseDTO dto = RestaurantResponseDTO.builder()
                    .id(1L)
                    .name("Cafeteria Sunrise")
                    .kitchenType("Café")
                    .openingAt(openingAt)
                    .closingAt(closingAt)
                    // address e owner não serão setados, serão nulos
                    .build();

            String json = objectMapper.writeValueAsString(dto);

            // Verifica se os campos não nulos estão presentes
            assertThat(json).contains("\"id\":1");
            assertThat(json).contains("\"name\":\"Cafeteria Sunrise\"");
            assertThat(json).contains("\"kitchenType\":\"Café\"");
            // LocalTime é serializado como array de números por padrão no Jackson com JavaTimeModule
            assertThat(json).contains("\"openingAt\":[7,30]");
            assertThat(json).contains("\"closingAt\":[19,0]");

            // Verifica se os campos nulos (address e owner) foram ignorados
            assertThat(json).doesNotContain("address");
            assertThat(json).doesNotContain("owner");
        }

        @Test
        @DisplayName("Deve serializar RestaurantResponseDTO com todos os campos (incluindo DTOs aninhadas)")
        void shouldSerializeDtoWithAllFields() throws Exception {
            AddressResponseDTO address = createSampleAddressResponseDTO();
            LocalTime openingAt = LocalTime.of(10, 0);
            LocalTime closingAt = LocalTime.of(22, 0);

            RestaurantResponseDTO dto = RestaurantResponseDTO.builder()
                    .id(2L)
                    .name("Comida Caseira")
                    .kitchenType("Caseira")
                    .openingAt(openingAt)
                    .closingAt(closingAt)
                    .address(address)
                    .owner("Ana Paula")
                    .build();

            String json = objectMapper.writeValueAsString(dto);

            assertThat(json).contains("\"id\":2");
            assertThat(json).contains("\"name\":\"Comida Caseira\"");
            assertThat(json).contains("\"kitchenType\":\"Caseira\"");
            assertThat(json).contains("\"openingAt\":[10,0]");
            assertThat(json).contains("\"closingAt\":[22,0]");
            assertThat(json).contains("\"owner\":\"Ana Paula\"");

            // Verifica a presença do objeto AddressResponseDTO (serializado como JSON)
            assertThat(json).contains("\"address\":{\"id\":1,\"state\":\"SP\",\"city\":\"São Paulo\",\"street\":\"Av. Paulista\",\"number\":1578,\"complement\":\"Andar 10\"");
            // Note: LocalDateTime no AddressResponseDTO será serializado como array [ano, mes, dia, hora, minuto, segundo, nano] por padrão
        }

        @Test
        @DisplayName("Deve desserializar RestaurantResponseDTO corretamente, ignorando propriedades desconhecidas")
        void shouldDeserializeDtoIgnoringUnknownProperties() throws Exception {
            String json = "{\"id\":3,\"name\":\"Restaurante Fictício\",\"kitchenType\":\"Variada\",\"openingAt\":[9,0],\"closingAt\":[21,0],\"address\":{\"id\":10,\"state\":\"RJ\",\"city\":\"Rio\",\"street\":\"Rua X\",\"number\":1,\"complement\":null,\"updatedAt\":[2024,7,16,10,0,0],\"createdAt\":[2024,7,15,9,0,0]},\"owner\":\"Carlos\",\"extraProperty\":\"someValue\"}";

            RestaurantResponseDTO dto = objectMapper.readValue(json, RestaurantResponseDTO.class);

            assertThat(dto.getId()).isEqualTo(3L);
            assertThat(dto.getName()).isEqualTo("Restaurante Fictício");
            assertThat(dto.getKitchenType()).isEqualTo("Variada");
            assertThat(dto.getOpeningAt()).isEqualTo(LocalTime.of(9, 0));
            assertThat(dto.getClosingAt()).isEqualTo(LocalTime.of(21, 0));
            assertThat(dto.getOwner()).isEqualTo("Carlos");
            assertThat(dto.getAddress()).isNotNull();
            assertThat(dto.getAddress().getId()).isEqualTo(10L);
            assertThat(dto.getAddress().getCity()).isEqualTo("Rio");
            // 'extraProperty' deve ser ignorado devido a @JsonIgnoreProperties(ignoreUnknown = true)
        }
    }
}