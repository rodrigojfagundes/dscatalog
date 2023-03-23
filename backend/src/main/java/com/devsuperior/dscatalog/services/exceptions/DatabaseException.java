package com.devsuperior.dscatalog.services.exceptions;

//classe DATABASEEXCEPTION que vai servir para DAR UMA EXCESSAO
//quando nos PEDIRMOS para DELETAR UMA CATEGORY q NÂO PODE SER DELETADA
//pois se DELETAR ELA data INCONSISTENCIA no BANCO...

public class DatabaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DatabaseException(String msg) {

		super(msg);
	}
}
