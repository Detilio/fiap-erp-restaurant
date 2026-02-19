package br.com.fiap.tech.challenge.erp_restaurant.application.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RoleExceptionTest {
    @Test
    @DisplayName("Deve lançar RoleException com a mensagem correta")
    void deveLancarRoleExceptionComMensagem() {
        String mensagemEsperada = "Exceção da função";

        RoleException exception = assertThrows(
                RoleException.class,
                () -> { throw new RoleException(mensagemEsperada); }
        );

        assertEquals(mensagemEsperada, exception.getMessage());
    }
}
