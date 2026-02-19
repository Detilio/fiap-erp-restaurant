package br.com.fiap.tech.challenge.erp_restaurant.application.gateway.restaurant;

import java.util.List;
import java.util.Optional;

import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.Restaurant;

public interface RestaurantGateway {

	Restaurant save(Restaurant restaurant);

	Optional<Restaurant> findById(Long id);

	List<Restaurant> findAll(int size, int page);

	void delete(Long id);

}
