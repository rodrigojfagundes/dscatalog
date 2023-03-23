package com.devsuperior.dscatalog.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.devsuperior.dscatalog.entities.User;

//DTO é um OBJ q serve para FILTRAR transferencia de DADOS... Exemplo
//temos um OBJ do tipo USER q tem NOME, IDADE, CPF, CEL, etc... Mas
//queremos q seja transferido para o FRONT apenas o NOME e IDADE
//dai usemos o USER_DTO... Vantagem é Controlar quais dados q vao ser
//jogados para o RESOURCER/controlador, e assim da mais seguranca e 
//economiza dados na REDE

public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	
	Set<RoleDTO> roles = new HashSet<>();
	
	public UserDTO() {
	}
	
	public UserDTO(Long id, String firstName, String lastName, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public UserDTO(User entity) {

		this.id = entity.getId();
		this.firstName = entity.getFirstName();
		this.lastName = entity.getLastName();
		this.email = entity.getEmail();

		entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
		
	}
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Set<RoleDTO> getRoles() {
		return roles;
	}

}