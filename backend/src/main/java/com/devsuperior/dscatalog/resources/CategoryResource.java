package com.devsuperior.dscatalog.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	// dentro do RESPONSEENTITY no caso é um PAGE/pagina de CATEGORYDTO
	//e dentro do FINDALL vamos passar alguns parametro de BUSCA
	public ResponseEntity<Page<CategoryDTO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,

			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,

			@RequestParam(value = "direction", defaultValue = "ASC") String direction,

			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy
			) {
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, 
				Direction.valueOf(direction), orderBy);

		Page<CategoryDTO> list = service.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(list);
	}

	// criando um METODO/ENDPOINT para retornar uma CATEGORIA pelo o ID
	// da CATEGORIA
	//
	// o @GETMAPPING e para dizer q o metodo FINDBYID vai ser um METODO
	// q sera solicitado PELO GET do navegador... ou SEJA PARA PEGAR
	// dados e o VALUE ali nos vamos passar {ID} pois quando nos buscar
	// uma CATEGORY especifica nos vamos passar assim
	// localhost:8080/category/ID (valor do id)
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
		CategoryDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	// CADASTRANDO CATEGORY NO BANCO COM POST
	//
	//
	// METODO POST RESTFUL para inserir no BANCO uma nova categoria
	// o RESPONSEENTITY e do tipo CATEGORYDTO, pois DPS de INSERIR
	// nos vamos RETORNAR o nome da CATEGORY/categorydto q foi inserido
	// o nome do metodo vai ser INSERT
	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}

	//
	// METODO/ENDPOINT para ATUALIZAR uma CATEGORIA
	//
	//
	//
	//
	// METODO/ENDPOINT PUT (putmapping), q é o METODO REST para ATUALIZACOES
	// e a ROTA da ANNOTATION @PUTMAPPING vai ter o VALUE ID q é o ID
	// da CATEGORY q queremos ATUALIZAR
	//
	// esse metodo UPDATE recebe os ANNOTATION PATHVARIABLE e para pegar o
	// ID do CATEGORY q queremos ATUALIZAR (q esta no value = "/{id}") e o
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}

	//
	// METODO/ENDPOINT para DELETAR uma CATEGORIA
	//METODO/ENDPOINT DELETE (DELETEMAPPING), q é o METODO REST para DELETAR
	//e a ROTA da ANNOTATION @DELETEMAPPING vai ter o VALUE ID q é o ID
	//da CATEGORY q queremos DELETAR
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
	service.delete(id);
	
		return ResponseEntity.noContent().build();
	}
}