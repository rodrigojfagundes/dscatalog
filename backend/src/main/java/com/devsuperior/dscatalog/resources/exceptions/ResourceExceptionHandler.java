package com.devsuperior.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

//para nao precisar IMPLEMENTAR BLOCO TRY CATCH em todos os METODOS
//do CONTROLADOR, vamos criar a CLASSE RESOURCEEXCEPTIONSHANDLER

@ControllerAdvice
public class ResourceExceptionHandler {
	
	//criando um metodo q trate a EXCEPTION ResourceNotFoundException

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;

		StandardError err = new StandardError();

		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}	
	
	
	//criando um metodo para tratar a DATABASEEXCEPTION
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError();
		
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Database exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}	

	
	//criando um metodo para tratar a METHODARGUMENTNOTVALIDEXCEPTION
	//q é a EXCESSAO de quando nos CAD um PRODUTO com o NOME DE TAMANHO
	//MTO PEQ ou GRANDE
	
		@ExceptionHandler(MethodArgumentNotValidException.class)
		public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

			HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
			ValidationError err = new ValidationError();

			err.setTimestamp(Instant.now());
			err.setStatus(status.value());
			err.setError("Validation exception");
			err.setMessage(e.getMessage());
			err.setPath(request.getRequestURI());
			
			for(FieldError f : e.getBindingResult().getFieldErrors()) {
				err.addError(f.getField(), f.getDefaultMessage());
			}
			return ResponseEntity.status(status).body(err);
		}
}