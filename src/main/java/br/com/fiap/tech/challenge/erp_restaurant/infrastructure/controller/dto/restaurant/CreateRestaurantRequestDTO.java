package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.restaurant;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.address.AddressRequestDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateRestaurantRequestDTO(
		
		@NotEmpty(message = "name must be not null or empty") 
		String name,
		
		@NotEmpty(message = "kitchen type must be not null or empty") 
		String kitchenType,
		
		@NotNull(message = "openningAt must be not null or empty")
		LocalTime openingAt ,
		
		@NotNull(message = "closingAt must be not null or empty")
		LocalTime closingAt,
		
		@NotNull(message = "ownerId must be not null or empty")
		Long ownerId
		) {

}

