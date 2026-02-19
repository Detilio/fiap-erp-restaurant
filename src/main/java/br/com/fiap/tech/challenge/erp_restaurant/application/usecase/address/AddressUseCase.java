package br.com.fiap.tech.challenge.erp_restaurant.application.usecase.address;

import br.com.fiap.tech.challenge.erp_restaurant.domain.address.Address;

public interface AddressUseCase {

	Address create(Address a);

	Address findById(Long id);

	Address update(Long id, Address address);
	
	void delete(Long id);

}
