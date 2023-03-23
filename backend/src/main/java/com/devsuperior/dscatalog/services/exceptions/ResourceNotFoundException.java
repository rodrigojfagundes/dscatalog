package com.devsuperior.dscatalog.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	
	//criando o construtor q recebe uma mensagem
	private static final long serialVersionUID = 1L;
	//e repassa a mensage para a CLASSE MAE, a SUPER CLASSE
	//no caso a RUNTIMEEXCEPTION
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
