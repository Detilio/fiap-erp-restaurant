package br.com.fiap.tech.challenge.erp_restaurant.application.usecase.dish;

import java.util.List;

import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.menu.Dish;

public interface SearchDishUseCase {

	Dish findById(Long id);
	
	List<Dish> findAll(int size , int page);
}
