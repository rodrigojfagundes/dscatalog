package com.devsuperior.dscatalog.services.exceptions;

//classe RESOURCENOTFOUNDEXCEPTION que vai servir para DAR UMA EXCESSAO
//quando nos PEDIRMOS UM ID DE CATEGORY ESPECIFICO e esse ID NAO EXISTIR
	//essa classe HERDA os metodos da RUNTIMEEXCEPTIONS
public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
