package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.v1;

import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.user.UserUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.domain.address.Address;
import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.address.AddressRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user.UserChangeRoleRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user.UserRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user.UserResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user.UserUpdateAddressRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.mapper.UserMapper;
import br.com.fiap.tech.challenge.erp_restaurant.shared.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerV1Test {

    @Mock
    private UserUseCase userUseCase;

    @InjectMocks
    private UserControllerV1 userController;

    private UserMapper mapper = UserMapper.INSTANCE;

    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;
    private User domainUser;

    private AddressRequestDTO addressRequestDTO;

    @BeforeEach
    void setup() {
        addressRequestDTO = new AddressRequestDTO("São Paulo", "Cidade", "Rua A", 10, "Apto 7");

        userRequestDTO = new UserRequestDTO(
                1,
                "João",
                "joão@email.com",
                "joao.teste",
                "654321",
                1L
        );

        Address addressDomain = new Address("Santa Catarina", "Cidade", "Rua B", 65, "Apto 10");

        domainUser = User.builder()
                .id(1L)
                .name("Rafael")
                .email("rafael@email.com")
                .login("rafarl.teste")
                .password("123456")
                .role(Role.CUSTOMER)
                .address(addressDomain)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userResponseDTO = mapper.domainToDTO(domainUser);
    }

    @Test
    @DisplayName("Deve buscar os usuários e retornar uma lista de DTO")
    void getUsers_shouldReturnListOfUserResponseDTO() {
        List<User> users = List.of(domainUser);
        when(userUseCase.findAllUsers(0, 10)).thenReturn(users);

        List<UserResponseDTO> result = userController.getUsers(0, 10);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(domainUser.getId().toString(), result.get(0).getId());
        assertEquals(domainUser.getName(), result.get(0).getName());

        verify(userUseCase, times(1)).findAllUsers(0, 10);
    }

    @Test
    @DisplayName("Deve buscar o usuário e retornar um DTO")
    void getUser_shouldReturnUserResponseDTO() {
        when(userUseCase.findUserById(1L)).thenReturn(domainUser);

        UserResponseDTO result = userController.getUser(1L);

        assertNotNull(result);
        assertEquals(domainUser.getId().toString(), result.getId());
        assertEquals(domainUser.getName(), result.getName());

        verify(userUseCase, times(1)).findUserById(1L);
    }

    @Test
    @DisplayName("Deve criar usuário e retornar um DTO")
    void createUser_shouldReturnUserResponseDTO() {
        User domainFromDto = mapper.dtoToDomain(userRequestDTO);
        domainFromDto = User.builder()
                .id(1L)
                .name(domainFromDto.getName())
                .email(domainFromDto.getEmail())
                .login(domainFromDto.getLogin())
                .password(domainFromDto.getPassword())
                .role(domainFromDto.getRole())
                .address(domainFromDto.getAddress())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(userUseCase.save(any(User.class))).thenReturn(domainFromDto);

        UserResponseDTO result = userController.createUser(userRequestDTO);

        assertNotNull(result);
        assertEquals(domainFromDto.getId().toString(), result.getId());
        assertEquals(domainFromDto.getName(), result.getName());

        verify(userUseCase, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Deve alterar o usuário e retornar um DTO")
    void updateUser_shouldReturnUserResponseDTO() {
        User domainFromDto = mapper.dtoToDomain(userRequestDTO);

        when(userUseCase.update(any(User.class))).thenReturn(domainFromDto);

        UserResponseDTO result = userController.updateUser(userRequestDTO);

        assertNotNull(result);
        assertEquals(domainFromDto.getName(), result.getName());

        verify(userUseCase, times(1)).update(any(User.class));
    }

    @Test
    @DisplayName("Deve alterar tipo do usuário e retornar um DTO")
    void updateUserRole_shouldReturnUserResponseDTO() {
        UserChangeRoleRequestDTO roleRequest = new UserChangeRoleRequestDTO(1, Role.OWNER);

        User domainFromDto = mapper.dtoToDomain(roleRequest);
        domainFromDto = User.builder()
                .id(domainFromDto.getId())
                .name("Rafael")
                .role(Role.OWNER)
                .build();

        when(userUseCase.updateRole(any(User.class))).thenReturn(domainFromDto);

        UserResponseDTO result = userController.updateUserRole(roleRequest);

        assertNotNull(result);
        assertEquals(Role.OWNER, result.getRole());

        verify(userUseCase, times(1)).updateRole(any(User.class));
    }

    @Test
    @DisplayName("Deve alterar endereço do usuário e retornar um DTO")
    void updateUserAddress_shouldReturnUserResponseDTO() {
        UserUpdateAddressRequestDTO addressRequest = new UserUpdateAddressRequestDTO(1, addressRequestDTO);

        User domainFromDto = mapper.dtoToDomain(addressRequest);
        domainFromDto = User.builder()
                .id(domainFromDto.getId())
                .name("Rafael")
                .address(new Address(
                        addressRequestDTO.state(),
                        addressRequestDTO.city(),
                        addressRequestDTO.street(),
                        addressRequestDTO.number(),
                        addressRequestDTO.complement()
                ))
                .build();

        when(userUseCase.updateAddress(any(User.class))).thenReturn(domainFromDto);

        UserResponseDTO result = userController.updateUserAddress(addressRequest);

        assertNotNull(result);
        assertEquals(domainFromDto.getId().toString(), result.getId());
        assertEquals(domainFromDto.getAddress().getStreet(), result.getAddress().getStreet());

        verify(userUseCase, times(1)).updateAddress(any(User.class));
    }

    @Test
    @DisplayName("Deve deletar usuário")
    void deleteUser_shouldCallDeleteUseCase() {
        Long idToDelete = 1L;

        doNothing().when(userUseCase).delete(idToDelete);

        userController.deleteUser(idToDelete);

        verify(userUseCase, times(1)).delete(idToDelete);
    }
}
