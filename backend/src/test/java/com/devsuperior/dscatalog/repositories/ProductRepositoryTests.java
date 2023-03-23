package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;

		//CLASSE DE TESTES
		//essa classe PRODUCTREPOTORYTESTS vai servir para nos testarmos
		//os METODOS da CLASSE PRODUCTREPOSITORY

@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception {

		existingId = 1L;

		nonExistingId = 1000L;

		countTotalProducts = 25L;
	}
	
	//Teste para testar SE o metodo SAVE do REPOSITORY realmente 
	//esta funcionando
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {

		Product product = Factory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		Optional<Product> result = repository.findById(product.getId());
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1L, product.getId());
		Assertions.assertTrue(result.isPresent());
		Assertions.assertSame(result.get(), product);
	}
	
	//vamos testar SE quando nos PEDIMOS PARA DELETAR um PRODUCT NO BANCO
	//atraves da classe PRODUCTREPOSITORY(classe q faz conexao ao BANCO)
	//se ta funcionando
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		repository.deleteById(existingId);
		Optional<Product> result = repository.findById(existingId);
	
		Assertions.assertFalse(result.isPresent());
	}
	
	
	//vamos testar SE quando nos PEDIMOS PARA DELETAR um PRODUCT NO BANCO
	//atraves da classe PRODUCTREPOSITORY(classe q faz conexao ao BANCO)
	//e esse ID NAO EXISTE, se retorna a MSG de erro
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);			
		});
	}
}
