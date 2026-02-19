package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.auth;

import jakarta.validation.constraints.NotEmpty;

public record AuthRequestDTO(

		@NotEmpty(message = "email is required") 
		String login,
		@NotEmpty(message = "password is required") 
		String password

		) {

}
