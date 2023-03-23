package com.devsuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

//classe PRODUCTSERVICE, ela recebe a SOLICITAÇÂO da classe
//PRODUCTRESOURCE, e ela PRODUCTSERVICE executa os metodos
//aqui conforme o q foi solicitado, e quando PRECISA pegar 
//algum dado ela se conecta AO BANCO, fazendo solicitacao a 
//CLASSE PRODUCTREPOSITORY (repository)
//
@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
		//vamos chamar o OBJ/DEPEDENCIA/VARIAVEL repository do tipo
		//PRODUCTREPOSITORY e como ele o PRODUCTREPOSITORY herda os
		//METODOS DO JPA para acesso ao BANCO, nos vamos chamar o metodo
		//FINDALL...
		//
		Page<Product> list = repository.findAll(pageRequest);
	
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
		Product entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));

		return new ProductDTO(entity, entity.getCategories());
	}
	
	//metodo PRODUCTDTO para INSERIR um novo OBJ do tipo PRODUCT 
	//no BANCO
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		entity = repository.save(entity);

		return new ProductDTO(entity);
	}
	
	
	//metodo do TIPO PRODUCTDTO de nome UPDATE para ATUALIZAR
	//os valores de um PRODUCTDTO/product no BANCO
	//
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
		Product entity = repository.getOne(id);
		entity = repository.save(entity);
		return new ProductDTO(entity);
		}

		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found" + id);
		}
		
	}
	
	//criando um METODO para DELETAR um PRODUCT
	public void delete(Long id) {

		try {
		repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
}