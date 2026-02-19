package br.com.fiap.tech.challenge.erp_restaurant.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.Restaurant;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.restaurant.CreateRestaurantRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.restaurant.RestaurantResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.RestaurantEntity;

@Mapper
public interface RestaurantMapper {

	RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

	@Mapping(target = "owner.id" , source = "ownerId")
	Restaurant dtoToDomain(CreateRestaurantRequestDTO dto); 
 
	@Mapping(target = "owner" , source = "owner.name")
	RestaurantResponseDTO domainToDTO(Restaurant domain); 

	RestaurantEntity domainToEntity(Restaurant restaurant);  

	Restaurant entityToDomain(RestaurantEntity r); 

}
