package com.devsuperior.dscatalog.services;

import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;
		
	//declarando um MOCK/MOCKITO de nome REPOSTORY com a intensao
	//de simular o comportamento do PRODUCTREPOSITORY
	@Mock
	private ProductRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long dependentId;
	private Product product;

	private PageImpl<Product> page;
	
	
	@BeforeEach
	void setUp() throws Exception {

		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		product = Factory.createProduct();
		
		page = new PageImpl<>(List.of(product));
		

		//o METODO FINDALL do REPOSITORY retorna uma PAGINA/PAGE de 
		//PRODUCTS Entao vamos fazer uma SIMULACAO, o FINDALL do 
		//REPOSITORY(MOCKADO/simulado) tem q retornar uma PAGINA 
		//de PRODUCTS (e nao importa os valores q estao nos atributos
		//dos products)
		Mockito.when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
		
		//simulando o FINDBYID, em 2 CENARIOS... 1 passando um ID q existe
		//e entao ele vai retornar um PRODUTO...
		//e no outro nos vamos passar um ID q nao existe, e entao ele vai retornar
		//um valor EMPITY
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
	}
	
	
	//vamos fazer um TESTE de UNIDADE/CLASSE para testar o metodo FINDALLPAGED...
	//q deve retornar uma PAGINA/PAGE de PRODUCTS
	@Test
	public void findAllPagedShouldReturnPage() {
		Pageable pageable = PageRequest.of(0, 12);
		
		Page<ProductDTO> result = service.findAllPaged(pageable);
		
		Assertions.assertNotNull(result);
		Mockito.verify(repository, times(1)).findAll(pageable);
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
		
		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});
		
		Mockito.verify(repository, times(1)).deleteById(dependentId);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
		
		Mockito.verify(repository, times(1)).deleteById(nonExistingId);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
		
		Mockito.verify(repository, times(1)).deleteById(existingId);
	}
}