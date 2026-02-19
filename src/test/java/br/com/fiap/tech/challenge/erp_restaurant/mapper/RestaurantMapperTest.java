package br.com.fiap.tech.challenge.erp_restaurant.mapper;

import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.Restaurant;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.RestaurantEntity;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.restaurant.CreateRestaurantRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantMapperTest {

    @Test
    @DisplayName("Deve mapear entidade para dominio e dominio para entidade")
    void domainToEntity_and_entityToDomain() {
        Restaurant domain = Restaurant.builder()
                .id(1L)
                .name("My Restaurant")
                .kitchenType("Italian")
                .openingAt(LocalTime.of(9, 0))
                .closingAt(LocalTime.of(22, 0))
                .build();

        RestaurantEntity entity = RestaurantMapper.INSTANCE.domainToEntity(domain);
        assertNotNull(entity);
        assertEquals(domain.getName(), entity.getName());

        Restaurant domain2 = RestaurantMapper.INSTANCE.entityToDomain(entity);
        assertNotNull(domain2);
        assertEquals(entity.getName(), domain2.getName());
    }

    @Test
    @DisplayName("Deve mapear DTO para domain")
    void dtoToDomain() {
        CreateRestaurantRequestDTO dto = new CreateRestaurantRequestDTO(
                "My Restaurant", "Italian", LocalTime.of(10, 0), LocalTime.of(10, 0), null
        );
        Restaurant domain = RestaurantMapper.INSTANCE.dtoToDomain(dto);
        assertNotNull(domain);
        assertEquals(dto.name(), domain.getName());
    }
}
