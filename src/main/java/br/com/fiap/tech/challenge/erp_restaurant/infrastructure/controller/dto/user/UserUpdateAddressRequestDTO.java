package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user;

import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.address.AddressRequestDTO;
import jakarta.validation.Valid;

public record UserUpdateAddressRequestDTO(

		int id,

		@Valid
		AddressRequestDTO address) {

}
