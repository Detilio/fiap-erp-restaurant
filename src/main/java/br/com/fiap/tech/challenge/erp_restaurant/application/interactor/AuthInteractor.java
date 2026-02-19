package br.com.fiap.tech.challenge.erp_restaurant.application.interactor;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.fiap.tech.challenge.erp_restaurant.application.exception.AuthenticationException;
import br.com.fiap.tech.challenge.erp_restaurant.application.gateway.UserGateway;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.auth.AuthUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;

@Service
public class AuthInteractor implements AuthUseCase{
	
	
	private final UserGateway userGateway;

	public AuthInteractor(UserGateway userGateway) {
		this.userGateway = userGateway;
	}
	
	@Override
	public User authenticate(User u) {
		User user = Optional.ofNullable(this.userGateway.findByEmailOrLogin(u.getEmail(), u.getLogin()))
				.orElseThrow(() -> new AuthenticationException(u.getLogin() + " is not in here")) ;

		if (!user.getPassword().equals(u.getPassword())) 
			throw new AuthenticationException("not authorized to enter here");
		return user;
	}

}
