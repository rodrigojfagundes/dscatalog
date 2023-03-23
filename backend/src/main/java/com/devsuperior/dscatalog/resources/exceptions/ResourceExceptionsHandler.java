package com.devsuperior.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

//para nao precisar IMPLEMENTAR BLOCO TRY CATCH em todos os METODOS
//do CONTROLADOR, vamos criar a CLASSE RESOURCEEXCEPTIONSHANDLER

//com a ANNOTATION @CONTROLLERADVICE CONTROLLERADVICE q permite q essa CLASSE
//INTERCEPTE alguma EXCEPTION q acontece na CAMADA DE RESOURCE
//e essa classe RESOURCEEXCEPTION vai TRATAR o ERRO
@ControllerAdvice
public class ResourceExceptionsHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){

		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
}
