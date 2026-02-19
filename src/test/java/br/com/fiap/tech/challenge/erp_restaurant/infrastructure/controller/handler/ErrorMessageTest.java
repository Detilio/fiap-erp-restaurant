package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorMessageTest {

    @Test
    @DisplayName("Deve criar um ErrorMessage com a mensagem fornecida e permitir acesso via getter")
    void shouldCreateErrorMessageAndAccessMessage() {
        // Cenário: Uma mensagem de erro específica
        String expectedMessage = "Ocorreu um erro interno no servidor.";

        // Ação: Criar uma instância de ErrorMessage usando o construtor gerado pelo Lombok
        ErrorMessage errorMessage = new ErrorMessage(expectedMessage);

        // Verificação: Assegurar que a mensagem armazenada é a mesma que foi fornecida
        assertThat(errorMessage).isNotNull(); // Verifica se o objeto não é nulo
        assertThat(errorMessage.getMessage()).isEqualTo(expectedMessage); // Verifica o valor da mensagem
    }

    @Test
    @DisplayName("Deve criar um ErrorMessage com uma mensagem vazia")
    void shouldCreateErrorMessageWithEmptyMessage() {
        // Cenário: Uma mensagem de erro vazia
        String expectedMessage = "";

        // Ação: Criar uma instância de ErrorMessage
        ErrorMessage errorMessage = new ErrorMessage(expectedMessage);

        // Verificação: Assegurar que a mensagem é vazia
        assertThat(errorMessage).isNotNull();
        assertThat(errorMessage.getMessage()).isEmpty();
    }

    @Test
    @DisplayName("Deve criar um ErrorMessage com uma mensagem nula")
    void shouldCreateErrorMessageWithNullMessage() {
        // Cenário: Uma mensagem de erro nula
        String expectedMessage = null;

        // Ação: Criar uma instância de ErrorMessage
        ErrorMessage errorMessage = new ErrorMessage(expectedMessage);

        // Verificação: Assegurar que a mensagem é nula
        assertThat(errorMessage).isNotNull();
        assertThat(errorMessage.getMessage()).isNull();
    }
}