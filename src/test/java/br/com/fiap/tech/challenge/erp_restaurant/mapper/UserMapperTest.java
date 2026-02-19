package br.com.fiap.tech.challenge.erp_restaurant.mapper;

import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.UserEntity;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user.UserRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.shared.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    @DisplayName("Deve mapear entidade para dominio e dominio para entidade")
    void toDomain_and_toEntity() {
        // Cria entidade
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setName("User Test");
        entity.setEmail("user@test.com");
        entity.setLogin("userlogin");
        entity.setPassword("pass123");
        entity.setRole(Role.CUSTOMER);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        // Mapeia entidade para domínio
        User domain = UserMapper.INSTANCE.toDomain(entity);
        assertNotNull(domain);
        assertEquals(entity.getEmail(), domain.getEmail());
        assertEquals(entity.getRole(), domain.getRole());

        // Mapeia domínio para entidade
        UserEntity entity2 = UserMapper.INSTANCE.toEntity(domain);
        assertNotNull(entity2);
        assertEquals(domain.getEmail(), entity2.getEmail());
        assertEquals(domain.getRole(), entity2.getRole());
    }

    @Test
    @DisplayName("Deve mapear DTO para domain")
    void dtoToDomain() {
        UserRequestDTO dto = new UserRequestDTO(
                1, "User Test", "user@test.com", "userlogin", "pass123", 1L
        );
        User domain = UserMapper.INSTANCE.dtoToDomain(dto);
        assertNotNull(domain);
        assertEquals(dto.email(), domain.getEmail());
        assertEquals(dto.id(), domain.getId());
    }
}
