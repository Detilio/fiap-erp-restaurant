package br.com.fiap.tech.challenge.erp_restaurant.application.usecase.dish;

import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.menu.Dish;

public interface CreateDishUseCase {

	Dish create(long restaurantId, Dish dish);
}
