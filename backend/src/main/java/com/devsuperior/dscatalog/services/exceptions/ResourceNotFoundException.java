package com.devsuperior.dscatalog.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	//criando o construtor q recebe uma mensagem
	public ResourceNotFoundException(String msg) {
		//e repassa a mensage para a CLASSE MAE, a SUPER CLASSE
		//no caso a RUNTIMEEXCEPTION
		super(msg);
	}
}
