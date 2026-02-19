package br.com.fiap.tech.challenge.erp_restaurant.application.interactor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.tech.challenge.erp_restaurant.application.exception.NotFoundException;
import br.com.fiap.tech.challenge.erp_restaurant.application.exception.RoleException;
import br.com.fiap.tech.challenge.erp_restaurant.application.gateway.restaurant.RestaurantGateway;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.restaurant.RestaurantUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.user.UserUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.Restaurant;
import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;
import br.com.fiap.tech.challenge.erp_restaurant.shared.Role;

@Service
public class RestaurantInteractor implements RestaurantUseCase {

	private final UserUseCase userUseCase;

	private final RestaurantGateway restaurantGateway;

	public RestaurantInteractor(UserUseCase userUseCase, RestaurantGateway restaurantGateway) {
		super();
		this.userUseCase = userUseCase;
		this.restaurantGateway = restaurantGateway;
	}

	@Override
	@Transactional
	public Restaurant save(Restaurant restaurant) {
		restaurant.setOwner(isUserOwner(restaurant.getOwner().getId()));
		return restaurantGateway.save(restaurant);
	}

	@Override
	public Restaurant findById(Long id) {
		return restaurantGateway.findById(id).orElseThrow(() -> new NotFoundException("Restaurant not found"));
	}

	@Override
	public List<Restaurant> findAll(int page, int size) {
		return restaurantGateway.findAll(page, size);
	}


	private User isUserOwner(Long userId) {
		User user = userUseCase.findUserById(userId);
		if (!user.getRole().equals(Role.OWNER))
			throw new RoleException("user id {" + userId + "} is not OWNER");
		return user;
	}

	@Override
	public Restaurant update(Restaurant restaurant) {
		findById(restaurant.getId());
		return restaurantGateway.save(restaurant);
	}

	@Override
	public void delete(Long id) {
		this.findById(id);
		restaurantGateway.delete(id);
	}

}
