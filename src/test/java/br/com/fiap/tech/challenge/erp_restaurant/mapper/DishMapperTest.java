package br.com.fiap.tech.challenge.erp_restaurant.mapper;

import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.menu.Dish;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.DishEntity;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.dish.CreateDishRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class DishMapperTest {

    @Test
    @DisplayName("Deve mapear entidade para dominio e dominio para entidade")
    void domainToEntity_and_entityToDomain() throws MalformedURLException {
        Dish domain = Dish.builder()
                .id(1L)
                .name("Pizza")
                .description("Delicious")
                .price(BigDecimal.valueOf(20.5))
                .onlyToGo(false)
                .photo(new URL("http://example.com/photo.png"))
                .build();

        DishEntity entity = DishMapper.INSTANCE.domainToEntity(domain);
        assertNotNull(entity);
        assertEquals(domain.getName(), entity.getName());

        Dish domain2 = DishMapper.INSTANCE.entityToDomain(entity);
        assertNotNull(domain2);
        assertEquals(entity.getName(), domain2.getName());
    }

    @Test
    @DisplayName("Deve mapear DTO para domain")
    void dtoToDomain() throws MalformedURLException {
        CreateDishRequestDTO dto = new CreateDishRequestDTO(
                "Burger", "Tasty burger", BigDecimal.valueOf(15.0), false, "http://www.foto.com.br"
        );
        Dish domain = DishMapper.INSTANCE.dtoToDomain(dto);
        assertNotNull(domain);
        assertEquals(dto.name(), domain.getName());
    }
}
