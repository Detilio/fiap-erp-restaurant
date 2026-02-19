package br.com.fiap.tech.challenge.erp_restaurant.application.gateway.restaurant;

import java.util.List;
import java.util.Optional;

import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.menu.Dish;

public interface DishGateway {

	Dish create(Dish dish);

	Dish update(Dish dish);

	void delete(long id);

	 Optional<Dish> findById(Long id);

	 List<Dish> findAll(int size, int page);
}
