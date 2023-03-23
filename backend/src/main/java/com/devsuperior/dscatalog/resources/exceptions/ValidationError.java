package com.devsuperior.dscatalog.resources.exceptions;

import java.util.ArrayList;
import java.util.List;


//classe VALIDATIONERROR q ira HERDAR/EXTENDS os metodos/atributos
//da classe STANDARERROR(erros padroes)...

public class ValidationError extends StandardError{
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();
	
	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
}
