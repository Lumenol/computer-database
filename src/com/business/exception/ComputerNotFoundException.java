package com.business.exception;

public class ComputerNotFoundException extends RuntimeException {
    public ComputerNotFoundException(long id) {
	super(String.format("L'ordinateur avec l'id %d est introuvable.", id));
    }
}
