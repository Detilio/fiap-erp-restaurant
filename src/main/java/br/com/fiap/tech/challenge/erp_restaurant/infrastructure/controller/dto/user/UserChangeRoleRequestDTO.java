package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user;

import br.com.fiap.tech.challenge.erp_restaurant.shared.Role;

public record UserChangeRoleRequestDTO(

		int id,

		Role role

) {

}
