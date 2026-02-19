package br.com.fiap.tech.challenge.erp_restaurant.application.interactor;

import br.com.fiap.tech.challenge.erp_restaurant.application.exception.NotFoundException;
import br.com.fiap.tech.challenge.erp_restaurant.application.exception.NotUniqueException;
import br.com.fiap.tech.challenge.erp_restaurant.application.gateway.UserGateway;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.address.AddressUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.domain.address.Address;
import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;
import br.com.fiap.tech.challenge.erp_restaurant.shared.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserInteractorTest {

    private UserGateway userGateway;
    private AddressUseCase addressUseCase;
    private UserInteractor interactor;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        addressUseCase = mock(AddressUseCase.class);
        interactor = new UserInteractor(userGateway, addressUseCase);
    }

    @Test
    void deveListarUsuarios() {
        when(userGateway.findAllUsers(10, 0)).thenReturn(List.of(mock(User.class)));
        List<User> result = interactor.findAllUsers(0, 10);
        assertEquals(1, result.size());
        verify(userGateway).findAllUsers(10, 0);
    }

    @Test
    void deveRetornarUsuarioPorId() {
        User user = new User(1L, "João", "joao@email.com", "joao", "123", Role.CUSTOMER);
        when(userGateway.findById(1L)).thenReturn(Optional.of(user));
        User result = interactor.findUserById(1L);
        assertEquals("João", result.getName());
    }

    @Test
    void deveLancarExcecaoSeUsuarioNaoForEncontrado() {
        when(userGateway.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> interactor.findUserById(99L));
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        User userToSave = new User(null, "Maria", "maria@email.com", "maria", "abc", Role.CUSTOMER);
        User savedUser = User.builder().id(1L).name("Maria").email("maria@email.com").login("maria").role(Role.CUSTOMER).build();

        when(userGateway.existsByEmail("maria@email.com")).thenReturn(false);
        when(userGateway.existsByLogin("maria")).thenReturn(false);
        when(userGateway.save(any())).thenReturn(savedUser);

        User result = interactor.save(userToSave);

        assertEquals(1L, result.getId());
        verify(userGateway).save(any());
    }

    @Test
    void deveLancarErroAoSalvarEmailDuplicado() {
        when(userGateway.existsByEmail("maria@email.com")).thenReturn(true);

        User user = new User(null, "Maria", "maria@email.com", "maria", "123", Role.CUSTOMER);
        assertThrows(NotUniqueException.class, () -> interactor.save(user));
    }

    @Test
    void deveLancarErroAoSalvarLoginDuplicado() {
        when(userGateway.existsByEmail("maria@email.com")).thenReturn(false);
        when(userGateway.existsByLogin("maria")).thenReturn(true);

        User user = new User(null, "Maria", "maria@email.com", "maria", "123", Role.CUSTOMER);
        assertThrows(NotUniqueException.class, () -> interactor.save(user));
    }

    @Test
    void deveAtualizarUsuarioComSucesso() {
        User userToUpdate = new User(1L, "Rafa", "rafa@email.com", "rafa", "321", Role.OWNER);
        when(userGateway.findById(1L)).thenReturn(Optional.of(userToUpdate));
        when(userGateway.save(any())).thenReturn(userToUpdate);

        User result = interactor.update(userToUpdate);
        assertEquals("Rafa", result.getName());
        verify(userGateway).save(any());
    }

    @Test
    void deveAtualizarEnderecoExistente() {
        Address oldAddress = new Address("SP", "São Paulo", "Rua A", 123, "Apto 1");
        oldAddress.setId(1L);

        Address newAddress = new Address("SP", "São Paulo", "Rua B", 456, "Casa");
        newAddress.setId(2L);

        User user = User.builder().id(1L).name("Fulano").address(oldAddress).build();
        User update = User.builder().id(1L).name("Fulano").address(newAddress).build();

        when(userGateway.findById(1L)).thenReturn(Optional.of(user));
        when(addressUseCase.update(1L, newAddress)).thenReturn(newAddress);

        User updated = interactor.updateAddress(update);

        assertEquals("Rua B", updated.getAddress().getStreet());
        verify(addressUseCase).update(1L, newAddress);
        verify(userGateway).save(user);
    }

    @Test
    void deveCriarEnderecoSeNaoExistente() {
        Address newAddress = new Address("SP", "Campinas", "Rua Nova", 456, null);
        newAddress.setId(2L);

        User user = User.builder().id(1L).name("Fulano").build();
        User update = User.builder().id(1L).name("Fulano").address(newAddress).build();

        when(userGateway.findById(1L)).thenReturn(Optional.of(user));
        when(addressUseCase.create(newAddress)).thenReturn(newAddress);

        User result = interactor.updateAddress(update);

        assertEquals("Rua Nova", result.getAddress().getStreet());
        verify(addressUseCase).create(newAddress);
    }

    @Test
    void deveLancarErroSeEnderecoForNuloNaAtualizacao() {
        User update = User.builder().id(1L).name("Fulano").build();
        when(userGateway.findById(1L)).thenReturn(Optional.of(update));

        assertThrows(IllegalArgumentException.class, () -> interactor.updateAddress(update));
    }

    @Test
    void deveAtualizarRoleDoUsuario() {
        User user = new User(1L, "João", "joao@email.com", "joao", "123", Role.CUSTOMER);
        User atualizacao = new User(1L, "João", "joao@email.com", "joao", "123", Role.OWNER);

        when(userGateway.findById(1L)).thenReturn(Optional.of(user));
        when(userGateway.save(any())).thenReturn(atualizacao);

        User result = interactor.updateRole(atualizacao);

        assertEquals(Role.OWNER, result.getRole());
        verify(userGateway).save(user);
    }

    @Test
    void deveDeletarUsuario() {
        User user = new User(1L, "João", "joao@email.com", "joao", "123", Role.CUSTOMER);
        when(userGateway.findById(1L)).thenReturn(Optional.of(user));

        interactor.delete(1L);

        verify(userGateway).delete(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarUsuarioInexistente() {
        when(userGateway.findById(404L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> interactor.delete(404L));
    }
}
