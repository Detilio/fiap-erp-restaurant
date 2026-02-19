package br.com.fiap.tech.challenge.erp_restaurant.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.auth.AuthRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user.UserChangeRoleRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user.UserRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user.UserResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user.UserUpdateAddressRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.UserEntity;

@Mapper
public interface UserMapper { 

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @ValueMapping(target = "CUSTOMER", source = "CUSTOMER")
    @ValueMapping(target = "OWNER", source = "OWNER")
    @Mapping(target = "address.id", source = "address")
    User dtoToDomain(UserRequestDTO u);

    User authDtoToDomain(AuthRequestDTO a);

    UserResponseDTO domainToDTO(User a);

    User toDomain(UserEntity u);

    UserEntity toEntity(User a);
    
    UserChangeRoleRequestDTO domainToChangeRoleDTO(User a);
    
    User dtoToDomain(UserChangeRoleRequestDTO u);

	@Mapping(target = "id", source = "id")
	@Mapping(target = "address", source = "address")
	User dtoToDomain(UserUpdateAddressRequestDTO userDto);
}
