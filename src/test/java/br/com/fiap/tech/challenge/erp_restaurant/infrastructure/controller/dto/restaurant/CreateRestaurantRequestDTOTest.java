package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.restaurant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CreateRestaurantRequestDTOTest {

    private Validator validator;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Configura o Validator para testar as anotações de validação
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        // Configura o ObjectMapper para testar serialização/desserialização
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Para lidar com LocalTime
    }

    @Nested
    @DisplayName("Testes de Criação e Acesso aos Campos (Records)")
    class RecordBehaviorTests {

        @Test
        @DisplayName("Deve criar um CreateRestaurantRequestDTO com todos os campos preenchidos corretamente")
        void shouldCreateDtoWithAllFields() {
            String name = "Restaurante Teste";
            String kitchenType = "Brasileira";
            LocalTime openingAt = LocalTime.of(9, 0);
            LocalTime closingAt = LocalTime.of(22, 0);
            Long ownerId = 1L;

            CreateRestaurantRequestDTO dto = new CreateRestaurantRequestDTO(name, kitchenType, openingAt, closingAt, ownerId);

            assertThat(dto.name()).isEqualTo(name);
            assertThat(dto.kitchenType()).isEqualTo(kitchenType);
            assertThat(dto.openingAt()).isEqualTo(openingAt);
            assertThat(dto.closingAt()).isEqualTo(closingAt);
            assertThat(dto.ownerId()).isEqualTo(ownerId);
        }

        @Test
        @DisplayName("Records devem ter equals e hashCode baseados em todos os componentes")
        void recordsShouldHaveCorrectEqualsAndHashCode() {
            LocalTime time1 = LocalTime.of(10, 0);
            LocalTime time2 = LocalTime.of(10, 0);

            CreateRestaurantRequestDTO dto1 = new CreateRestaurantRequestDTO("Nome A", "Tipo X", time1, LocalTime.of(18,0), 1L);
            CreateRestaurantRequestDTO dto2 = new CreateRestaurantRequestDTO("Nome A", "Tipo X", time2, LocalTime.of(18,0), 1L);
            CreateRestaurantRequestDTO dto3 = new CreateRestaurantRequestDTO("Nome B", "Tipo X", time1, LocalTime.of(18,0), 1L); // Nome diferente

            assertThat(dto1).isEqualTo(dto2);
            assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
            assertThat(dto1).isNotEqualTo(dto3);
            assertThat(dto1.hashCode()).isNotEqualTo(dto3.hashCode());
        }
    }

    @Nested
    @DisplayName("Testes de Validação do CreateRestaurantRequestDTO")
    class ValidationTests {

        @Test
        @DisplayName("Deve permitir um CreateRestaurantRequestDTO válido")
        void shouldAllowValidDto() {
            CreateRestaurantRequestDTO dto = new CreateRestaurantRequestDTO(
                    "Restaurante Válido", "Italiana", LocalTime.of(10, 0), LocalTime.of(23, 0), 10L);
            Set<ConstraintViolation<CreateRestaurantRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Não deve permitir nome nulo ou vazio")
        void shouldNotAllowNullOrEmptyName() {
            CreateRestaurantRequestDTO dtoNull = new CreateRestaurantRequestDTO(
                    null, "Japonesa", LocalTime.of(10, 0), LocalTime.of(23, 0), 20L);
            Set<ConstraintViolation<CreateRestaurantRequestDTO>> violationsNull = validator.validate(dtoNull);
            assertThat(violationsNull).hasSize(1);
            assertThat(violationsNull).extracting(ConstraintViolation::getMessage)
                    .contains("name must be not null or empty");

            CreateRestaurantRequestDTO dtoEmpty = new CreateRestaurantRequestDTO(
                    "", "Japonesa", LocalTime.of(10, 0), LocalTime.of(23, 0), 20L);
            Set<ConstraintViolation<CreateRestaurantRequestDTO>> violationsEmpty = validator.validate(dtoEmpty);
            assertThat(violationsEmpty).hasSize(1);
            assertThat(violationsEmpty).extracting(ConstraintViolation::getMessage)
                    .contains("name must be not null or empty");
        }

        @Test
        @DisplayName("Não deve permitir tipo de cozinha nulo ou vazio")
        void shouldNotAllowNullOrEmptyKitchenType() {
            CreateRestaurantRequestDTO dtoNull = new CreateRestaurantRequestDTO(
                    "Nome", null, LocalTime.of(10, 0), LocalTime.of(23, 0), 30L);
            Set<ConstraintViolation<CreateRestaurantRequestDTO>> violationsNull = validator.validate(dtoNull);
            assertThat(violationsNull).hasSize(1);
            assertThat(violationsNull).extracting(ConstraintViolation::getMessage)
                    .contains("kitchen type must be not null or empty");

            CreateRestaurantRequestDTO dtoEmpty = new CreateRestaurantRequestDTO(
                    "Nome", "", LocalTime.of(10, 0), LocalTime.of(23, 0), 30L);
            Set<ConstraintViolation<CreateRestaurantRequestDTO>> violationsEmpty = validator.validate(dtoEmpty);
            assertThat(violationsEmpty).hasSize(1);
            assertThat(violationsEmpty).extracting(ConstraintViolation::getMessage)
                    .contains("kitchen type must be not null or empty");
        }

        @Test
        @DisplayName("Não deve permitir openingAt nulo")
        void shouldNotAllowNullOpeningAt() {
            CreateRestaurantRequestDTO dto = new CreateRestaurantRequestDTO(
                    "Nome", "Tipo", null, LocalTime.of(23, 0), 40L);
            Set<ConstraintViolation<CreateRestaurantRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).hasSize(1);
            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("openningAt must be not null or empty");
        }

        @Test
        @DisplayName("Não deve permitir closingAt nulo")
        void shouldNotAllowNullClosingAt() {
            CreateRestaurantRequestDTO dto = new CreateRestaurantRequestDTO(
                    "Nome", "Tipo", LocalTime.of(10, 0), null, 50L);
            Set<ConstraintViolation<CreateRestaurantRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).hasSize(1);
            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("closingAt must be not null or empty");
        }

        @Test
        @DisplayName("Não deve permitir ownerId nulo")
        void shouldNotAllowNullOwnerId() {
            CreateRestaurantRequestDTO dto = new CreateRestaurantRequestDTO(
                    "Nome", "Tipo", LocalTime.of(10, 0), LocalTime.of(23, 0), null);
            Set<ConstraintViolation<CreateRestaurantRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).hasSize(1);
            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .contains("ownerId must be not null or empty");
        }

        @Test
        @DisplayName("Deve capturar múltiplas violações")
        void shouldCaptureMultipleViolations() {
            CreateRestaurantRequestDTO dto = new CreateRestaurantRequestDTO(
                    "", null, LocalTime.of(20,0), LocalTime.of(20,0), null); // 3 violações esperadas
            Set<ConstraintViolation<CreateRestaurantRequestDTO>> violations = validator.validate(dto);
            assertThat(violations).hasSize(3);
            assertThat(violations).extracting(ConstraintViolation::getMessage)
                    .containsExactlyInAnyOrder(
                            "name must be not null or empty",
                            "kitchen type must be not null or empty",
                            "ownerId must be not null or empty"
                    );
        }
    }

    @Nested
    @DisplayName("Testes de Serialização/Desserialização (Jackson)")
    class JsonTests {

        @Test
        @DisplayName("Deve serializar CreateRestaurantRequestDTO corretamente, ignorando campos nulos")
        void shouldSerializeDtoIgnoringNulls() throws Exception {
            // Este DTO não possui campos que seriam nulos se válidos, pois todos são @NotEmpty ou @NotNull.
            // Para demonstrar o @JsonInclude(Include.NON_NULL), teríamos que ter um campo opcional.
            // No entanto, podemos testar que ele serializa corretamente.
            CreateRestaurantRequestDTO dto = new CreateRestaurantRequestDTO(
                    "Restaurante Completo", "Mexicana", LocalTime.parse("11:00:00"), LocalTime.parse("23:30:00"), 100L);

            String json = objectMapper.writeValueAsString(dto);

            assertThat(json).contains("\"name\":\"Restaurante Completo\"");
            assertThat(json).contains("\"kitchenType\":\"Mexicana\"");
            assertThat(json).contains("\"openingAt\":[11,0]");
            assertThat(json).contains("\"closingAt\":[23,30]");
            assertThat(json).contains("\"ownerId\":100");
        }

        @Test
        @DisplayName("Deve desserializar CreateRestaurantRequestDTO corretamente, ignorando propriedades desconhecidas")
        void shouldDeserializeDtoIgnoringUnknownProperties() throws Exception {
            String json = "{\"name\":\"Restaurante JSON\",\"kitchenType\":\"Asiática\",\"openingAt\":\"12:00:00\",\"closingAt\":\"21:00:00\",\"ownerId\":200,\"extraField\":\"someValue\"}";

            CreateRestaurantRequestDTO dto = objectMapper.readValue(json, CreateRestaurantRequestDTO.class);

            assertThat(dto.name()).isEqualTo("Restaurante JSON");
            assertThat(dto.kitchenType()).isEqualTo("Asiática");
            assertThat(dto.openingAt()).isEqualTo(LocalTime.of(12, 0, 0));
            assertThat(dto.closingAt()).isEqualTo(LocalTime.of(21, 0, 0));
            assertThat(dto.ownerId()).isEqualTo(200L);
        }
    }
}