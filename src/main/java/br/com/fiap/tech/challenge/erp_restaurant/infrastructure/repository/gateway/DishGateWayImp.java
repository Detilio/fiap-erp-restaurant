package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.repository.gateway;

import static br.com.fiap.tech.challenge.erp_restaurant.mapper.DishMapper.INSTANCE;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.fiap.tech.challenge.erp_restaurant.application.gateway.restaurant.DishGateway;
import br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant.menu.Dish;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.DishEntity;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.repository.DishRepository;

@Service
public class DishGateWayImp implements DishGateway {

	private final DishRepository dishRepository;

	public DishGateWayImp(DishRepository dishRepository) {
		this.dishRepository = dishRepository;
	}

	@Override
	public Dish create(Dish dish) {
		DishEntity entity = INSTANCE.domainToEntity(dish);
		return INSTANCE.entityToDomain(dishRepository.save(entity));
	}

	@Override
	public Dish update(Dish dish) {
		return this.create(dish);
	}

	@Override
	public void delete(long id) {
		dishRepository.deleteById(id);
		
	}

	@Override
	public Optional<Dish> findById(Long id) {
		Optional<DishEntity> d = this.dishRepository.findById(id);
        return d.isPresent() ? Optional.of(INSTANCE.entityToDomain(d.get())) : Optional.empty();

	}

	@Override
	public List<Dish> findAll(int size, int page) {
		Page<DishEntity> all = dishRepository.findAll(PageRequest.of(size, page));
        return all.stream().map(INSTANCE::entityToDomain).toList();
	}

}
