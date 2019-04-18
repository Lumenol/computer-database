package com.business.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(long id) {
	this(id, null);
    }

    public CompanyNotFoundException(long id, Throwable cause) {
	super(String.format("La compagnie avec l'id %d est introuvable.", id), cause);
    }

}
