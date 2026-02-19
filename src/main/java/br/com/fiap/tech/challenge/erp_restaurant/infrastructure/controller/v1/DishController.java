package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.v1;

import static br.com.fiap.tech.challenge.erp_restaurant.mapper.DishMapper.INSTANCE;

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

import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.dish.CreateDishUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.dish.DeleteDishUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.dish.SearchDishUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.dish.UpdateDishUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.menu.Dish;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.dish.CreateDishRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.dish.DishResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.dish.UpdateDishRequestDTO;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("v1/dishes")
public class DishController {

	private CreateDishUseCase createDishUseCase;
	private UpdateDishUseCase updateDishUseCase;
	private DeleteDishUseCase deleteDishUseCase;
	private SearchDishUseCase searchDishUseCase;

	public DishController(CreateDishUseCase createDishUseCase,
			UpdateDishUseCase updateDishUseCase,
			DeleteDishUseCase deleteDishUseCase,
			SearchDishUseCase searchDishUseCase) {
		this.createDishUseCase = createDishUseCase;
		this.updateDishUseCase = updateDishUseCase;
		this.deleteDishUseCase = deleteDishUseCase;
		this.searchDishUseCase = searchDishUseCase;
	}


	@PostMapping("/restaurant/{restaurantId}")
	public DishResponseDTO createDish(@RequestBody @Valid CreateDishRequestDTO dishRequestDTO,
			@PathVariable long restaurantId) {
		log.info("receiving dish to create", dishRequestDTO);
		Dish dish = INSTANCE.dtoToDomain(dishRequestDTO);
		return INSTANCE.domainToDTO(createDishUseCase.create(restaurantId, dish));
	}
	
	@GetMapping("{id}")
	public DishResponseDTO getDish(@PathVariable Long id) {
		return INSTANCE.domainToDTO(searchDishUseCase.findById(id));
	}
	
	@GetMapping
	public List<DishResponseDTO> findAll(@RequestParam int page, @RequestParam int size) {
		log.info("find all restaurants list page {} size {}", page, size);
		return searchDishUseCase.findAll(page, size).stream().map(INSTANCE::domainToDTO).toList();
	}
	
    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        log.info("receiving dish id {} to delete", id);
        deleteDishUseCase.deleteById(id);
    }
    
    @PutMapping
    public DishResponseDTO update (@RequestBody UpdateDishRequestDTO dishRequestDTO) {
        log.info("receiving dish to update", dishRequestDTO);
        return INSTANCE.domainToDTO(updateDishUseCase.update(INSTANCE.dtoToDomain(dishRequestDTO)));
    }

}
