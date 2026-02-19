package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserRequestDTO(
		
		int id,

		@NotEmpty(message = "name must be not null or empty") 
		String name,

		@NotEmpty(message = "email must be not null or empty") 
		@Email(message = "email is not valid") 
		String email,

		@NotEmpty(message = "login must be not null or empty") 
		String login,

		@NotEmpty(message = "password must be not null or empty") 
		String password,
			
		Long address) {

}
