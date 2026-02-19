package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.dish;

import java.math.BigDecimal;

public record UpdateDishRequestDTO(
		int id,
		String name,
		String description,
		BigDecimal price,
		Boolean onlyToGo,
		String photo) {
}
