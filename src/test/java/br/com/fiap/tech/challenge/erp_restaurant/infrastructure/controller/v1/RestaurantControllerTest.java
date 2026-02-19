package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.v1;

import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.restaurant.RestaurantUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.Restaurant;
import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.restaurant.CreateRestaurantRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.restaurant.RestaurantResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.mapper.RestaurantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    @Mock
    private RestaurantUseCase restaurantUseCase;

    @InjectMocks
    private RestaurantController restaurantController;

    private RestaurantMapper mapper = RestaurantMapper.INSTANCE;

    private CreateRestaurantRequestDTO createRestaurantRequestDTO;
    private Restaurant restaurantDomain;
    private RestaurantResponseDTO restaurantResponseDTO;

    @BeforeEach
    void setup() {
        createRestaurantRequestDTO = new CreateRestaurantRequestDTO(
                "Pizzaria",
                "Italiana",
                LocalTime.of(11, 0),
                LocalTime.of(23, 0),
                99L
        );

        User owner = User.builder()
                .id(99L)
                .name("João Dono")
                .build();

        restaurantDomain = Restaurant.builder()
                .id(1L)
                .name("Pizzaria")
                .kitchenType("Italiana")
                .openingAt(LocalTime.of(11, 0))
                .closingAt(LocalTime.of(23, 0))
                .owner(owner)
                .build();

        restaurantResponseDTO = mapper.domainToDTO(restaurantDomain);
    }

    @Test
    @DisplayName("Deve criar restaurante e retornar um DTO")
    void createRestaurant_shouldReturnRestaurantResponseDTO() {
        // Converter dto para domain para simular retorno do UseCase
        Restaurant domainFromDto = mapper.dtoToDomain(createRestaurantRequestDTO);
        domainFromDto.setOwner(new User(createRestaurantRequestDTO.ownerId(), null, null, null, null, null));
        domainFromDto = Restaurant.builder()
                .id(1L)
                .name(domainFromDto.getName())
                .kitchenType(domainFromDto.getKitchenType())
                .openingAt(domainFromDto.getOpeningAt())
                .closingAt(domainFromDto.getClosingAt())
                .owner(domainFromDto.getOwner())
                .build();

        when(restaurantUseCase.save(any(Restaurant.class))).thenReturn(domainFromDto);

        RestaurantResponseDTO result = restaurantController.createRestaurant(createRestaurantRequestDTO);

        assertNotNull(result);
        assertEquals(domainFromDto.getId(), result.getId());
        assertEquals(domainFromDto.getName(), result.getName());
        assertEquals(domainFromDto.getKitchenType(), result.getKitchenType());
        assertEquals(domainFromDto.getOpeningAt(), result.getOpeningAt());
        assertEquals(domainFromDto.getClosingAt(), result.getClosingAt());
        assertEquals(domainFromDto.getOwner().getName(), result.getOwner());

        verify(restaurantUseCase, times(1)).save(any(Restaurant.class));
    }

    @Test
    @DisplayName("Deve listar todpos os restaurantes e retornar uma lista de DTO")
    void findAll_shouldReturnListOfRestaurantResponseDTO() {
        List<Restaurant> domainList = List.of(restaurantDomain);
        when(restaurantUseCase.findAll(0, 10)).thenReturn(domainList);

        List<RestaurantResponseDTO> responseList = restaurantController.findAll(0, 10);

        assertNotNull(responseList);
        assertEquals(1, responseList.size());
        assertEquals(restaurantDomain.getId(), responseList.get(0).getId());
        assertEquals(restaurantDomain.getName(), responseList.get(0).getName());

        verify(restaurantUseCase, times(1)).findAll(0, 10);
    }

    @Test
    @DisplayName("Deve retornar o restaurante do dono em um DTO")
    void getUser_shouldReturnRestaurantResponseDTO() {
        when(restaurantUseCase.findById(1L)).thenReturn(restaurantDomain);

        RestaurantResponseDTO responseDTO = restaurantController.getUser(1L);

        assertNotNull(responseDTO);
        assertEquals(restaurantDomain.getId(), responseDTO.getId());
        assertEquals(restaurantDomain.getName(), responseDTO.getName());

        verify(restaurantUseCase, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve deletar o restaurante")
    void delete_shouldCallDeleteUseCase() {
        Long idToDelete = 1L;

        doNothing().when(restaurantUseCase).delete(idToDelete);

        restaurantController.delete(idToDelete);

        verify(restaurantUseCase, times(1)).delete(idToDelete);
    }

    @Test
    @DisplayName("Deve alterar o restaurante e retornar um DTO")
    void update_shouldReturnUpdatedRestaurantResponseDTO() {
        CreateRestaurantRequestDTO updateDto = new CreateRestaurantRequestDTO(
                "Pizzaria Atualizada",
                "Italiana",
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                99L
        );

        Restaurant updatedDomain = mapper.dtoToDomain(updateDto);
        updatedDomain.setOwner(new User(updateDto.ownerId(), null, null, null, null, null));
        updatedDomain = Restaurant.builder()
                .id(1L)
                .name(updatedDomain.getName())
                .kitchenType(updatedDomain.getKitchenType())
                .openingAt(updatedDomain.getOpeningAt())
                .closingAt(updatedDomain.getClosingAt())
                .owner(updatedDomain.getOwner())
                .build();

        when(restaurantUseCase.update(any(Restaurant.class))).thenReturn(updatedDomain);

        RestaurantResponseDTO result = restaurantController.update(updateDto);

        assertNotNull(result);
        assertEquals(updatedDomain.getId(), result.getId());
        assertEquals(updatedDomain.getName(), result.getName());

        verify(restaurantUseCase, times(1)).update(any(Restaurant.class));
    }
}
