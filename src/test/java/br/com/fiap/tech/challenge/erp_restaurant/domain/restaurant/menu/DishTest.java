package br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.menu;

import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.Restaurant;
import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.menu.Dish;
import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;
import br.com.fiap.tech.challenge.erp_restaurant.shared.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DishTest {
    @Test
    @DisplayName("Deve criar item de menu com sucesso")
    void DeveCriarItensDoMenuComSucesso(){
        long id = 1L;
        String name = "Prato Feito";
        String description = "Arroz, Feijão e Bife";
        BigDecimal price = BigDecimal.valueOf(25.99);
        Boolean onlyToGo = false;
        URI uri = URI.create("https://www.restaurant.com");
        URL photo = null;
        try {
            photo = uri.toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        String nameRestaurant = "Restaurante Paris 7";
        Long idRestaurant = 1l;
        LocalTime closingAtRestaurant = LocalTime.parse("22:00");
        LocalTime openingAtRestaurant = LocalTime.parse("10:00");
        String loginUser = "rafael.detilio";
        String emailUser = "rafael.detilio@email.com";
        Long idUser = 1L;
        String nameUser = "Rafael";
        String passwordUser = "123456";
        Role role = Role.OWNER;

        Dish dish = Dish.builder()
                .description(description)
                .name(name)
                .id(id)
                .price(price)
                .photo(photo)
                .onlyToGo(onlyToGo)
                .restaurant(Restaurant.builder()
                        .name(nameRestaurant)
                        .id(idRestaurant)
                        .closingAt(closingAtRestaurant)
                        .openingAt(openingAtRestaurant)
                        .owner(User.builder()
                                .login(loginUser)
                                .email(emailUser)
                                .role(role)
                                .id(idUser)
                                .name(nameUser)
                                .password(passwordUser)
                                .build())
                        .build())
                .build();

        assertEquals(description, dish.getDescription());
        assertEquals(name, dish.getName());
        assertEquals(id, dish.getId());
        assertEquals(price, dish.getPrice());
        assertEquals(photo, dish.getPhoto());
        assertEquals(onlyToGo, dish.getOnlyToGo());
        assertEquals(nameRestaurant, dish.getRestaurant().getName());
        assertEquals(idRestaurant, dish.getRestaurant().getId());
        assertEquals(closingAtRestaurant, dish.getRestaurant().getClosingAt());
        assertEquals(openingAtRestaurant, dish.getRestaurant().getOpeningAt());
        assertEquals(loginUser, dish.getRestaurant().getOwner().getLogin());
        assertEquals(emailUser, dish.getRestaurant().getOwner().getEmail());
        assertEquals(role, dish.getRestaurant().getOwner().getRole());
        assertEquals(idUser, dish.getRestaurant().getOwner().getId());
        assertEquals(nameUser, dish.getRestaurant().getOwner().getName());
        assertEquals(passwordUser, dish.getRestaurant().getOwner().getPassword());
    }
}
