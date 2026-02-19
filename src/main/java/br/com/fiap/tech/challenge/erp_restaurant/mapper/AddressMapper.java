package br.com.fiap.tech.challenge.erp_restaurant.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.fiap.tech.challenge.erp_restaurant.domain.address.Address;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.address.AddressResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.AddressEntity;

@Mapper
public interface AddressMapper {

	AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);


	AddressResponseDTO objectToDto(Address a);
  
	AddressEntity toEntity(Address a);

	Address toDomain(AddressEntity a);
}
