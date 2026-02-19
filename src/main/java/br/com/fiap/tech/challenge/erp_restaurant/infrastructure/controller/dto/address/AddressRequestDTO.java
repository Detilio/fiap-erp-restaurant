package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.address;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record AddressRequestDTO(
        
        @NotEmpty(message = "state must be not null or empty")
        String state,
        
        @NotEmpty(message = "city must be not null or empty")
        String city,
        
        @NotEmpty(message = "street must be not null or empty")
        String street,
       
        //@NotEmpty(message = "number must be not null or empty")
        @NotNull(message = "number must be not null")
        @Positive(message = "number must be positive")
        int number,
        
        String complement) {
}
