package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

//classe CATEGORYSERVICE, ela recebe a SOLICITAÇÂO da classe
//CATEGORYRESOURCE, e ela CATEGORYSERVICE executa os metodos
//aqui conforme o q foi solicitado, e quando PRECISA pegar 
//algum dado ela se conecta AO BANCO, fazendo solicitacao a 
//CLASSE CATEGORYREPOSITORY (repository)

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		//vamos chamar o OBJ/DEPEDENCIA/VARIAVEL repository do tipo
		//CATEGORYREPOSITORY e como ele o CATEGORYREPOSITORY herda os
		//METODOS DO JPA para acesso ao BANCO, nos vamos chamar o metodo
		//FINDALL... para pegar todos os CATEGORY cadastrados no BANCO
		//
		//e o valor q for retornado da busca de FINDALL vao ser salvos
		//em uma LISTA q em o nome LIST e é do tipo CATEGORY
		List<Category> list = repository.findAll();
		
		
		//como os valores RETORNADOS do FINDALL estao armazenados em
		//uma LISTA do tipo CATEGORY, mas nos precisamos deles no 
		//formato CATEGORYDTO
		List<CategoryDTO> listDto = list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		return listDto;
	}
	

	//metodo FINDBYID q busca uma determinada CATEGORY conforme o ID
	//informado
	/////////////////////////
	//
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.get();
		
		return new CategoryDTO(entity);
	}
}
