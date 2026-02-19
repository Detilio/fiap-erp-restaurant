package br.com.fiap.tech.challenge.erp_restaurant.application.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(String string) {
		super(string);
	}

}
