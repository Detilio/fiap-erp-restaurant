package br.com.fiap.tech.challenge.erp_restaurant.application.interactor.dish;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiap.tech.challenge.erp_restaurant.application.exception.NotFoundException;
import br.com.fiap.tech.challenge.erp_restaurant.application.gateway.restaurant.DishGateway;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.dish.CreateDishUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.dish.DeleteDishUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.dish.SearchDishUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.dish.UpdateDishUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.restaurant.RestaurantUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.menu.Dish;

@Service
public class DishUseInteractor implements CreateDishUseCase, SearchDishUseCase, UpdateDishUseCase, DeleteDishUseCase {

	private final DishGateway dishGateWay;

	private final RestaurantUseCase restaurantUseCase;

	public DishUseInteractor(DishGateway dishGateWay, RestaurantUseCase restaurantUseCase) {
		this.dishGateWay = dishGateWay;
		this.restaurantUseCase = restaurantUseCase;
	}

	@Override
	public Dish create(long restaurantId, Dish dish) {
		dish.setRestaurant(restaurantUseCase.findById(restaurantId));
		return dishGateWay.create(dish);
	}

	@Override
	public Dish findById(Long id) {
		 return dishGateWay.findById(id).orElseThrow(() -> new NotFoundException("Dish not found"));
	}

	@Override
	public List<Dish> findAll(int size, int page) {
		 return dishGateWay.findAll(size, page);
	}

	@Override
	public void deleteById(long id) {
		this.findById(id);
		dishGateWay.delete(id);

	}

	@Override
	public Dish update(Dish d) {
		Dish dish = findById(d.getId());
		dish.update(d.getName(),d.getDescription(),d.getOnlyToGo(),d.getPrice(),d.getPhoto());
		return dishGateWay.update(dish);
	}

}
