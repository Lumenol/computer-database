package com.metier.exception;

public class CompagnyNotFoundException extends RuntimeException {
	public CompagnyNotFoundException(long id) {
		super(String.format("La compagnie avec l'id %d est introuvable.", id));
	}
}
