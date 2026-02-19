package br.com.fiap.tech.challenge.erp_restaurant.domain.user;

import br.com.fiap.tech.challenge.erp_restaurant.domain.address.Address;
import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.Restaurant;
import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;
import br.com.fiap.tech.challenge.erp_restaurant.shared.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    @Test
    @DisplayName("Teste de criação de usuário com valores corretos")
    void deveCriarUsuarioComSucesso(){
        Long id = 1L;
        String name = "joao";
        String email = "joao@email.com";
        String login = "joao.silva";
        String password = "123456";
        Role role =Role.OWNER;
        Address address = new Address("SP", "São Paulo", "Rua Teste", 123, "Apto 42");
        User owner = new User(1L, "Dono Restaurante", "dono@email.com", "donorest", "senha", Role.OWNER);
        String nameRestaurant = "Restaurante Paris 7";
        Long idRestaurant = 1L;
        LocalTime closingAtRestaurant = LocalTime.parse("22:00");
        LocalTime openingAtRestaurant = LocalTime.parse("10:00");

        //Cria objeto do restaurante
        Restaurant restaurant = Restaurant.builder()
                .id(idRestaurant)
                .name(nameRestaurant)
                .openingAt(openingAtRestaurant)
                .closingAt(closingAtRestaurant)
                .owner(owner)
                .build();

        // Cria usuario
        User user = User.builder()
                .id(id)
                .name(name)
                .email(email)
                .login(login)
                .password(password)
                .role(role)
                .address(address)
                .restaurant(restaurant)
                .build();

        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(login, user.getLogin());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
        assertEquals(address, user.getAddress());
        assertEquals(restaurant, user.getRestaurant());

    }
}
