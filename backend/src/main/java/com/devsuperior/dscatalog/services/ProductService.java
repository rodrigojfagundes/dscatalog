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
//
@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	
	//criando um METODO do tipo PAGE de PRODUCTDTO
	//q vamos chamar de FINDALLPAGED q recebe um PAGEREQUEST
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
		//vamos chamar o OBJ/DEPEDENCIA/VARIAVEL repository do tipo
		//PRODUCTREPOSITORY e como ele o PRODUCTREPOSITORY herda os
		//METODOS DO JPA para acesso ao BANCO, nos vamos chamar o metodo
		//FINDALL...
		Page<Product> list = repository.findAll(pageRequest);
		//Convertendo de PRODUCT para PRODUCTDTO
		return list.map(x -> new ProductDTO(x));
		//return listDto;
	}
	
	//
	//
	//metodo FINDBYID q busca uma determinado PRODUCT conforme o ID
	//informado
	//
	//
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		//chamando o OBJ REPOSITORY que é o OBJ da classe PRODUCTREPOSITORY
		//e essa classe é a responsavel por ACESSO AO BANCO, e para ela passando o ID
		//do product q queremos encontrar
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		
		return new ProductDTO(entity, entity.getCategories());
	}
	
	//metodo PRODUCTDTO para INSERIR um novo OBJ do tipo PRODUCT 
	//no BANCO
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		//chamando o metodo AUXILIAR (COPYDTOTOENTITY) para pegar as 
		//INFORMACOES q estao no DTO e passar para o ENTITY que é uma
		//VAR/OBJ do tipo PRODUCT
		copyDtoToEntity(dto, entity);
		//para SALVAR no BANCO
		entity = repository.save(entity);
		
		return new ProductDTO(entity);
	}
	
	
	//metodo do TIPO PRODUCTDTO de nome UPDATE para ATUALIZAR
	//os valores de um PRODUCTDTO/product no BANCO
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {

		try {
		Product entity = repository.getOne(id);
		//vamos atualizar os DADOS, ou seja vamos PEGAR o o DTO
		//e passar os valores q tao nos ATRIBUTOS do DTO para o 
		//nosso ENTITY... PARA ISSO VAMOS CHAMAR O METODO COPYTOENTITY
		copyDtoToEntity(dto, entity);	
		entity = repository.save(entity);
	
		return new ProductDTO(entity);
		}
		//caso der erro ao tentar ATUALIZAR o nome de um PRODUCT
		//dai cai nesse bloco CATCH aqui
		//pois caso de um erro sera do tipo ENTITYNOTFOUNDEXCEPTION
		//dai vamos deixar a forma de tratar esse erro
		catch(EntityNotFoundException e) {
			//chamando a nossa classe RESOURCENOTFOUNDEXCEPTION 
			//mostrando uma MENSAGEM DE ERRO PERSONALIZADA
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
	
	//
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