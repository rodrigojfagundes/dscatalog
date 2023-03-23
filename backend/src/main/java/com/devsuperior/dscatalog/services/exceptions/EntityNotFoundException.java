package com.devsuperior.dscatalog.services.exceptions;

public class EntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	//criando o construtor q recebe uma mensagem
	public EntityNotFoundException(String msg) {

		super(msg);
	}
}
