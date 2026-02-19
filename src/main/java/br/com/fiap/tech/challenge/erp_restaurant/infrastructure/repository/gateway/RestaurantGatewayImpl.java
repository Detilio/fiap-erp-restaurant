package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.repository.gateway;

import static br.com.fiap.tech.challenge.erp_restaurant.mapper.RestaurantMapper.INSTANCE;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.fiap.tech.challenge.erp_restaurant.application.gateway.restaurant.RestaurantGateway;
import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.Restaurant;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.RestaurantEntity;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.repository.RestaurantRepository;

@Service
public class RestaurantGatewayImpl implements RestaurantGateway {

	private final RestaurantRepository restaurantRepository;

	public RestaurantGatewayImpl(RestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
	}

	@Override
	public Restaurant save(Restaurant restaurant) {
		RestaurantEntity entity = INSTANCE.domainToEntity(restaurant);
		return INSTANCE.entityToDomain(restaurantRepository.save(entity));
	}

	@Override
	public List<Restaurant> findAll(int page, int size) {
		Page<RestaurantEntity> all = restaurantRepository.findAll(PageRequest.of(page, size));
		return all.stream().map(INSTANCE::entityToDomain).toList();
	}

	@Override
	public Optional<Restaurant> findById(Long id) {
		Optional<RestaurantEntity> r = restaurantRepository.findById(id);
		return r.isPresent() ? Optional.of(INSTANCE.entityToDomain(r.get())) : Optional.empty();

	}

	@Override
	public void delete(Long id) {
		restaurantRepository.deleteById(id);
	}

}
