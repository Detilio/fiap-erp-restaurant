package br.com.fiap.tech.challenge.erp_restaurant.application.gateway;

import br.com.fiap.tech.challenge.erp_restaurant.domain.address.Address;

public interface AddressGateway {

	public Address findById(Long id);

	public Address save(Address userAddres);

	public void delete(Long idAddress);
}
