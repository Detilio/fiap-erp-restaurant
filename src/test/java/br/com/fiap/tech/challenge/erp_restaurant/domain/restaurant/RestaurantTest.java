package br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant;


import br.com.fiap.tech.challenge.erp_restaurant.domain.address.Address;
import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.Restaurant;
import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;
import br.com.fiap.tech.challenge.erp_restaurant.shared.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestaurantTest {
    @Test
    @DisplayName("Deve criar restaurante com todos os atributos corretamente")
    void deveCriarRestaurantComValoresCorretos(){
        Long id = (long) 1.0;
        String name = "Restaurante Teste";
        String kitchenType = "Italiano";
        LocalTime openingAt = LocalTime.parse("10:00");
        LocalTime closingAt = LocalTime.parse("22:00");
        User owner = new User(1L, "Dono Restaurante", "dono@email.com", "donorest", "senha", Role.OWNER);

        Restaurant restaurant = Restaurant.builder()
                .id(id)
                .name(name)
                .kitchenType(kitchenType)
                .openingAt(openingAt)
                .closingAt(closingAt)
                .owner(owner)
                .build();

        assertEquals(name, restaurant.getName());
        assertEquals(kitchenType, restaurant.getKitchenType());
        assertEquals(openingAt, restaurant.getOpeningAt());
        assertEquals(closingAt, restaurant.getClosingAt());
        assertEquals(owner, restaurant.getOwner());
    }

    @Test
    @DisplayName("Deve atualizar o proprietário do restaurante")
    void deveAtualizarProprietario() {
        User antigoDono = new User(1L, "Dono Antigo", "antigo@email.com", "antigo", "senha", Role.OWNER);
        User novoDono = new User(2L, "Novo Dono", "novo@email.com", "novo", "senha", Role.OWNER);

        Restaurant restaurante = Restaurant.builder()
                .name("Restaurante Exemplo")
                .owner(antigoDono)
                .build();

        restaurante.setOwner(novoDono);
        assertEquals(novoDono, restaurante.getOwner());
    }
}
