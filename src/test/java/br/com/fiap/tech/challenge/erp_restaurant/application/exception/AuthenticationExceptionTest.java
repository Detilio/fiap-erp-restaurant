package br.com.fiap.tech.challenge.erp_restaurant.application.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthenticationExceptionTest {
    @Test
    @DisplayName("Deve lançar AuthenticationException com a mensagem correta")
    void deveLancarAuthenticationExceptionComMensagem() {
        String mensagemEsperada = "Usuário não autenticado";

        AuthenticationException exception = assertThrows(
                AuthenticationException.class,
                () -> { throw new AuthenticationException(mensagemEsperada); }
        );

        assertEquals(mensagemEsperada, exception.getMessage());
    }
}
