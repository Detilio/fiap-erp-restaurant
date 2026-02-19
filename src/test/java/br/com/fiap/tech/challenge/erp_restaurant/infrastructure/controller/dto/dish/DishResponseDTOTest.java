package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.dish;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class DishResponseDTOTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Nested
    @DisplayName("Testes de Criação e Acesso aos Campos (Records)")
    class RecordBehaviorTests {

        @Test
        @DisplayName("Deve criar um DishResponseDTO com todos os campos preenchidos corretamente")
        void shouldCreateDtoWithAllFields() {
            Long id = 1L;
            String name = "Hambúrguer Clássico";
            String description = "Pão, carne, queijo, alface, tomate";
            BigDecimal price = new BigDecimal("35.50");
            Boolean onlyToGo = true;
            String photo = "http://example.com/burger.jpg";
            String restaurant = "BurgerMania";

            DishResponseDTO dto = new DishResponseDTO(id, name, description, price, onlyToGo, photo, restaurant);

            assertThat(dto.id()).isEqualTo(id);
            assertThat(dto.name()).isEqualTo(name);
            assertThat(dto.description()).isEqualTo(description);
            assertThat(dto.price()).isEqualTo(price);
            assertThat(dto.onlyToGo()).isEqualTo(onlyToGo);
            assertThat(dto.photo()).isEqualTo(photo);
            assertThat(dto.restaurant()).isEqualTo(restaurant);
        }

        @Test
        @DisplayName("Deve criar um DishResponseDTO com campos opcionais nulos")
        void shouldCreateDtoWithNullOptionalFields() {
            Long id = 2L;
            String name = "Batata Frita";
            BigDecimal price = new BigDecimal("12.00");
            Boolean onlyToGo = false;
            String restaurant = "SnackHouse";

            DishResponseDTO dto = new DishResponseDTO(id, name, null, price, onlyToGo, null, restaurant);

            assertThat(dto.id()).isEqualTo(id);
            assertThat(dto.name()).isEqualTo(name);
            assertThat(dto.description()).isNull();
            assertThat(dto.price()).isEqualTo(price);
            assertThat(dto.onlyToGo()).isFalse();
            assertThat(dto.photo()).isNull();
            assertThat(dto.restaurant()).isEqualTo(restaurant);
        }

        @Test
        @DisplayName("Records devem ter equals e hashCode baseados em todos os componentes")
        void recordsShouldHaveCorrectEqualsAndHashCode() {
            BigDecimal price1 = new BigDecimal("50.00");
            BigDecimal price2 = new BigDecimal("50.00"); // Mesmo valor, diferente instância

            DishResponseDTO dto1 = new DishResponseDTO(3L, "Salmão Grelhado", "Com legumes", price1, false, "salmao.jpg", "Seafood Place");
            DishResponseDTO dto2 = new DishResponseDTO(3L, "Salmão Grelhado", "Com legumes", price2, false, "salmao.jpg", "Seafood Place");
            DishResponseDTO dto3 = new DishResponseDTO(4L, "Salmão Grelhado", "Com legumes", price1, false, "salmao.jpg", "Seafood Place"); // ID diferente

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
        @DisplayName("Deve serializar DishResponseDTO corretamente")
        void shouldSerializeDishResponseDTO() throws Exception {
            DishResponseDTO dto = new DishResponseDTO(
                    5L, "Suco de Laranja", null, new BigDecimal("8.00"), true, null, "Fresh Juices");

            String json = objectMapper.writeValueAsString(dto);

            assertThat(json).contains("\"id\":5");
            assertThat(json).contains("\"name\":\"Suco de Laranja\"");
            assertThat(json).contains("\"price\":8.00");
            assertThat(json).contains("\"onlyToGo\":true");
            assertThat(json).contains("\"restaurant\":\"Fresh Juices\"");
            assertThat(json).contains("description");
            assertThat(json).contains("photo");
        }
    }
}