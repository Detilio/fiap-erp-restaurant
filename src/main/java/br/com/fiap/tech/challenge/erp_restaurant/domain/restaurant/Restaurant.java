package br.com.fiap.tech.challenge.erp_restaurant.domain.restaurant;

import java.time.LocalTime;

import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Restaurant {

	private Long id;
	private String name;
	private String kitchenType;
	private LocalTime openingAt;
	private LocalTime closingAt;
	private User owner;
	
	
	public void setOwner(User owner) {
		this.owner = owner;
	}

	

}
