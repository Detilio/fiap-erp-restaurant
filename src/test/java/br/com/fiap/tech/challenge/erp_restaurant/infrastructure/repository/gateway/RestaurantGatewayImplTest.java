package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.repository.gateway;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.Restaurant;
import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.RestaurantEntity;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.UserEntity;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.repository.RestaurantRepository;

public class RestaurantGatewayImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantGatewayImpl restaurantGateway;

    private Restaurant restaurant;
    private RestaurantEntity entity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        User user = User.builder().id(1L).name("João").build();

        restaurant = Restaurant.builder()
                .id(1L)
                .name("Restaurante A")
                .kitchenType("Italiana")
                .openingAt(LocalTime.of(9, 0))
                .closingAt(LocalTime.of(18, 0))
                .owner(user)
                .build();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("João");

        entity = new RestaurantEntity();
        entity.setId(1L);
        entity.setName("Restaurante A");
        entity.setKitchenType("Italiana");
        entity.setOpeningAt(LocalTime.of(9, 0));
        entity.setClosingAt(LocalTime.of(18, 0));
        entity.setOwner(userEntity);
    }

    @Test
    @DisplayName("Deve salvar um restaurante com sucesso")
    void testSave() {
        when(restaurantRepository.save(any(RestaurantEntity.class))).thenReturn(entity);

        Restaurant result = restaurantGateway.save(restaurant);

        assertNotNull(result);
        assertEquals("Restaurante A", result.getName());
        verify(restaurantRepository, times(1)).save(any(RestaurantEntity.class));
    }

    @Test
    @DisplayName("Deve retornar todos os restaurantes paginados")
    void testFindAll() {
        Page<RestaurantEntity> page = new PageImpl<>(List.of(entity));
        when(restaurantRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        List<Restaurant> result = restaurantGateway.findAll(0, 10);

        assertEquals(1, result.size());
        verify(restaurantRepository).findAll(PageRequest.of(0, 10));
    }

    @Test
    @DisplayName("Deve retornar restaurante por ID existente")
    void testFindByIdFound() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Restaurant> result = restaurantGateway.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Restaurante A", result.get().getName());
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar restaurante por ID inexistente")
    void testFindByIdNotFound() {
        when(restaurantRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Restaurant> result = restaurantGateway.findById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Deve deletar restaurante pelo ID")
    void testDelete() {
        doNothing().when(restaurantRepository).deleteById(1L);

        restaurantGateway.delete(1L);

        verify(restaurantRepository, times(1)).deleteById(1L);
    }
}
