package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user;

import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.address.AddressResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.shared.Role; // Confirme a importação do seu enum Role
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature; // Importar para desativar WRITE_DATES_AS_TIMESTAMPS
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserResponseDTOTest {

    private ObjectMapper objectMapper;

    private static final LocalDateTime FIXED_CREATED_AT = LocalDateTime.of(2024, 1, 10, 10, 0, 0);
    private static final LocalDateTime FIXED_UPDATED_AT = LocalDateTime.of(2024, 1, 15, 11, 30, 0);

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    private AddressResponseDTO createSampleAddressResponseDTO() {
        AddressResponseDTO address = new AddressResponseDTO();
        address.setId(1L);
        address.setState("SP");
        address.setCity("São Paulo");
        address.setStreet("Av. Paulista");
        address.setNumber(1578);
        address.setComplement("Andar 10");
        // Usando LocalDateTime fixos para garantir que AddressResponseDTOs sejam "iguais" se construídos com os mesmos dados
        address.setCreatedAt(FIXED_CREATED_AT.minusDays(10));
        address.setUpdatedAt(FIXED_UPDATED_AT.minusDays(5));
        return address;
    }

    @Nested
    @DisplayName("Testes de Criação e Acesso aos Campos (Lombok Builder e Getters)")
    class CreationAndAccessTests {

        @Test
        @DisplayName("Deve criar um UserResponseDTO usando o builder com todos os campos")
        void shouldCreateDtoUsingBuilderWithAllFields() {
            AddressResponseDTO address = createSampleAddressResponseDTO();

            UserResponseDTO dto = UserResponseDTO.builder()
                    .id("123456")
                    .name("Rafael")
                    .email("rafael@email.com")
                    .login("rafael.teste")
                    .role(Role.CUSTOMER)
                    .address(address)
                    .createdAt(FIXED_CREATED_AT)
                    .updatedAt(FIXED_UPDATED_AT)
                    .build();

            assertThat(dto.getId()).isEqualTo("123456");
            assertThat(dto.getName()).isEqualTo("Rafael");
            assertThat(dto.getEmail()).isEqualTo("rafael@email.com");
            assertThat(dto.getLogin()).isEqualTo("rafael.teste");
            assertThat(dto.getRole()).isEqualTo(Role.CUSTOMER);
            assertThat(dto.getAddress()).isEqualTo(address);
            assertThat(dto.getCreatedAt()).isEqualTo(FIXED_CREATED_AT);
            assertThat(dto.getUpdatedAt()).isEqualTo(FIXED_UPDATED_AT);
        }

        @Test
        @DisplayName("Deve criar um UserResponseDTO usando o builder com campos opcionais nulos")
        void shouldCreateDtoUsingBuilderWithOptionalNullFields() {
            UserResponseDTO dto = UserResponseDTO.builder()
                    .id("123456")
                    .name("Rafael")
                    .email("rafael@email.com")
                    .login("rafael.teste")
                    .role(Role.OWNER)
                    .createdAt(FIXED_CREATED_AT)
                    .build();

            assertThat(dto.getId()).isEqualTo("123456");
            assertThat(dto.getName()).isEqualTo("Rafael");
            assertThat(dto.getEmail()).isEqualTo("rafael@email.com");
            assertThat(dto.getLogin()).isEqualTo("rafael.teste");
            assertThat(dto.getRole()).isEqualTo(Role.OWNER);
            assertThat(dto.getAddress()).isNull();
            assertThat(dto.getCreatedAt()).isEqualTo(FIXED_CREATED_AT);
            assertThat(dto.getUpdatedAt()).isNull();
        }
    }

    @Nested
    @DisplayName("Testes de Serialização/Desserialização (Jackson)")
    class JsonTests {

        @Test
        @DisplayName("Deve serializar UserResponseDTO corretamente, ignorando campos nulos")
        void shouldSerializeDtoIgnoringNullFields() throws Exception {
            UserResponseDTO dto = UserResponseDTO.builder()
                    .id("1")
                    .name("Rafael")
                    .email("rafael@email.com")
                    .login("nulluser")
                    .role(Role.CUSTOMER)
                    .createdAt(FIXED_CREATED_AT)
                    .build();

            String json = objectMapper.writeValueAsString(dto);

            assertThat(json).contains("\"id\":\"1\"");
            assertThat(json).contains("\"name\":\"Rafael\"");
            assertThat(json).contains("\"email\":\"rafael@email.com\"");
            assertThat(json).contains("\"login\":\"nulluser\"");
            assertThat(json).contains("\"role\":\"CUSTOMER\"");
            assertThat(json).contains("\"createdAt\":[" + FIXED_CREATED_AT.getYear() + "," + FIXED_CREATED_AT.getMonthValue() + "," + FIXED_CREATED_AT.getDayOfMonth() + "," + FIXED_CREATED_AT.getHour() + "," + FIXED_CREATED_AT.getMinute() + "]");
            assertThat(json).doesNotContain("address");
            assertThat(json).doesNotContain("updatedAt");
        }

        @Test
        @DisplayName("Deve serializar UserResponseDTO com todos os campos (incluindo DTO aninhado)")
        void shouldSerializeDtoWithAllFields() throws Exception {
            AddressResponseDTO address = createSampleAddressResponseDTO();

            UserResponseDTO dto = UserResponseDTO.builder()
                    .id("user_abc")
                    .name("Usuário Completo")
                    .email("completo@example.com")
                    .login("fulluser")
                    .role(Role.OWNER)
                    .address(address)
                    .createdAt(FIXED_CREATED_AT)
                    .updatedAt(FIXED_UPDATED_AT)
                    .build();

            String json = objectMapper.writeValueAsString(dto);

            assertThat(json).contains("\"id\":\"user_abc\"");
            assertThat(json).contains("\"name\":\"Usuário Completo\"");
            assertThat(json).contains("\"email\":\"completo@example.com\"");
            assertThat(json).contains("\"login\":\"fulluser\"");
            assertThat(json).contains("\"role\":\"OWNER\"");
            assertThat(json).contains("\"createdAt\":[" + FIXED_CREATED_AT.getYear() + "," + FIXED_CREATED_AT.getMonthValue() + "," + FIXED_CREATED_AT.getDayOfMonth() + "," + FIXED_CREATED_AT.getHour() + "," + FIXED_CREATED_AT.getMinute() + "]");
            assertThat(json).contains("\"updatedAt\":[" + FIXED_UPDATED_AT.getYear() + "," + FIXED_UPDATED_AT.getMonthValue() + "," + FIXED_UPDATED_AT.getDayOfMonth() + "," + FIXED_UPDATED_AT.getHour() + "," + FIXED_UPDATED_AT.getMinute() + "]");
            assertThat(json).contains("\"address\":{\"id\":" + address.getId() + ",\"state\":\"" + address.getState() + "\",\"city\":\"" + address.getCity() + "\",\"street\":\"" + address.getStreet() + "\",\"number\":" + address.getNumber());
            assertThat(json).contains("\"createdAt\":[" + address.getCreatedAt().getYear() + "," + address.getCreatedAt().getMonthValue() + "," + address.getCreatedAt().getDayOfMonth() + "," + address.getCreatedAt().getHour() + "," + address.getCreatedAt().getMinute() + "]");
        }


        @Test
        @DisplayName("Deve desserializar UserResponseDTO corretamente, ignorando propriedades desconhecidas")
        void shouldDeserializeDtoIgnoringUnknownProperties() throws Exception {
            String json = "{\"id\":\"user_deser\",\"name\":\"User Desserializado\",\"email\":\"deser@example.com\",\"login\":\"userdeser\",\"role\":\"CUSTOMER\",\"updatedAt\":[2024,7,16,10,0,0],\"createdAt\":[2024,7,15,9,0,0],\"address\":{\"id\":10,\"state\":\"RJ\",\"city\":\"Rio\",\"street\":\"Rua X\",\"number\":1,\"complement\":\"Apto 101\",\"updatedAt\":[2024,7,16,10,0,0],\"createdAt\":[2024,7,15,9,0,0]},\"extraProperty\":\"someValue\"}";

            UserResponseDTO dto = objectMapper.readValue(json, UserResponseDTO.class);

            assertThat(dto.getId()).isEqualTo("user_deser");
            assertThat(dto.getName()).isEqualTo("User Desserializado");
            assertThat(dto.getEmail()).isEqualTo("deser@example.com");
            assertThat(dto.getLogin()).isEqualTo("userdeser");
            assertThat(dto.getRole()).isEqualTo(Role.CUSTOMER);
            assertThat(dto.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 7, 15, 9, 0, 0));
            assertThat(dto.getUpdatedAt()).isEqualTo(LocalDateTime.of(2024, 7, 16, 10, 0, 0));
            assertThat(dto.getAddress()).isNotNull();
            assertThat(dto.getAddress().getId()).isEqualTo(10L);
            assertThat(dto.getAddress().getCity()).isEqualTo("Rio");
        }

        @Test
        @DisplayName("Deve desserializar UserResponseDTO corretamente com campos nulos (ausentes no JSON)")
        void shouldDeserializeDtoWithNullFields() throws Exception {
            String json = "{\"id\":\"user_nulls\",\"name\":\"User Com Nulos\",\"email\":\"comnulos@example.com\",\"login\":\"comnulos\",\"role\":\"OWNER\",\"createdAt\":[2024,7,10,10,0,0]}"; // 'updatedAt' e 'address' ausentes no JSON

            UserResponseDTO dto = objectMapper.readValue(json, UserResponseDTO.class);

            assertThat(dto.getId()).isEqualTo("user_nulls");
            assertThat(dto.getName()).isEqualTo("User Com Nulos");
            assertThat(dto.getEmail()).isEqualTo("comnulos@example.com");
            assertThat(dto.getLogin()).isEqualTo("comnulos");
            assertThat(dto.getRole()).isEqualTo(Role.OWNER);
            assertThat(dto.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 7, 10, 10, 0, 0));
            assertThat(dto.getUpdatedAt()).isNull(); // Deve ser nulo
            assertThat(dto.getAddress()).isNull(); // Deve ser nulo
        }
    }
}