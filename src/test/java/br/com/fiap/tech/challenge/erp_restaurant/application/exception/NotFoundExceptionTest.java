package br.com.fiap.tech.challenge.erp_restaurant.application.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotFoundExceptionTest {
    @Test
    @DisplayName("Deve lançar NotFoundException com a mensagem correta")
    void deveLancarNotFoundExceptionComMensagem() {
        String mensagemEsperada = "Recurso não encontrado";

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> { throw new NotFoundException(mensagemEsperada); }
        );

        assertEquals(mensagemEsperada, exception.getMessage());
    }
}
