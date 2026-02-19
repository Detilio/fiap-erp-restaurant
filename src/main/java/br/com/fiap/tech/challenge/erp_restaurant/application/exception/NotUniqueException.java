package br.com.fiap.tech.challenge.erp_restaurant.application.exception;

public class NotUniqueException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotUniqueException(String message) {
        super(message);
    }
}
