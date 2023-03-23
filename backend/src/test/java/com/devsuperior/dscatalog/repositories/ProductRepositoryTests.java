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
	
	private long exintingId;
	private long nonExistingId;
	private long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception {

		exintingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25L;
	}
	
	//Teste para testar SE o metodo SAVE do REPOSITORY realmente 
	//esta funcionando
	
	@Test
	public void SaveShouldPersistWithAutoincrementWhenIdIsNull() {

		Product product = Factory.createProduct();
		product.setId(null);

		product = repository.save(product);		
		Assertions.assertNotNull(product.getId());

		Assertions.assertEquals(countTotalProducts + 1, product.getId());
		
	}
	
	//vamos testar SE quando nos PEDIMOS PARA DELETAR um PRODUCT NO BANCO
	//atraves da classe PRODUCTREPOSITORY(classe q faz conexao ao BANCO)
	//se ta funcionando
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		
	 	repository.deleteById(exintingId);	 	 
	 	Optional<Product> result = repository.findById(exintingId);
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
