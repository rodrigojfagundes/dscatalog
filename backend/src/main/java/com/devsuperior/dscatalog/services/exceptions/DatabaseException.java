package com.devsuperior.dscatalog.services.exceptions;

//classe DATABASEEXCEPTION que vai servir para DAR UMA EXCESSAO
//quando nos PEDIRMOS para DELETAR UMA CATEGORY q NÂO PODE SER DELETADA
public class DatabaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	//criando o construtor q recebe uma mensagem
	public DatabaseException(String msg) {
		//e repassa a mensage para a CLASSE MAE, a SUPER CLASSE
		//no caso a RUNTIMEEXCEPTION
		super(msg);
	}
}
