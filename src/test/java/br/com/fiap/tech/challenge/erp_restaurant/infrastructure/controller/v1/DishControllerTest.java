package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.v1;

import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.dish.CreateDishUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.dish.DeleteDishUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.dish.SearchDishUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.dish.UpdateDishUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.Restaurant;
import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.menu.Dish;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.dish.CreateDishRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.dish.DishResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.dish.UpdateDishRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.mapper.DishMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishControllerTest {

    @Mock
    private CreateDishUseCase createDishUseCase;

    @Mock
    private UpdateDishUseCase updateDishUseCase;

    @Mock
    private DeleteDishUseCase deleteDishUseCase;

    @Mock
    private SearchDishUseCase searchDishUseCase;

    @Mock
    private br.com.fiap.tech.challenge.erp_restaurant.mapper.DishMapper dishMapper;

    @InjectMocks
    private DishController dishController;
    private DishMapper mapper = DishMapper.INSTANCE;
    private CreateDishRequestDTO createDishRequestDTO;
    private Dish dishDomain;
    private DishResponseDTO dishResponseDTO;

    @BeforeEach
    void setup() throws MalformedURLException {
        createDishRequestDTO = new CreateDishRequestDTO(
                "Pizza Margherita",
                "Delicious classic pizza",
                BigDecimal.valueOf(35.5),
                false,
                "http://photo.url/pizza.jpg"
        );

        dishDomain = Dish.builder()
                .id(1L)
                .name("Pizza Margherita")
                .description("Delicious classic pizza")
                .price(BigDecimal.valueOf(35.5))
                .onlyToGo(false)
                .photo(new URL("http://photo.url/pizza.jpg"))
                .restaurant(new Restaurant(1L, "Pizzaria do Bairro", "Italiana", LocalTime.of(12, 0, 0), LocalTime.of(23, 59, 59), null))
                .build();

        dishResponseDTO = new DishResponseDTO(
                1L,
                "Pizza Margherita",
                "Delicious classic pizza",
                BigDecimal.valueOf(35.5),
                false,
                "http://photo.url/pizza.jpg",
                "Pizzaria do Bairro"
        );
    }

    @Test
    @DisplayName("Deve criar prato e retornar um DTO")
    void createDish_shouldReturnDishResponseDTO() throws MalformedURLException {
        Dish domainFromDto = mapper.dtoToDomain(createDishRequestDTO);
        domainFromDto.setId(1L);
        domainFromDto.setRestaurant(new Restaurant(1L, "Pizzaria do Bairro", "Italiana", LocalTime.of(12, 0, 0), LocalTime.of(23, 59, 59), null));

        when(createDishUseCase.create(anyLong(), any(Dish.class))).thenReturn(domainFromDto);

        DishResponseDTO result = dishController.createDish(createDishRequestDTO, 1L);

        assertNotNull(result);
        assertEquals(domainFromDto.getId(), result.id());
        assertEquals(domainFromDto.getName(), result.name());
        assertEquals(domainFromDto.getDescription(), result.description());
        assertEquals(domainFromDto.getPrice(), result.price());
        assertEquals(domainFromDto.getOnlyToGo(), result.onlyToGo());
        assertEquals(domainFromDto.getPhoto().toString(), result.photo());
        assertEquals(domainFromDto.getRestaurant().getName(), result.restaurant());

        verify(createDishUseCase, times(1)).create(eq(1L), any(Dish.class));
    }

    @Test
    @DisplayName("Deve obter prato e retornar um DTO")
    void getDish_shouldReturnDishResponseDTO() throws MalformedURLException {
        when(searchDishUseCase.findById(1L)).thenReturn(dishDomain);

        DishResponseDTO result = dishController.getDish(1L);

        assertNotNull(result);
        assertEquals(dishDomain.getId(), result.id());
        assertEquals(dishDomain.getName(), result.name());
        assertEquals(dishDomain.getDescription(), result.description());
        assertEquals(dishDomain.getPrice(), result.price());
        assertEquals(dishDomain.getOnlyToGo(), result.onlyToGo());
        assertEquals(dishDomain.getPhoto().toString(), result.photo());
        assertEquals(dishDomain.getRestaurant().getName(), result.restaurant());

        verify(searchDishUseCase, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve buscar todos os pratos e retornar uma lista de DTO")
    void findAll_shouldReturnListDishResponseDTO() {
        List<Dish> domainList = List.of(dishDomain);
        when(searchDishUseCase.findAll(0, 10)).thenReturn(domainList);

        List<DishResponseDTO> resultList = dishController.findAll(0, 10);

        assertNotNull(resultList);
        assertEquals(1, resultList.size());
        assertEquals(dishDomain.getId(), resultList.get(0).id());
        assertEquals(dishDomain.getName(), resultList.get(0).name());

        verify(searchDishUseCase, times(1)).findAll(0, 10);
    }

    @Test
    @DisplayName("Deve alterar o prato e retornar um DTO")
    void updateDish_shouldReturnDishResponseDTO() {
        UpdateDishRequestDTO updateDto = new UpdateDishRequestDTO(
                1,
                "Pizza Pepperoni",
                "Spicy pepperoni pizza",
                BigDecimal.valueOf(40.0),
                true,
                "http://photo.url/pepperoni.jpg"
        );

        Dish updatedDishDomain = mapper.dtoToDomain(updateDto);
        updatedDishDomain.setId(1L);
        updatedDishDomain.setRestaurant(new Restaurant(1L, "Pizzaria do Bairro", "Italiana", LocalTime.of(12, 0, 0), LocalTime.of(23, 59, 59), null));

        when(updateDishUseCase.update(any(Dish.class))).thenReturn(updatedDishDomain);

        DishResponseDTO result = dishController.update(updateDto);

        assertNotNull(result);
        assertEquals(updatedDishDomain.getId(), result.id());
        assertEquals(updatedDishDomain.getName(), result.name());

        verify(updateDishUseCase, times(1)).update(any(Dish.class));
    }

    @Test
    @DisplayName("Deve deleter o prato")
    void deleteDish_shouldCallDeleteUseCase() {
        Long idToDelete = 1L;

        doNothing().when(deleteDishUseCase).deleteById(idToDelete);

        dishController.delete(idToDelete);

        verify(deleteDishUseCase, times(1)).deleteById(idToDelete);
    }
}
