package br.com.fiap.tech.challenge.erp_restaurant.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.menu.Dish;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.dish.CreateDishRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.dish.DishResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.dish.UpdateDishRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.DishEntity;


@Mapper
public interface DishMapper {
 
	DishMapper INSTANCE = Mappers.getMapper(DishMapper.class);
 
	Dish dtoToDomain(CreateDishRequestDTO dto);
	
	Dish dtoToDomain(UpdateDishRequestDTO dto);

	@Mapping(target = "restaurant", source = "restaurant.name")
	DishResponseDTO domainToDTO(Dish a);

	@Mapping(target = "restaurant", source = "restaurant")
	DishEntity domainToEntity(Dish dish); 

	Dish entityToDomain(DishEntity entity);
}
