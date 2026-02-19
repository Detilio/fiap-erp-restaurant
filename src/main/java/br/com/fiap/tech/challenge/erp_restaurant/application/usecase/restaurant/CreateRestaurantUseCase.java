package br.com.fiap.tech.challenge.erp_restaurant.application.usecase.restaurant;

import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.Restaurant;

public interface CreateRestaurantUseCase {

	Restaurant save(Restaurant restaurant);
}
