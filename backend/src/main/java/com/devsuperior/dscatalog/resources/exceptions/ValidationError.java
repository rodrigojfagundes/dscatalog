package com.devsuperior.dscatalog.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

//
//
//classe VALIDATIONERROR q ira HERDAR/EXTENDS os metodos/atributos
//da classe STANDARERROR(erros padroes)...
//so q a CLASSE VALIDATIONERROR ela recebe uma LISTA DE FIELDMESSAGE
//para colocarmos junto aos ATRIBUTOS DA CLASSE STANDARDERROR
public class ValidationError extends StandardError{
	private static final long serialVersionUID = 1L;
	
	//declarando uma lista de nome ERRORS do tipo FIELDMESSAGE
	private List<FieldMessage> errors = new ArrayList<>();
	
	//GET para a lista de nome ERROS do tipo FIELDMESSAGE
	public List<FieldMessage> getErrors() {
		return errors;
	}
	
	//criando um metodo do tipo PUBLICO para podermos ADD
	//elementos/valores a essa LISTA
	//q ira receber 2 parametros:
	//FIELDNAME = nome do campo (ex priece, name, etc..)
	//MESSAGE = o nome da mensagem (ex preco tem q ser positivo)
	public void addError(String fieldName, String message) {
		//add os valores q vieram nos atributos acima
		//o fieldname e message na nossa lista de nome ERRORS
		//q é uma lista do TIPO FIELDMESSAGE
		errors.add(new FieldMessage(fieldName, message));
	}
}
