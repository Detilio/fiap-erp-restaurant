package br.com.fiap.tech.challenge.erp_restaurant.application.interactor.dish;

import br.com.fiap.tech.challenge.erp_restaurant.application.exception.NotFoundException;
import br.com.fiap.tech.challenge.erp_restaurant.application.gateway.restaurant.DishGateway;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.restaurant.RestaurantUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.Restaurant;
import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.menu.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DishUseInteractorTest {
    private DishGateway dishGateway;
    private RestaurantUseCase restaurantUseCase;
    private DishUseInteractor interactor;

    @BeforeEach
    public void setup() {
        dishGateway = mock(DishGateway.class);
        restaurantUseCase = mock(RestaurantUseCase.class);
        interactor = new DishUseInteractor(dishGateway, restaurantUseCase);
    }

    @Test
    public void testCreate() {
        Dish dish = new Dish("Pizza", "Deliciosa pizza", BigDecimal.valueOf(50), false, null);
        long restaurantId = 1L;

        // Mock restaurant
        Restaurant restaurant = Restaurant.builder()
                .id(1L)
                .build();
        when(restaurantUseCase.findById(restaurantId)).thenReturn(restaurant);
        when(dishGateway.create(dish)).thenReturn(dish);

        Dish result = interactor.create(restaurantId, dish);

        assertNotNull(result);
        assertEquals(dish.getName(), result.getName());
        assertEquals(dish.getDescription(), result.getDescription());
        verify(dishGateway, times(1)).create(dish);
    }

    @Test
    public void testFindByIdSuccess() {
        Dish dish = new Dish("Pizza", "Deliciosa pizza", BigDecimal.valueOf(50), false, null);
        dish.setId(1L);

        when(dishGateway.findById(1L)).thenReturn(Optional.of(dish));

        Dish result = interactor.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Pizza", result.getName());
        verify(dishGateway, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdNotFound() {
        when(dishGateway.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> interactor.findById(1L));
    }

    @Test
    public void testFindAll() {
        Dish dish1 = new Dish("Pizza", "Deliciosa pizza", BigDecimal.valueOf(50), false, null);
        Dish dish2 = new Dish("Sushi", "DeliciosO Sushi", BigDecimal.valueOf(40), false, null);
        List<Dish> dishes = Arrays.asList(dish1, dish2);

        when(dishGateway.findAll(10, 0)).thenReturn(dishes);

        List<Dish> result = interactor.findAll(10, 0);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Pizza", result.get(0).getName());
        assertEquals("Sushi", result.get(1).getName());
        verify(dishGateway, times(1)).findAll(10, 0);
    }

    @Test
    public void testDeleteById() {
        Dish dish = new Dish("Pizza", "Deliciosa pizza", BigDecimal.valueOf(50), false, null);
        dish.setId(1L);

        // Mock
        when(dishGateway.findById(1L)).thenReturn(Optional.of(dish));
        doNothing().when(dishGateway).delete(1L);

        interactor.deleteById(1L);

        verify(dishGateway, times(1)).findById(1L);
        verify(dishGateway, times(1)).delete(1L);
    }

    @Test
    public void testUpdate() {
        URI uri = URI.create("https://www.restaurant.com");
        URL photo = null;
        try {
            photo = uri.toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Dish existingDish = new Dish("Pizza", "Deliciosa pizza", BigDecimal.valueOf(50), false, photo);
        existingDish.setId(1L);
        Dish updatedDish = new Dish("Pizza Grande", "Pizza gigante e deliciosa", BigDecimal.valueOf(40.0), false, photo);
        updatedDish.setId(1L);

        when(dishGateway.findById(1L)).thenReturn(Optional.of(existingDish));
        when(dishGateway.update(existingDish)).thenReturn(updatedDish);

        Dish result = interactor.update(updatedDish);

        assertNotNull(result);
        assertEquals("Pizza Grande", result.getName());
        assertEquals("Pizza gigante e deliciosa", result.getDescription());
        assertEquals(BigDecimal.valueOf(40.0), result.getPrice());
        assertEquals(photo, result.getPhoto());
        verify(dishGateway, times(1)).findById(1L);
        verify(dishGateway, times(1)).update(existingDish);
    }
}
