package com.devsuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

//classe PRODUCTSERVICE, ela recebe a SOLICITAÇÂO da classe
//PRODUCTRESOURCE, e ela PRODUCTSERVICE executa os metodos
//aqui conforme o q foi solicitado, e quando PRECISA pegar 
//algum dado ela se conecta AO BANCO, fazendo solicitacao a 
//CLASSE PRODUCTREPOSITORY (repository)
@Service
public class ProductService {

	//criando uma DEPEDENCIA/VARIAVEL do tipo
	//PRODUCTREPOSITORY de nome REPOSITORY
	//e com o ANNOTATION @Autowired serve para INJETAR AUTOMATICAMENTE
	//uma INSTANCIA gerenciada pelo SPRINGBOOT
	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	
	//criando um METODO do tipo PAGE de PRODUCTDTO
	//q vamos chamar de FINDALLPAGED q recebe um PEGEABLE
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Pageable pageable) {
		//vamos chamar o OBJ/DEPEDENCIA/VARIAVEL repository do tipo
		//PRODUCTREPOSITORY e como ele o PRODUCTREPOSITORY herda os
		//METODOS DO JPA para acesso ao BANCO, nos vamos chamar o metodo
		//FINDALL...
		//
		Page<Product> list = repository.findAll(pageable);
		return list.map(x -> new ProductDTO(x));
		//return listDto;
	}
	
	//
	//metodo FINDBYID q busca uma determinado PRODUCT conforme o ID
	//informado
	//
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		//OBS: Objeto OPTIONAL é uma tecnologia q serve para EVITAR
		//trabalhar com VALORES NULO
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

		return new ProductDTO(entity, entity.getCategories());
	}
	
	//metodo PRODUCTDTO para INSERIR um novo OBJ do tipo PRODUCT 
	//no BANCO
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();

		copyDtoToEntity(dto, entity);
		//para SALVAR no BANCO
		//vamos chamar o REPOSITORY q é um OBJ do tipo PRODUCTREPOSITORY
		//dai para o SAVE do REPOSITORY vamos passar o valor q ta
		//na nossa VAR ENTITY q é do tipo USER
		entity = repository.save(entity);

		return new ProductDTO(entity);
	}

	
	//metodo do TIPO PRODUCTDTO de nome UPDATE para ATUALIZAR
	//os valores de um PRODUCTDTO/product no BANCO
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);

			return new ProductDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}
	
	//criando um METODO para DELETAR um PRODUCT
	public void delete(Long id) {
		try {

			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	//criando um metodo AUXILIAR de nome COPYDTOTOENTITY para pegar 
	//as INFORMACOES/ATRIBUTOS q estao no PRODUCTDTO e passar para o
	//ENTITY que é uma VAR/OBJ do tipo PRODUCT
	private void copyDtoToEntity(ProductDTO dto, Product entity) {

		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		
		entity.getCategories().clear();

		for (CategoryDTO catDto : dto.getCategories()) {
			Category category = categoryRepository.getOne(catDto.getId());

			entity.getCategories().add(category);			
		}
	}	
}
