package br.com.fiap.tech.challenge.erp_restaurant.service;

import br.com.fiap.tech.challenge.erp_restaurant.dto.UserRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.dto.UserResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.entity.UserEntity;
import br.com.fiap.tech.challenge.erp_restaurant.mapper.UserMapper;
import br.com.fiap.tech.challenge.erp_restaurant.model.User;
import br.com.fiap.tech.challenge.erp_restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // Instancia Mapper para realizar as conversões
    private static final UserMapper userMapper = new UserMapper();

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

        // Usando o UserMapper para fazer a conversão
        User user = userMapper.convertToModel(userRequestDTO);
        UserEntity userEntity = userMapper.convertToEntity(user);
        UserEntity savedUserEntity = userRepository.save(userEntity);

        return userMapper.convertToResponseDTO(userMapper.convertToModel(savedUserEntity));
    }

    public List<UserResponseDTO> listUsers(String token) {
        if (!validarToken(token)) {
            throw new IllegalArgumentException("Token inválido!");
        }

        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(userMapper::convertToModel) // Usando o UserMapper
                .map(userMapper::convertToResponseDTO)
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
        existingUserEntity.setName(userRequestDTO.getName());
        existingUserEntity.setEmail(userRequestDTO.getEmail());
        existingUserEntity.setLogin(userRequestDTO.getLogin());
        existingUserEntity.setPassword(userRequestDTO.getPassword()); // Considere a segurança ao atualizar senhas
        existingUserEntity.setType(userRequestDTO.getType());
        existingUserEntity.setDateChange(new java.util.Date());

        UserEntity updatedUserEntity = userRepository.save(existingUserEntity);
        return userMapper.convertToResponseDTO(userMapper.convertToModel(updatedUserEntity));
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
