package com.devsuperior.dscatalog.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;

//classe STANDARDERROR, ou seja erro padrao...
//uma estrutura PADRAO para erros
public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//declarando os atributos
	private Instant timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;
	
	//declarando construtor
	public StandardError() {
	}
	
	//criando os GET e SET
	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
