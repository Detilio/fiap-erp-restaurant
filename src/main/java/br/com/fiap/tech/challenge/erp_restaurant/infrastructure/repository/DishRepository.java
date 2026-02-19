package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.DishEntity;

@Repository
public interface DishRepository extends JpaRepository<DishEntity, Long>{


}
