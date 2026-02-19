package br.com.fiap.tech.challenge.erp_restaurant.application.interactor;

import br.com.fiap.tech.challenge.erp_restaurant.application.exception.NotFoundException;
import br.com.fiap.tech.challenge.erp_restaurant.application.exception.RoleException;
import br.com.fiap.tech.challenge.erp_restaurant.application.gateway.restaurant.RestaurantGateway;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.address.AddressUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.user.UserUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.domain.address.Address;
import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.Restaurant;
import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;
import br.com.fiap.tech.challenge.erp_restaurant.shared.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RestaurantInteractorTest {
    private UserUseCase userUseCase;
    private RestaurantGateway restaurantGateway;
    private RestaurantInteractor interactor;

    @BeforeEach
    void setUp() {
        userUseCase = mock(UserUseCase.class);
        restaurantGateway = mock(RestaurantGateway.class);
        interactor = new RestaurantInteractor(userUseCase, restaurantGateway);
    }

    @Test
    void deveSalvarRestauranteComOwnerValido() {
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

        when(userUseCase.findUserById(1L)).thenReturn(owner);
        when(restaurantGateway.save(restaurant)).thenReturn(restaurant);

        Restaurant salvo = interactor.save(restaurant);

        assertNotNull(salvo);
        assertEquals("Restaurante Teste", salvo.getName());
        verify(userUseCase).findUserById(1L);
        verify(restaurantGateway).save(restaurant);
    }

    @Test
    void deveLancarExcecaoSeOwnerNaoForOWNER() {
        Long id = 2L;
        String name = "Restaurante Teste";
        String kitchenType = "Italiano";
        LocalTime openingAt = LocalTime.parse("10:00");
        LocalTime closingAt = LocalTime.parse("22:00");
        User owner = new User(2L, "Dono Restaurante", "dono@email.com", "donorest", "senha", Role.CUSTOMER);

        Restaurant restaurant = Restaurant.builder()
                .id(id)
                .name(name)
                .kitchenType(kitchenType)
                .openingAt(openingAt)
                .closingAt(closingAt)
                .owner(owner)
                .build();

        when(userUseCase.findUserById(2L)).thenReturn(owner);

        assertThrows(RoleException.class, () -> interactor.save(restaurant));
        verify(userUseCase).findUserById(2L);
        verify(restaurantGateway, never()).save(any());
    }

    @Test
    void deveRetornarRestaurantePorId() {
        Long id = 10L;
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

        when(restaurantGateway.findById(10L)).thenReturn(Optional.of(restaurant));

        Restaurant result = interactor.findById(10L);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Restaurante Teste", result.getName());
        verify(restaurantGateway).findById(10L);
    }

    @Test
    void deveLancarExcecaoSeRestauranteNaoForEncontrado() {
        when(restaurantGateway.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> interactor.findById(99L));
        verify(restaurantGateway).findById(99L);
    }
}
