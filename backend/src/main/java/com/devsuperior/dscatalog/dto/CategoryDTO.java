package com.devsuperior.dscatalog.dto;

import java.io.Serializable;

import com.devsuperior.dscatalog.entities.Category;

//DTO é um OBJ q serve para FILTRAR transferencia de DADOS... Exemplo
//temos um OBJ do tipo USER q tem NOME, IDADE, CPF, CEL, etc... Mas
//queremos q seja transferido para o FRONT apenas o NOME e IDADE
//dai usemos o USER_DTO... Vantagem é Controlar quais dados q vao ser
//jogados para o RESOURCER/controlador, e assim da mais seguranca e 
//economiza dados na REDE

public class CategoryDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	
	public CategoryDTO() {
	}
	
	public CategoryDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public CategoryDTO(Category entity) {
		//os valores dos ATRIBUTOS do nosso CATEGORYDTO vao ser
		//preenchidos pelos os VALORES q estiverem nos ATRIBUTOS
		//do nosso OBJ/variavel ENTITY que é do tipo da classe CATEGORY
		this.id = entity.getId();
		this.name = entity.getName();
	}
	
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}	
}