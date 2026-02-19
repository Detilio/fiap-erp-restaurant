package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.dish;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CreateDishRequestDTOTest {

    @Nested
    @DisplayName("Testes de Criação e Acesso aos Campos (Records)")
    class RecordBehaviorTests {

        @Test
        @DisplayName("Deve criar um CreateDishRequestDTO com todos os campos preenchidos corretamente")
        void shouldCreateDtoWithAllFields() {
            String name = "Pizza Calabresa";
            String description = "Pizza tradicional com calabresa e cebola";
            BigDecimal price = new BigDecimal("45.99");
            Boolean onlyToGo = false;
            String photo = "http://example.com/pizza.jpg";

            CreateDishRequestDTO dto = new CreateDishRequestDTO(name, description, price, onlyToGo, photo);

            assertThat(dto.name()).isEqualTo(name);
            assertThat(dto.description()).isEqualTo(description);
            assertThat(dto.price()).isEqualTo(price);
            assertThat(dto.onlyToGo()).isEqualTo(onlyToGo);
            assertThat(dto.photo()).isEqualTo(photo);
        }

        @Test
        @DisplayName("Deve criar um CreateDishRequestDTO com campos opcionais nulos")
        void shouldCreateDtoWithNullOptionalFields() {
            String name = "Refrigerante";
            BigDecimal price = new BigDecimal("7.50");

            // Descrição e foto podem ser nulos
            CreateDishRequestDTO dto = new CreateDishRequestDTO(name, null, price, true, null);

            assertThat(dto.name()).isEqualTo(name);
            assertThat(dto.description()).isNull();
            assertThat(dto.price()).isEqualTo(price);
            assertThat(dto.onlyToGo()).isTrue();
            assertThat(dto.photo()).isNull();
        }

        @Test
        @DisplayName("Records devem ter equals e hashCode baseados em todos os componentes")
        void recordsShouldHaveCorrectEqualsAndHashCode() {
            BigDecimal price1 = new BigDecimal("25.00");
            BigDecimal price2 = new BigDecimal("25.00"); // Mesmo valor, diferente instância

            CreateDishRequestDTO dto1 = new CreateDishRequestDTO("Sanduíche", "Descrição", price1, true, "foto1.jpg");
            CreateDishRequestDTO dto2 = new CreateDishRequestDTO("Sanduíche", "Descrição", price2, true, "foto1.jpg");
            CreateDishRequestDTO dto3 = new CreateDishRequestDTO("Salada", "Descrição", price1, true, "foto1.jpg"); // Nome diferente

            assertThat(dto1).isEqualTo(dto2);
            assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
            assertThat(dto1).isNotEqualTo(dto3);
            assertThat(dto1.hashCode()).isNotEqualTo(dto3.hashCode());
        }
    }
}