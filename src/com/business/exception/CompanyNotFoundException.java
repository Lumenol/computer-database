package com.business.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(long id) {
	super(String.format("La compagnie avec l'id %d est introuvable.", id));
    }
}
