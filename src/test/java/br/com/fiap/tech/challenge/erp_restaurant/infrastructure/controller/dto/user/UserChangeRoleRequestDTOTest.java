package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user;

import br.com.fiap.tech.challenge.erp_restaurant.shared.Role; // Certifique-se de que esta importação está correta
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserChangeRoleRequestDTOTest {

    @Nested
    @DisplayName("Testes de Criação e Acesso aos Campos (Records)")
    class RecordBehaviorTests {

        @Test
        @DisplayName("Deve criar um UserChangeRoleRequestDTO com Role.CUSTOMER")
        void shouldCreateDtoWithCustomerRole() {
            int id = 123;
            Role role = Role.CUSTOMER;

            UserChangeRoleRequestDTO dto = new UserChangeRoleRequestDTO(id, role);

            assertThat(dto.id()).isEqualTo(id);
            assertThat(dto.role()).isEqualTo(role);
        }

        @Test
        @DisplayName("Deve criar um UserChangeRoleRequestDTO com Role.OWNER")
        void shouldCreateDtoWithOwnerRole() {
            int id = 456;
            Role role = Role.OWNER;

            UserChangeRoleRequestDTO dto = new UserChangeRoleRequestDTO(id, role);

            assertThat(dto.id()).isEqualTo(id);
            assertThat(dto.role()).isEqualTo(role);
        }

        @Test
        @DisplayName("Records devem ter equals e hashCode baseados em todos os componentes")
        void recordsShouldHaveCorrectEqualsAndHashCode() {
            UserChangeRoleRequestDTO dto1 = new UserChangeRoleRequestDTO(1, Role.CUSTOMER);
            UserChangeRoleRequestDTO dto2 = new UserChangeRoleRequestDTO(1, Role.CUSTOMER);
            UserChangeRoleRequestDTO dto3 = new UserChangeRoleRequestDTO(2, Role.CUSTOMER); // ID diferente
            UserChangeRoleRequestDTO dto4 = new UserChangeRoleRequestDTO(1, Role.OWNER); // Role diferente

            assertThat(dto1).isEqualTo(dto2);
            assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());

            assertThat(dto1).isNotEqualTo(dto3);
            assertThat(dto1.hashCode()).isNotEqualTo(dto3.hashCode());

            assertThat(dto1).isNotEqualTo(dto4);
            assertThat(dto1.hashCode()).isNotEqualTo(dto4.hashCode());
        }
    }
}