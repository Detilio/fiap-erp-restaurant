package br.com.fiap.tech.challenge.erp_restaurant.application.interactor;

import br.com.fiap.tech.challenge.erp_restaurant.application.exception.AuthenticationException;
import br.com.fiap.tech.challenge.erp_restaurant.application.gateway.UserGateway;
import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;
import br.com.fiap.tech.challenge.erp_restaurant.shared.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AuthInteractorTest {
    private UserGateway userGateway;
    private AuthInteractor authInteractor;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        authInteractor = new AuthInteractor(userGateway);
    }

    @Test
    void deveAutenticarUsuario() {
        User input = new User(1L, "joao", "joao@gmail.com", "joao.silva", "123456", Role.OWNER);
        User existente = new User(1L, "joao", "joao@gmail.com", "joao.silva", "123456", Role.OWNER);

        when(userGateway.findByEmailOrLogin("joao@gmail.com", "joao.silva")).thenReturn(existente);

        User autenticado = authInteractor.authenticate(input);

        assertEquals(1L, autenticado.getId());
        assertEquals("joao.silva", autenticado.getLogin());
        assertEquals("123456", autenticado.getPassword());
        verify(userGateway).findByEmailOrLogin("joao@gmail.com", "joao.silva");
    }

    @Test
    void deveLancarExcecaoSeUsuarioNaoExiste() {
        User input = User.builder()
                .login("naoexiste")
                .password("senha")
                .build();

        when(userGateway.findByEmailOrLogin("naoexiste", "naoexiste")).thenReturn(null);

        AuthenticationException ex = assertThrows(
                AuthenticationException.class,
                () -> authInteractor.authenticate(input)
        );

        assertEquals("naoexiste is not in here", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeSenhaIncorreta() {
        User input = User.builder()
                .login("rafael")
                .password("errada")
                .email("rafael@email.com")
                .build();

        User existente = User.builder()
                .id(2L)
                .login("rafael")
                .password("correta")
                .email("rafael@email.com")
                .role(Role.CUSTOMER)
                .build();

        when(userGateway.findByEmailOrLogin("rafael@email.com", "rafael")).thenReturn(existente);

        AuthenticationException ex = assertThrows(
                AuthenticationException.class,
                () -> authInteractor.authenticate(input)
        );

        assertEquals("not authorized to enter here", ex.getMessage());
    }
}
