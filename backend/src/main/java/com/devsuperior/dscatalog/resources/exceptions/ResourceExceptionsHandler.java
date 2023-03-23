package com.devsuperior.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;


@ControllerAdvice
public class ResourceExceptionsHandler {
	
	//criando um metodo q trate a EXCEPTION ResourceNotFoundException
	//
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){

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
		public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){

			HttpStatus status = HttpStatus.BAD_REQUEST;
			StandardError err = new StandardError();
			err.setTimestamp(Instant.now());
			err.setStatus(status.value());
			err.setError("Database exception");
			err.setMessage(e.getMessage());
			err.setPath(request.getRequestURI());
			return ResponseEntity.status(status).body(err);
		}	
}