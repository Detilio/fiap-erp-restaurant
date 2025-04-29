package br.com.fiap.tech.challenge.erp_restaurant.service;

import br.com.fiap.tech.challenge.erp_restaurant.dto.AddressDTO;
import br.com.fiap.tech.challenge.erp_restaurant.dto.UserRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.dto.UserResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.entity.UserEntity;
import br.com.fiap.tech.challenge.erp_restaurant.model.Address;
import br.com.fiap.tech.challenge.erp_restaurant.model.User;
import br.com.fiap.tech.challenge.erp_restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final String SECRET = "Y29tLmV4YW1wbGUudG9rZW4uZ2VuZXJhdG9yLkdlcmFyVG9rZW5BbGVhdG9yaW8=";
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Metodo para converter Address para AddressDTO
    private Address convertToAddressModel(AddressDTO addressDTO) {
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

    private AddressDTO convertToAddressDTO(Address address) {
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

    private User convertToModel(UserRequestDTO requestDTO) {
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

    // Metodo para converter a classe de modelo User para UserEntity
    private UserEntity convertToEntity(User user) {
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

    // Metodo para converter UserEntity para a classe de modelo User
    private User convertToModel(UserEntity entity) {
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

    // Metodo para converter a classe de modelo User para UserResponseDTO
    private UserResponseDTO convertToResponseDTO(User user) {
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

    public String authenticate(String login, String password) {
        Optional<UserEntity> userEntityOptional = userRepository.findByLoginAndPasswordIgnoreCase(login, password);
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            if (userEntity.getPassword().equals(password)) {
                return "Bearer " + SECRET;
            }
        }
        return null;
    }

    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        Optional<UserEntity> existingUser = userRepository.findByLoginIgnoreCase(userRequestDTO.getLogin());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Já existe usuário cadastrado com este login!");
        }
        User user = convertToModel(userRequestDTO);
        UserEntity userEntity = convertToEntity(user);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return convertToResponseDTO(convertToModel(savedUserEntity));
    }

    public List<UserResponseDTO> listUsers(String token) {
        if (!validarToken(token)) {
            throw new IllegalArgumentException("Token inválido!");
        }
        // Precisa buscar o usuário pelo token para verificar o tipo (MASTER)
        // Esta é uma implementação simplificada, em um cenário real, o token conteria informações do usuário
        // ou você precisaria consultar o banco com base no token.
        // Assumindo que o token válido implica ser MASTER para este exemplo.
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(this::convertToModel)
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO, String token) {
        if (!validarToken(token)) {
            throw new IllegalArgumentException("Token inválido!");
        }
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (userEntityOptional.isEmpty()) {
            throw new NoSuchElementException("Usuário não encontrado.");
        }
        UserEntity existingUserEntity = userEntityOptional.get();
        // Atualiza os campos com base no DTO
        existingUserEntity.setName(userRequestDTO.getName());
        existingUserEntity.setEmail(userRequestDTO.getEmail());
        existingUserEntity.setLogin(userRequestDTO.getLogin());
        existingUserEntity.setPassword(userRequestDTO.getPassword()); // Considere a segurança ao atualizar senhas
        existingUserEntity.setType(userRequestDTO.getType());
        existingUserEntity.setDateChange(new Date());

        UserEntity updatedUserEntity = userRepository.save(existingUserEntity);
        return convertToResponseDTO(convertToModel(updatedUserEntity));
    }

    public void deleteUser(Long id, String token) {
        if (!validarToken(token)) {
            throw new IllegalArgumentException("Token inválido!");
        }
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (userEntityOptional.isEmpty()) {
            throw new NoSuchElementException("Usuário não encontrado.");
        }
        userRepository.deleteById(id);
    }

    private Boolean validarToken(String token) {
        return token.equalsIgnoreCase(SECRET);
    }
}