package br.com.fiap.tech.challenge.erp_restaurant.mapper;

import br.com.fiap.tech.challenge.erp_restaurant.dto.AddressDTO;
import br.com.fiap.tech.challenge.erp_restaurant.dto.UserRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.dto.UserResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.entity.UserEntity;
import br.com.fiap.tech.challenge.erp_restaurant.model.Address;
import br.com.fiap.tech.challenge.erp_restaurant.model.User;

import java.util.Date;
import java.util.stream.Collectors;

public class UserMapper {
    // Metodo para converter AddressDTO para Address Model
    public Address convertToAddressModel(AddressDTO addressDTO) {
        Address address = new Address();
        address.setId(addressDTO.getId());
        address.setStreet(addressDTO.getStreet());
        address.setNumber(addressDTO.getNumber());
        address.setDistrict(addressDTO.getDistrict());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setZipCode(addressDTO.getZipCode());
        return address;
    }

    // Converte Address Model para AddressDTO
    public AddressDTO convertToAddressDTO(Address address) {
        return new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getNumber(),
                address.getDistrict(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                null // User ID não é necessário no DTO de resposta aqui
        );
    }

    // Converte UserRequestDTO para User Model
    public User convertToModel(UserRequestDTO requestDTO) {
        return new User(
                null,
                requestDTO.getName(),
                requestDTO.getEmail(),
                requestDTO.getLogin(),
                new Date(),
                null, // Endereço removido do UserRequestDTO
                requestDTO.getPassword(),
                requestDTO.getType(),
                null
        );
    }

    // Converte User Model para UserEntity
    public UserEntity convertToEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setLogin(user.getLogin());
        entity.setDateGeneration(user.getDateGeneration());
        entity.setDateChange(user.getDateChange());
        entity.setPassword(user.getPassword());
        entity.setType(user.getType());
        return entity;
    }

    // Converte UserEntity para User Model
    public User convertToModel(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getLogin(),
                entity.getDateChange(),
                entity.getDateGeneration(),
                entity.getPassword(), // Considerar não retornar a senha em cenários de leitura
                entity.getType(),
                entity.getDeliveryAddresses() != null ? entity.getDeliveryAddresses().stream()
                        .map(addressEntity -> convertToAddressModel(convertToAddressDTO(new Address(
                                addressEntity.getId(),
                                addressEntity.getStreet(),
                                addressEntity.getNumber(),
                                addressEntity.getDistrict(),
                                addressEntity.getCity(),
                                addressEntity.getState(),
                                addressEntity.getZipCode()
                        ))))
                        .collect(Collectors.toList()) : null
        );
    }

    // Converte User Model para UserResponseDTO
    public UserResponseDTO convertToResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getDateChange(),
                user.getDateGeneration(),
                user.getType(),
                user.getDeliveryAddresses() != null ? user.getDeliveryAddresses().stream()
                        .map(this::convertToAddressDTO)
                        .collect(Collectors.toList()) : null
        );
    }
}
