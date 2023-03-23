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
@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	//criando um METODO do tipo PAGE de CATEGORYDTO
	//q vamos chamar de FINDALLPAGED q recebe um PAGEREQUEST
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest){
		//vamos chamar o OBJ/DEPEDENCIA/VARIAVEL repository do tipo
		//CATEGORYREPOSITORY e como ele o CATEGORYREPOSITORY herda os
		//METODOS DO JPA para acesso ao BANCO, nos vamos chamar o metodo
		//FINDALL... E passamos o valor do PAGEREQUEST
		//
		Page<Category> list = repository.findAll(pageRequest);
		return list.map(x -> new CategoryDTO(x));
		//return listDto;
	}
	
	//
	//
	//metodo FINDBYID q busca uma determinada CATEGORY conforme o ID
	//informado
	//
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		//chamando o OBJ REPOSITORY que é o OBJ da classe CATEGORYREPOSITORY
		//e essa classe é a responsavel por ACESSO AO BANCO
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));

		return new CategoryDTO(entity);
	}
	
	//metodo CATEGORYDTO para INSERIR um novo OBJ do tipo CATEGORY 
	//no BANCO
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		//pegando o DTO do tipo CATEGORYDTO e converter para uma
		//ENTIDADE, Um ENTITY do tipo CATEGORY
		Category entity = new Category();
		entity.setName(dto.getName());
		//Salvando a var ENTITY do tipo CATEGORY no banco
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}
	
	
	//metodo do TIPO CATEGORYDTO de nome UPDATE para ATUALIZAR
	//os valores de uma CATEGORYDTO/category no BANCO
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
		//instanciando uma ENTIDADE/OBJETO do tipo CATEGORY de nome ENTITY
		//E a ENTITY vai receber o RETORNO do METODO GETONE do REPOSITORY
		//metodo GETONE q recebe o ID da CATEGORY q queremos q SEJA ATUALIZADA
		Category entity = repository.getOne(id);
		//vamos atualizar os DADOS, ou seja vamos PEGAR o o DTO
		//e passar o valor q ta em NAME do DTO para o nosso ENTITY
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