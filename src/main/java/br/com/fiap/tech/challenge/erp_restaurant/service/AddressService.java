package br.com.fiap.tech.challenge.erp_restaurant.service;

import br.com.fiap.tech.challenge.erp_restaurant.dto.AddressDTO;
import br.com.fiap.tech.challenge.erp_restaurant.entity.AddressEntity;
import br.com.fiap.tech.challenge.erp_restaurant.entity.UserEntity;
import br.com.fiap.tech.challenge.erp_restaurant.model.Address;
import br.com.fiap.tech.challenge.erp_restaurant.repository.AddressRepository;
import br.com.fiap.tech.challenge.erp_restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    // Converte AddressDTO para o modelo Address
    private Address convertToModel(AddressDTO addressDTO) {
        Address address = new Address();
        address.setStreet(addressDTO.getStreet());
        address.setNumber(addressDTO.getNumber());
        address.setDistrict(addressDTO.getDistrict());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setZipCode(addressDTO.getZipCode());
        return address;
    }

    // Converte o modelo Address para a entidade AddressEntity
    private AddressEntity convertToEntity(Address address, UserEntity user) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet(address.getStreet());
        addressEntity.setNumber(address.getNumber());
        addressEntity.setDistrict(address.getDistrict());
        addressEntity.setCity(address.getCity());
        addressEntity.setState(address.getState());
        addressEntity.setZipCode(address.getZipCode());
        addressEntity.setUser(user);
        return addressEntity;
    }

    // Converte AddressEntity para AddressDTO
    private AddressDTO convertToDTO(AddressEntity addressEntity) {
        return new AddressDTO(
                addressEntity.getId(),
                addressEntity.getStreet(),
                addressEntity.getNumber(),
                addressEntity.getDistrict(),
                addressEntity.getCity(),
                addressEntity.getState(),
                addressEntity.getZipCode(),
                addressEntity.getUser().getId() // Retorna o ID do usuário associado
        );
    }

    // Adiciona um novo endereço para um usuário
    public AddressDTO addAddressToUser(Long userId, AddressDTO addressDTO) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com o ID: " + userId));
        Address address = convertToModel(addressDTO);
        AddressEntity addressEntity = convertToEntity(address, user);
        AddressEntity savedAddressEntity = addressRepository.save(addressEntity);
        return convertToDTO(savedAddressEntity);
    }

    // Lista todos os endereços de um usuário específico
    public List<AddressDTO> getAllAddressesByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com o ID: " + userId));
        List<AddressEntity> addressEntities = addressRepository.findByUser_Id(userId);
        return addressEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Busca um endereço específico por ID
    public AddressDTO getAddressById(Long addressId) {
        AddressEntity addressEntity = addressRepository.findById(addressId)
                .orElseThrow(() -> new NoSuchElementException("Endereço não encontrado com o ID: " + addressId));
        return convertToDTO(addressEntity);
    }

    // Atualiza um endereço existente
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        AddressEntity existingAddressEntity = addressRepository.findById(addressId)
                .orElseThrow(() -> new NoSuchElementException("Endereço não encontrado com o ID: " + addressId));

        // Atualiza os campos com os dados do DTO
        existingAddressEntity.setStreet(addressDTO.getStreet());
        existingAddressEntity.setNumber(addressDTO.getNumber());
        existingAddressEntity.setDistrict(addressDTO.getDistrict());
        existingAddressEntity.setCity(addressDTO.getCity());
        existingAddressEntity.setState(addressDTO.getState());
        existingAddressEntity.setZipCode(addressDTO.getZipCode());

        AddressEntity updatedAddressEntity = addressRepository.save(existingAddressEntity);
        return convertToDTO(updatedAddressEntity);
    }

    // Remove um endereço por ID
    public void deleteAddress(Long addressId) {
        if (!addressRepository.existsById(addressId)) {
            throw new NoSuchElementException("Endereço não encontrado com o ID: " + addressId);
        }
        addressRepository.deleteById(addressId);
    }
}