package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.address;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressResponseDTO {
    private Long id;
    private String state;
    private String city;
    private String street;
    private int number;
    private String complement;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

}
