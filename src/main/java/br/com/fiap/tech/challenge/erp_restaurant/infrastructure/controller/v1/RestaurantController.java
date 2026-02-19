package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.v1;

import static br.com.fiap.tech.challenge.erp_restaurant.mapper.RestaurantMapper.INSTANCE;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.restaurant.RestaurantUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.restaurant.CreateRestaurantRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.restaurant.RestaurantResponseDTO;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("v1/restaurant")
public class RestaurantController {

	private RestaurantUseCase restaurantUseCase;

	public RestaurantController(RestaurantUseCase restaurantUseCase) {
		this.restaurantUseCase = restaurantUseCase;
	}

	@PostMapping
	public RestaurantResponseDTO createRestaurant(@RequestBody @Valid CreateRestaurantRequestDTO restaurantDto) {
		log.info("receiving restaurant to create", restaurantDto);
		return INSTANCE.domainToDTO(restaurantUseCase.save(INSTANCE.dtoToDomain(restaurantDto)));
	}
	
	@GetMapping
	public List<RestaurantResponseDTO> findAll(@RequestParam int page, @RequestParam int size) {
		log.info("find all restaurants list page {} size {}", page, size);
		return restaurantUseCase.findAll(page, size).stream().map(INSTANCE::domainToDTO).toList();
	}

	@GetMapping("{id}")
	public RestaurantResponseDTO getUser(@PathVariable Long id) {
		return INSTANCE.domainToDTO(restaurantUseCase.findById(id));
	}
	
    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        log.info("receiving restaurant id {} to delete", id);
        restaurantUseCase.delete(id);
    }
    
    @PutMapping
    public RestaurantResponseDTO update (@RequestBody CreateRestaurantRequestDTO restaurantDto) {
        log.info("receiving restaurant to update", restaurantDto);
        return INSTANCE.domainToDTO(restaurantUseCase.update(INSTANCE.dtoToDomain(restaurantDto)));
    }


}
