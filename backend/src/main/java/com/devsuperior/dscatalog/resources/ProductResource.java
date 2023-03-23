package com.devsuperior.dscatalog.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.services.ProductService;


//classe para fazer chamada dos RECURSOS REST dos OBJETOS do tipo
//PRODUCT... Ou seja quando o JAVASCRIPT q ta rodando no FRONT
//requisitar os PRODUTOS, ele o JS vai chamar os metodos dessa
//classe aqui, a classe PRODUCTRESOURCE, e ESSA CLASSE chama
//a classe PRODUCTSERVICE, q roda os METODOS solicitados
//
//para dizer q essa classe é um CONTROLADOR REST, vamos por o
//@RESTCONTROLLER... e o @RequestMapping e para dizer qual a ROTA
//do recurso... ou seja (localhost:8080/produtos)
@RestController
@RequestMapping(value = "/products")
public class ProductResource {
	@Autowired
	private ProductService service;
	
	//o @GETMAPPING e para dizer q o metodo FINDALL vai ser um METODO
	//q sera solicitado PELO GET do navegador... ou SEJA PARA PEGAR
	//dados
	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAll(
			Pageable pageable) {
		Page<ProductDTO> list = service.findAllPaged(pageable);	

		return ResponseEntity.ok().body(list);
	}
	
	// criando um METODO/ENDPOINT para retornar um PRODUTO pelo o ID
	// da PRODUTO
	//
	@GetMapping(value = "/{id}")
	// criando o METODO/ENDPOINT... ou seja uma ROTA q vai
	// responder a uma SOLICITAÇÂO feita atraves do navegador
	// o retorno do metodo é um RESPONSEENTITY q é um OBJ do spring q
	// encapsula uma RESPOSTA/retorno no formato HTTP...
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		ProductDTO dto = service.findById(id);

		return ResponseEntity.ok().body(dto);
	}
	

	// CADASTRANDO PRODUCT NO BANCO COM POST
	//
	//

	// METODO POST RESTFUL para inserir no BANCO um novo produto
	// o RESPONSEENTITY e do tipo PRODUCTDTO, pois DPS de INSERIR
	// nos vamos RETORNAR o nome da PRODUCT/productdto q foi inserido
	// o nome do metodo vai ser INSERT
	@PostMapping
	public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto) {
		dto = service.insert(dto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}
	
	//
	// METODO/ENDPOINT para ATUALIZAR um PRODUTO
	//
	// METODO/ENDPOINT PUT (putmapping), q é o METODO REST para ATUALIZACOES
	// e a ROTA da ANNOTATION @PUTMAPPING vai ter o VALUE ID q é o ID
	// da PRODUCT q queremos ATUALIZAR
	//
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto) {
		dto = service.update(id, dto);

		return ResponseEntity.ok().body(dto);
	}
	

	// METODO/ENDPOINT para DELETAR um PRODUCT
	//
	//
	//
	//
	//METODO/ENDPOINT DELETE (DELETEMAPPING), q é o METODO REST para DELETAR
	//e a ROTA da ANNOTATION @DELETEMAPPING vai ter o VALUE ID q é o ID
	//do PRODUCT q queremos DELETAR
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) { 
		service.delete(id);

		return ResponseEntity.noContent().build();
	}
} 
