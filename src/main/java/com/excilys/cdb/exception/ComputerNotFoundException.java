package com.excilys.cdb.exception;

public class ComputerNotFoundException extends RuntimeException {
    public ComputerNotFoundException(long id) {
	this(id, null);
    }

    public ComputerNotFoundException(long id, Throwable cause) {
	super(String.format("L'ordinateur avec l'id %d est introuvable.", id), cause);
    }
}
