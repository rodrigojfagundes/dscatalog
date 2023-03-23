package com.devsuperior.dscatalog.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;

//DTO é um OBJ q serve para FILTRAR transferencia de DADOS... Exemplo
//temos um OBJ do tipo USER q tem NOME, IDADE, CPF, CEL, etc... Mas
//queremos q seja transferido para o FRONT apenas o NOME e IDADE
//dai usemos o USER_DTO... Vantagem é Controlar quais dados q vao ser
//jogados para o RESOURCER/controlador, e assim da mais seguranca e 
//economiza dados na REDE

public class ProductDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	@Size(min = 5, max = 60, message = "Deve ter entre 5 e 60 caracteres")
	@NotBlank(message = "campo requerido")
	private String name;

	@NotBlank(message = "campo requerido")
	private String description;

	@Positive(message = "Preco deve ser um valor positivo")
	private Double price;
	private String imgUrl;

	@PastOrPresent(message = "A data do produto nao pode ser futura")
	private Instant date;

	private List<CategoryDTO> categories = new ArrayList<>();
	
	public ProductDTO() {
	}	

	public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
		this.date = date;
	}
	
	public ProductDTO(Product entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
		this.imgUrl = entity.getImgUrl();
		this.date = entity.getDate();
	}
	 
	public ProductDTO(Product entity, Set<Category> categories) {

		this(entity);
		categories.forEach(cat -> this.categories.add(new CategoryDTO(cat)));
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryDTO> categories) {
		this.categories = categories;
	}
}