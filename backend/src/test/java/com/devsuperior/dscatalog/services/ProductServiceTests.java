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
	//instanciando um PRODUCT de nome product
	private Product product;
	//o atributo PAGEIMPL do tipo PRODUCT de nome PAGE
	//PELO O Q EU ENTENDI, é um tipo concreto q representa uma PAGE/pagina
	//de dados
	private PageImpl<Product> page;
	
	
	@BeforeEach
	void setUp() throws Exception {
		//declarando atributos/variaveis com valor q EXISTEM 
		//e q NAO EXISTEM
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		//chamando o metodo CREATEPRODUCT q ta na classe FACTORY
		//e criando um novo PRODUTO e passando o valor desse PRODUTO
		//para a nossa VAR/ATRIBUTO PRODUCT q foi criada ali em cima
		product = Factory.createProduct();
		//para o atributo PAGE criado ali em cima, estamos atribuindo
		//o valor de uma LISTA de produtos... Pois dentro de uma pagina
		//teremos uma LISTA com varios PRODUTOS (product esse
		//q foi CRIADO/INSTANCIADO ali na linha de cima) :)
		page = new PageImpl<>(List.of(product));
		
		//OBS: no mockito temos o (FACA A ACAO) ex: DONOTHING quando 
		//acontecer isso WHEN (dai nos passamos um metodo tipo 
		//DELETEBYID com um valor)
		
		//o METODO FINDALL do REPOSITORY retorna uma PAGINA/PAGE de 
		//PRODUCTS Entao vamos fazer uma SIMULACAO, o FINDALL do 
		//REPOSITORY(MOCKADO/simulado) tem q retornar uma PAGINA 
		//de PRODUCTS (e nao importa os valores q estao nos atributos
		//dos products)
		//
		//
		//chamando o MOCKITO e QUANDO(WHEN) chamarmos o REPOSITORY.FINDALL
		//q recebe um PAGEABLE como argumento, e para nos SIMULARMOS os valores de um
		//PAGEABLE vamos ter q usar o ARGUMENTMATCHERS.ANY 
		Mockito.when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		
		//OBS: o SAVE do REPOSITORY recebe uma ENTIDADE como argumento e retorna uma
		//referencia para essa ENTIDADE... (local onde foi armazenado)
		//ou seja, quando chamarmos o SAVE vamos retornar um PRODUCT
		//
		//chamando o MOCKITO e QUANDO(WHEN) chamarmos o SAVE do REPOSITORY
		//e no SAVE usando o ArgumentMatchers para passar QUALQUER OBJETO
		//dai quando isso acontecer nos .THENRETURN (retornamos) um PRODUCT
		//q no caso e a VAR PRODUCT q ta instanciado ali em cima
		//e dessa forma nos SIMULAMOS o comportamento do SAVE REPOSITORY
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
		//instanciando um OBJ do tipo PAGEABLE em q vai ser a PAG 0
		//do tamanho 12
		Pageable pageable = PageRequest.of(0, 12);
		
		//criando uma PAGE do tipo PRODUCTDTO em q o nome vai ser
		//RESULT, q vai receber o retorno do metodo FINDALLPAGED
		//no qual nos passamos o OBJ PAGEABLE acima coo argumento
		Page<ProductDTO> result = service.findAllPaged(pageable);
		
		//vamos testar SE apos realizar o processo acima o valor
		//retornado e salva na VAR RESULT e DIFERENTE de NULL
		Assertions.assertNotNull(result);
		//chamando o VERIFY do MOCKITO para testar SE realmente
		//foi chamado o FINDALL dentro do FINDALLPAGED
		Mockito.verify(repository, times(1)).findAll(pageable);
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
		
		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});
		
		Mockito.verify(repository, times(1)).deleteById(dependentId);
	}
	
	
	
	//TESTANDO SE quando o USUARIO informa um ID de um PRODUCT
	//q NAO existe, SE retorna uma EXCESSAO
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		//chamando o ASSERTTHROWS e informando q DEVE retornar uma EXCESSAO
		//do tipo ResourceNotFoundException, quando nos formos
		//TENTAR deletar um PRODUCT com o ID q NAO EXISTE (NOMEXISTINGID)
		//atraves do metodo DELETE do SERVICE
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
		
		//verificando no MOCKITO SE no processo acima foi chamado
		//o DELETEBYID com um ID q NAO EXISTE (NONEXISTINGID)
		Mockito.verify(repository, times(1)).deleteById(nonExistingId);
	}
	
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(() -> {
			//chamando o metodo DELETE do nosso SERVICE q e do tipo
			//PRODUCTSERVICE e passando um ID de um PRODUCT q EXISTE
			service.delete(existingId);
		});
		
		//verificando no MOCKITO SE no processo acima foi chamado
		//o DELETEBYID passando um ID Q EXISTE (EXISTINGID)
		Mockito.verify(repository, times(1)).deleteById(existingId);
	}
}
