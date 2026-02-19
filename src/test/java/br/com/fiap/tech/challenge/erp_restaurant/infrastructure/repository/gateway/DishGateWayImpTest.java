package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.repository.gateway;

import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.menu.Dish;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.DishEntity;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.repository.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DishGateWayImpTest {

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private DishGateWayImp dishGateway;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar prato")
    void testCreateDish() throws Exception {
        Dish dish = new Dish("Pizza", "Delicious", new BigDecimal("25.00"), true, new URL("http://image.com/pizza.jpg"));
        dish.setId(1L);

        DishEntity entity = new DishEntity();
        entity.setId(1L);
        entity.setName("Pizza");
        entity.setDescription("Delicious");
        entity.setPrice(new BigDecimal("25.00"));
        entity.setOnlyToGo(true);
        entity.setPhoto(new URL("http://image.com/pizza.jpg"));

        when(dishRepository.save(any())).thenReturn(entity);

        Dish result = dishGateway.create(dish);

        assertNotNull(result);
        assertEquals("Pizza", result.getName());
        verify(dishRepository).save(any(DishEntity.class));
    }

    @Test
    @DisplayName("Deve alterar o prato")
    void testUpdateDish() throws Exception {
        Dish dish = new Dish("Lasanha", "Carne e queijo", new BigDecimal("30.00"), false, new URL("http://image.com/lasanha.jpg"));
        dish.setId(2L);

        DishEntity entity = new DishEntity();
        entity.setId(2L);
        entity.setName("Lasanha");
        entity.setDescription("Carne e queijo");
        entity.setPrice(new BigDecimal("30.00"));
        entity.setOnlyToGo(false);
        entity.setPhoto(new URL("http://image.com/lasanha.jpg"));

        when(dishRepository.save(any())).thenReturn(entity);

        Dish result = dishGateway.update(dish);

        assertNotNull(result);
        assertEquals("Lasanha", result.getName());
        verify(dishRepository).save(any(DishEntity.class));
    }

    @Test
    @DisplayName("Deve excluir o prato")
    void testDeleteDish() {
        long id = 10L;

        doNothing().when(dishRepository).deleteById(id);

        dishGateway.delete(id);

        verify(dishRepository).deleteById(id);
    }

    @Test
    @DisplayName("Deve buscar o prato pelo id")
    void testFindById_found() throws Exception {
        long id = 1L;

        DishEntity entity = new DishEntity();
        entity.setId(id);
        entity.setName("Sushi");
        entity.setDescription("Peixe fresco");
        entity.setPrice(new BigDecimal("45.00"));
        entity.setOnlyToGo(false);
        entity.setPhoto(new URL("http://image.com/sushi.jpg"));

        when(dishRepository.findById(id)).thenReturn(Optional.of(entity));

        Optional<Dish> result = dishGateway.findById(id);

        assertTrue(result.isPresent());
        assertEquals("Sushi", result.get().getName());
        verify(dishRepository).findById(id);
    }

    @Test
    @DisplayName("Deve tentar buscar um id inexistente")
    void testFindById_notFound() {
        long id = 999L;

        when(dishRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Dish> result = dishGateway.findById(id);

        assertFalse(result.isPresent());
        verify(dishRepository).findById(id);
    }

    @Test
    @DisplayName("Deve buscar todos os pratos")
    void testFindAll() throws Exception {
        DishEntity entity1 = new DishEntity();
        entity1.setId(1L);
        entity1.setName("Pizza");
        entity1.setDescription("Mussarela");
        entity1.setPrice(new BigDecimal("25.00"));
        entity1.setOnlyToGo(true);
        entity1.setPhoto(new URL("http://image.com/pizza.jpg"));

        DishEntity entity2 = new DishEntity();
        entity2.setId(2L);
        entity2.setName("Burger");
        entity2.setDescription("Bacon");
        entity2.setPrice(new BigDecimal("22.00"));
        entity2.setOnlyToGo(false);
        entity2.setPhoto(new URL("http://image.com/burger.jpg"));

        List<DishEntity> list = List.of(entity1, entity2);
        Page<DishEntity> page = new PageImpl<>(list);

        when(dishRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        List<Dish> result = dishGateway.findAll(0, 10);

        assertEquals(2, result.size());
        assertEquals("Pizza", result.get(0).getName());
        assertEquals("Burger", result.get(1).getName());

        verify(dishRepository).findAll(PageRequest.of(0, 10));
    }
}
