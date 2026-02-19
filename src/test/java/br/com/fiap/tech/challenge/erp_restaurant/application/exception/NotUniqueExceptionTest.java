package br.com.fiap.tech.challenge.erp_restaurant.application.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotUniqueExceptionTest {
    @Test
    @DisplayName("Deve lançar NotUniqueException com a mensagem correta")
    void deveLancarNotUniqueExceptionComMensagem() {
        String mensagemEsperada = "Valor já existente";

        NotUniqueException exception = assertThrows(
                NotUniqueException.class,
                () -> { throw new NotUniqueException(mensagemEsperada); }
        );

        assertEquals(mensagemEsperada, exception.getMessage());
    }
}
