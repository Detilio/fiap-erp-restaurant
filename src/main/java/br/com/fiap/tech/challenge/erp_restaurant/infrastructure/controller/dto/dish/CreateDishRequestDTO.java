package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.dish;

import java.math.BigDecimal;

public record CreateDishRequestDTO(
		String name,
		String description,
		BigDecimal price,
		Boolean onlyToGo,
		String photo) {}