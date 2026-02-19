package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.v1;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.auth.AuthUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.auth.AuthRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.mapper.UserMapper;
import jakarta.validation.Valid;

@RestController
@RequestMapping("v1/auth")
public class AuthenticationController {

	private AuthUseCase authUseCase;

	public AuthenticationController(AuthUseCase authService) {
		this.authUseCase = authService;
	}

	@PostMapping
	public void auth(@RequestBody @Valid AuthRequestDTO dto) {

		authUseCase.authenticate(UserMapper.INSTANCE.authDtoToDomain(dto));

	}

}
