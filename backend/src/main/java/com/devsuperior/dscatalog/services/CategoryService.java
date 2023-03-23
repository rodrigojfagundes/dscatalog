package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

//classe CATEGORYSERVICE, ela recebe a SOLICITAÇÂO da classe
//CATEGORYRESOURCE, e ela CATEGORYSERVICE executa os metodos
//aqui conforme o q foi solicitado, e quando PRECISA pegar 
//algum dado ela se conecta AO BANCO, fazendo solicitacao a 
//CLASSE CATEGORYREPOSITORY (repository)
//
//a ANNOTATION @SERVICE, registra a classe CATEGORYSERVICE
//como um componente q vai participar do SISTEMA DE INJECAO 
//DE DEPEDENCIAS automaticado do SPRINGBOOT, ou seja qm vai
//gerenciar as depedencias das instancias
//do CATEGORYSERVICE é o SPRINGBOOT
@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){

		List<Category> list = repository.findAll();

		List<CategoryDTO> listDto = list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		return listDto;
	}
	
	//
	//metodo FINDBYID q busca uma determinada CATEGORY conforme o ID
	//informado
	//
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {

		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));

		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {

		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);

		return new CategoryDTO(entity);
	}
	
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
		Category entity = repository.getOne(id);

		entity.setName(dto.getName());
		entity = repository.save(entity);

		return new CategoryDTO(entity);
		}

		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found" + id);
		}
	}
	
	//criando um METODO para DELETAR uma CATEGORY
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
