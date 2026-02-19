package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.dish;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateDishRequestDTOTest {

    @Nested
    @DisplayName("Testes de Criação e Acesso aos Campos (Records)")
    class RecordBehaviorTests {

        @Test
        @DisplayName("Deve criar um UpdateDishRequestDTO com todos os campos preenchidos corretamente")
        void shouldCreateDtoWithAllFields() {
            int id = 1;
            String name = "Pizza Vegana";
            String description = "Pizza com queijo vegetal e legumes";
            BigDecimal price = new BigDecimal("55.00");
            Boolean onlyToGo = true;
            String photo = "http://example.com/pizza_vegana.jpg";

            UpdateDishRequestDTO dto = new UpdateDishRequestDTO(id, name, description, price, onlyToGo, photo);

            assertThat(dto.id()).isEqualTo(id);
            assertThat(dto.name()).isEqualTo(name);
            assertThat(dto.description()).isEqualTo(description);
            assertThat(dto.price()).isEqualTo(price);
            assertThat(dto.onlyToGo()).isEqualTo(onlyToGo);
            assertThat(dto.photo()).isEqualTo(photo);
        }

        @Test
        @DisplayName("Deve criar um UpdateDishRequestDTO com campos opcionais nulos")
        void shouldCreateDtoWithNullOptionalFields() {
            int id = 2;
            String name = "Água Mineral";
            BigDecimal price = new BigDecimal("5.00");

            // Descrição e foto podem ser nulos
            UpdateDishRequestDTO dto = new UpdateDishRequestDTO(id, name, null, price, false, null);

            assertThat(dto.id()).isEqualTo(id);
            assertThat(dto.name()).isEqualTo(name);
            assertThat(dto.description()).isNull();
            assertThat(dto.price()).isEqualTo(price);
            assertThat(dto.onlyToGo()).isFalse();
            assertThat(dto.photo()).isNull();
        }

        @Test
        @DisplayName("Records devem ter equals e hashCode baseados em todos os componentes")
        void recordsShouldHaveCorrectEqualsAndHashCode() {
            BigDecimal price1 = new BigDecimal("30.00");
            BigDecimal price2 = new BigDecimal("30.00"); // Mesmo valor, diferente instância

            UpdateDishRequestDTO dto1 = new UpdateDishRequestDTO(10, "Bolo de Cenoura", "Com cobertura", price1, false, "bolo.jpg");
            UpdateDishRequestDTO dto2 = new UpdateDishRequestDTO(10, "Bolo de Cenoura", "Com cobertura", price2, false, "bolo.jpg");
            UpdateDishRequestDTO dto3 = new UpdateDishRequestDTO(11, "Bolo de Cenoura", "Com cobertura", price1, false, "bolo.jpg"); // ID diferente

            assertThat(dto1).isEqualTo(dto2);
            assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
            assertThat(dto1).isNotEqualTo(dto3);
            assertThat(dto1.hashCode()).isNotEqualTo(dto3.hashCode());
        }
    }
}