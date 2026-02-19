package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.v1;

import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.address.AddressUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.domain.address.Address;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.handler.ExceptionHandlerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

@WebMvcTest(AddressControllerV1.class)
@Import({AddressControllerV1Test.Config.class, AddressControllerV1Test.Config.class, ExceptionHandlerController.class})
class AddressControllerV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressUseCase addressUseCase;

    @TestConfiguration
    static class Config {
        @Bean
        public AddressUseCase addressUseCase() {
            return org.mockito.Mockito.mock(AddressUseCase.class);
        }
    }

    @Test
    void findById_deveRetornarStatus200EBodyCorreto() throws Exception {
        // Arrange
        Address address = new Address("SP", "São Paulo", "Rua Teste", 123, "Bloco A");
        address.setId(1L);

        LocalDateTime now = LocalDateTime.now();
        address.setCreatedAt(now);
        address.setUpdatedAt(now);

        when(addressUseCase.findById(1L)).thenReturn(address);

        // Act & Assert
        mockMvc.perform(get("/v1/address/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.state").value("SP"))
                .andExpect(jsonPath("$.city").value("São Paulo"))
                .andExpect(jsonPath("$.street").value("Rua Teste"))
                .andExpect(jsonPath("$.number").value(123))
                .andExpect(jsonPath("$.complement").value("Bloco A"));
    }

// Pendente a criação de consistência para quando o user id não for encontrado
//    @Test
//    void findById_deveRetornar404QuandoNaoEncontrado() throws Exception {
//        // Arrange
//        when(addressUseCase.findById(99L)).thenReturn(null);
//
//        // Act & Assert
//        mockMvc.perform(get("/v1/address/99"))
//                .andExpect(status().isInternalServerError()); // ou customize um 404 se tiver lógica pra isso
//    }
}
