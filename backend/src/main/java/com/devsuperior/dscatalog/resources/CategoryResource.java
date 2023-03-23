package com.devsuperior.dscatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.services.CategoryService;

//classe para fazer chamada dos RECURSOS REST dos OBJETOS do tipo
//CATEGORY... Ou seja quando o JAVASCRIPT q ta rodando no FRONT
//requisitar as CATEGORIAS, ele o JS vai chamar os metodos dessa
//classe aqui, a classe CATEGORYRESOURCE, e ESSA CLASSE chama
//a classe CATEGORYSERVICE, q roda os METODOS solicitados
//
//para dizer q essa classe é um CONTROLADOR REST, vamos por o
//@RESTCONTROLLER... e o @RequestMapping e para dizer qual a ROTA
//do recurso... ou seja (localhost:8080/categories)
@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	@Autowired
	private CategoryService service;

	@GetMapping
	// criando o primeiro METODO/ENDPOINT... ou seja uma ROTA q vai
	// responder a uma SOLICITAÇÂO feita atraves do navegador
	// o retorno do metodo é um RESPONSEENTITY q é um OBJ do spring q
	// encapsula uma RESPOSTA/retorno no formato HTTP... E
	// entre <<>> nos vamos colocar o tipo de dado q estara presente
	// dentro do RESPONSEENTITY no caso é um LIST de CATEGORYDTO
	public ResponseEntity<List<CategoryDTO>> findAll() {

		// criando uma LISTA do tipo CATEGORYDTO q irá se chamar de LIST
		// e essa lista, vai receber o RETORNO do metodo FINDALL
		// q esta na classe CATEGORYSERVICE, q nos iremos chamar
		// pelo SERVICE
		List<CategoryDTO> list = service.findAll();

		return ResponseEntity.ok().body(list);
	}

	// criando um METODO/ENDPOINT para retornar uma CATEGORIA pelo o ID
	// da CATEGORIA
	//
	//
	// o @GETMAPPING e para dizer q o metodo FINDBYID vai ser um METODO
	// q sera solicitado PELO GET do navegador... ou SEJA PARA PEGAR
	// dados e o VALUE ali nos vamos passar {ID} pois quando nos buscar
	//uma CATEGORY especifica nos vamos passar assim
	//localhost:8080/category/ID (valor do id)
	@GetMapping(value = "/{id}")


	// criando o METODO/ENDPOINT... ou seja uma ROTA q vai
	// responder a uma SOLICITAÇÂO feita atraves do navegador
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
		//
		// criando uma VARIAVEL do tipo CATEGORYDTO q irá se chamar de DTO
		// e essa VAR, vai receber o RETORNO do metodo FINDBYID (com o ID)
		CategoryDTO dto = service.findById(id);
		
		return ResponseEntity.ok().body(dto);
	}

}
