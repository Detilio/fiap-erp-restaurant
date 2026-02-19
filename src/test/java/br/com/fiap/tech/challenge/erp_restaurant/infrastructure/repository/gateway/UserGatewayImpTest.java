package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.repository.gateway;

import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.UserEntity;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.repository.UserRepository;
import br.com.fiap.tech.challenge.erp_restaurant.shared.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserGatewayImpTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserGatewayImp gateway;

    private UserEntity userEntity;
    private User user;

    @BeforeEach
    void setup() {
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("email@test.com");
        userEntity.setLogin("user123");
        userEntity.setPassword("pass");
        userEntity.setName("Test User");
        userEntity.setRole(Role.CUSTOMER);
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setUpdatedAt(LocalDateTime.now());

        user = new User(1L, "Test User", "email@test.com", "user123", "pass", Role.CUSTOMER);
    }

    @Test
    @DisplayName("Deve buscar usuário pelo Id")
    void testFindById_success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        User result = gateway.findById(1L).orElse(null);
        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    @DisplayName("Deve buscar todos os usuários")
    void testFindAllUsers_success() {
        Page<UserEntity> page = new PageImpl<>(singletonList(userEntity));
        when(userRepository.findAll(PageRequest.of(10, 1))).thenReturn(page);

        List<User> users = gateway.findAllUsers(1, 10);
        assertEquals(1, users.size());
        assertEquals(user.getEmail(), users.get(0).getEmail());
    }

    @Test
    @DisplayName("Deve salvar usuário")
    void testSave_success() {
        when(userRepository.save(any())).thenReturn(userEntity);

        User result = gateway.save(user);
        assertNotNull(result);
        assertEquals(user.getLogin(), result.getLogin());
    }

    @Test
    @DisplayName("Deve verificar se existe usuario com o email informado")
    void testExistsByEmail_true() {
        when(userRepository.existsByEmail("email@test.com")).thenReturn(true);
        assertTrue(gateway.existsByEmail("email@test.com"));
    }

    @Test
    @DisplayName("Deve verificar se existe usuario com o usuário informado")
    void testExistsByLogin_true() {
        when(userRepository.existsByLogin("user123")).thenReturn(true);
        assertTrue(gateway.existsByLogin("user123"));
    }

    @Test
    @DisplayName("Deve deletar usuario")
    void testDelete_success() {
        doNothing().when(userRepository).deleteById(1L);
        gateway.delete(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve retornar falso ao buscar usuário com id inexistente")
    void testExistsById_defaultFalse() {
        assertFalse(gateway.existsById(1L));
    }

    @Test
    @DisplayName("Deve buscar usuário por email ou login")
    void testFindByEmailOrLogin_success() {
        when(userRepository.findByEmailOrLogin("email@test.com", "user123")).thenReturn(userEntity);
        User found = gateway.findByEmailOrLogin("email@test.com", "user123");
        assertNotNull(found);
        assertEquals(user.getLogin(), found.getLogin());
    }
}
