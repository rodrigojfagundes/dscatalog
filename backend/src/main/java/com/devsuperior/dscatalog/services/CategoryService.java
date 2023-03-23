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
	//q vamos chamar de FINDALLPAGED q recebe um PAGEABLE
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(Pageable pageable) {
		//vamos chamar o OBJ/DEPEDENCIA/VARIAVEL repository do tipo
		//CATEGORYREPOSITORY e como ele o CATEGORYREPOSITORY herda os
		//METODOS DO JPA para acesso ao BANCO, nos vamos chamar o metodo
		//FINDALL...
		//
		Page<Category> list = repository.findAll(pageable);
		return list.map(x -> new CategoryDTO(x));
		//return listDto;
	}
	
	//
	//metodo FINDBYID q busca uma determinada CATEGORY conforme o ID
	//informado
	//
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		//chamando o OBJ REPOSITORY que é o OBJ da classe CATEGORYREPOSITORY
		//e essa classe é a responsavel por ACESSO AO BANCO
		//e o resultado dessa busca, vamos armazenar em um OBJ OPTIONAL
		//do tipo CATEGORY
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

		return new CategoryDTO(entity);
	}
	
	//metodo CATEGORYDTO para INSERIR um novo OBJ do tipo CATEGORY 
	//no BANCO
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {

		Category entity = new Category();
		entity.setName(dto.getName());
		//para SALVAR no BANCO
		entity = repository.save(entity);

		return new CategoryDTO(entity);
	}
	
	//metodo do TIPO CATEGORYDTO de nome UPDATE para ATUALIZAR
	//os valores de uma CATEGORYDTO/category no BANCO
	//
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);

			return new CategoryDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}
	
	//criando um METODO para DELETAR uma CATEGORY
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
}
