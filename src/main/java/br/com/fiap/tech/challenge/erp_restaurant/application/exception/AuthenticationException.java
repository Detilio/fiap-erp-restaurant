package br.com.fiap.tech.challenge.erp_restaurant.application.exception;

public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AuthenticationException (String s) {
		super(s);
	}

}
