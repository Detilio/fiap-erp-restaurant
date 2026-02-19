package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.AddressEntity;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long>{


}
