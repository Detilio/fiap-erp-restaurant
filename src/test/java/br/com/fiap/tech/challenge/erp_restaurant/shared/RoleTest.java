package br.com.fiap.tech.challenge.erp_restaurant.shared;

import br.com.fiap.tech.challenge.erp_restaurant.application.exception.RoleException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    @DisplayName("Deve retornar o enum Role correspondente quando fornecido um valor válido")
    void shouldReturnEnumFromValidString() {
        assertEquals(Role.CUSTOMER, Role.fromString("CUSTOMER"));
        assertEquals(Role.OWNER, Role.fromString("OWNER"));
    }

    @Test
    @DisplayName("Deve lançar RoleException quando o valor não corresponde a nenhum enum")
    void shouldThrowExceptionForInvalidString() {
        String invalid = "ADMIN";

        RoleException exception = assertThrows(RoleException.class, () -> {
            Role.fromString(invalid);
        });

        assertTrue(exception.getMessage().contains("unkonw role"));
        assertTrue(exception.getMessage().contains("CUSTOMER"));
        assertTrue(exception.getMessage().contains("OWNER"));
    }

    @Test
    @DisplayName("Deve diferenciar maiúsculas de minúsculas no método fromString")
    void shouldBeCaseSensitive() {
        assertThrows(RoleException.class, () -> Role.fromString("customer"));
        assertThrows(RoleException.class, () -> Role.fromString("owner"));
    }
}
