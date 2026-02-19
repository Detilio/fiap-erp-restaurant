package br.com.fiap.tech.challenge.erp_restaurant.application.interactor;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.fiap.tech.challenge.erp_restaurant.application.exception.NotFoundException;
import br.com.fiap.tech.challenge.erp_restaurant.application.gateway.AddressGateway;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.address.AddressUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.domain.address.Address;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AddressInteractor implements AddressUseCase {

	private final AddressGateway addressGateway;

	public AddressInteractor(AddressGateway addressGateway) {
		this.addressGateway = addressGateway;
	}

	@Override
	public Address findById(Long id) {
		return Optional.ofNullable(addressGateway.findById(id))
				.orElseThrow(() -> new NotFoundException("this address doesn't exists"));
	}

	@Override
	public Address create(Address a) {
		return addressGateway.save(a);
	}

	public Address update(Long id, Address a) {

		Address userAddress = this.findById(id);

		userAddress.setState(a.getState());
		userAddress.setCity(a.getCity());
		userAddress.setStreet(a.getStreet());
		userAddress.setNumber(a.getNumber());
		userAddress.setComplement(a.getComplement());
		
		return addressGateway.save(userAddress);
	}

	@Override
	public void delete(Long id) {
		addressGateway.delete(id);
	}

}
