package com.devsuperior.dscatalog.services.exceptions;

public class DatabaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DatabaseException(String msg) {
		//e repassa a mensage para a CLASSE MAE, a SUPER CLASSE
		//no caso a RUNTIMEEXCEPTION
		super(msg);
	}
}
