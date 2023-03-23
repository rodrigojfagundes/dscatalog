package com.devsuperior.dscatalog.services.exceptions;
//
//
//classe RESOURCENOTFOUNDEXCEPTION que vai servir para DAR UMA EXCESSAO
//quando nos PEDIRMOS UM ID DE CATEGORY ESPECIFICO e esse ID NAO EXISTIR
	//essa classe HERDA os metodos da RUNTIMEEXCEPTIONS
public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	//criando o construtor q recebe uma mensagem
	public ResourceNotFoundException(String msg) {
		//e repassa a mensage para a CLASSE MAE, a SUPER CLASSE
		//no caso a RUNTIMEEXCEPTION
		super(msg);
	}
}
