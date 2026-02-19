package br.com.fiap.tech.challenge.erp_restaurant.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;

import br.com.fiap.tech.challenge.erp_restaurant.application.exception.RoleException;

public enum Role {

	CUSTOMER, OWNER;

	@JsonCreator
	public static Role fromString(String value) {
		for (Role r : Role.values()) {
			if (r.name().equals(value))
				return r;
		}
		throw new RoleException("unkonw role "+value+" do not match these " + Arrays.toString(Role.values()));
	}

}
