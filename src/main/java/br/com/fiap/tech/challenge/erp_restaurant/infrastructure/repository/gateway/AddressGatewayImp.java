package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.repository.gateway;

import static br.com.fiap.tech.challenge.erp_restaurant.mapper.AddressMapper.INSTANCE;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.fiap.tech.challenge.erp_restaurant.application.gateway.AddressGateway;
import br.com.fiap.tech.challenge.erp_restaurant.domain.address.Address;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.AddressEntity;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.repository.AddressRepository;

@Component
public class AddressGatewayImp implements AddressGateway {

	private final AddressRepository addressRepository;

	public AddressGatewayImp(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	public Address findById(Long id) {
		Optional<AddressEntity> a = addressRepository.findById(id);
		return a.isPresent() ? INSTANCE.toDomain(a.get()) : null;
	}

	public Address save(Address userAddres) {
		return INSTANCE.toDomain(addressRepository.save(INSTANCE.toEntity(userAddres)));
	}

	public void delete(Long idAddress) {
		addressRepository.deleteById(idAddress);
	}

}