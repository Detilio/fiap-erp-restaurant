package br.com.fiap.tech.challenge.erp_restaurant.application.usecase.restaurant;

import java.util.List;

import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.Restaurant;

public interface SearchRestaurantUseCase {
	
	Restaurant findById(Long id);
	
	List<Restaurant> findAll(int size , int page);

}
