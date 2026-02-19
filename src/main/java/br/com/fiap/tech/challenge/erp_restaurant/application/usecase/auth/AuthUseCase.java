package br.com.fiap.tech.challenge.erp_restaurant.application.usecase.auth;

import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;

public interface AuthUseCase {
	
	User authenticate(User user);
}
