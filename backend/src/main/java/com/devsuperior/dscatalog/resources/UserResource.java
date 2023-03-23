package com.devsuperior.dscatalog.resources;

import java.net.URI;

import javax.validation.Valid;

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

import com.devsuperior.dscatalog.dto.UserDTO;
import com.devsuperior.dscatalog.dto.UserInsertDTO;
import com.devsuperior.dscatalog.dto.UserUpdateDTO;
import com.devsuperior.dscatalog.services.UserService;


//classe para fazer chamada dos RECURSOS REST dos OBJETOS do tipo
//USER... Ou seja quando o JAVASCRIPT q ta rodando no FRONT
//requisitar os PRODUTOS, ele o JS vai chamar os metodos dessa
//classe aqui, a classe USERRESOURCE, e ESSA CLASSE chama
//a classe USERSERVICE, q roda os METODOS solicitados
//
//para dizer q essa classe é um CONTROLADOR REST, vamos por o
//@RESTCONTROLLER... e o @RequestMapping e para dizer qual a ROTA
//do recurso... ou seja (localhost:8080/users)
@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	private UserService service;
	
	//o @GETMAPPING e para dizer q o metodo FINDALL vai ser um METODO
	//q sera solicitado PELO GET do navegador... ou SEJA PARA PEGAR
	//dados
	@GetMapping
	public ResponseEntity<Page<UserDTO>> findAll(
			Pageable pageable) {
		Page<UserDTO> list = service.findAllPaged(pageable);	

		return ResponseEntity.ok().body(list);
	}
	
	// criando um METODO/ENDPOINT para retornar um USUARIO pelo o ID
	// da USUARIO
	//
	@GetMapping(value = "/{id}")
	// criando o METODO/ENDPOINT... ou seja uma ROTA q vai
	// responder a uma SOLICITAÇÂO feita atraves do navegador
	// o retorno do metodo é um RESPONSEENTITY q é um OBJ do spring q
	// encapsula uma RESPOSTA/retorno no formato HTTP...
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		UserDTO dto = service.findById(id);

		return ResponseEntity.ok().body(dto);
	}
	

	// CADASTRANDO USER NO BANCO COM POST
	//
	// METODO POST RESTFUL para inserir no BANCO um novo usuario
	// o RESPONSEENTITY e do tipo USERINSERTDTO, pois DPS de INSERIR
	// nos vamos RETORNAR o nome do USER/userdto q foi inserido
	// o nome do metodo vai ser INSERT
	@PostMapping
	public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO dto) {
		UserDTO newDto = service.insert(dto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newDto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(newDto);
	}

	// METODO/ENDPOINT para ATUALIZAR um USER
	//
	// METODO/ENDPOINT PUT (putmapping), q é o METODO REST para ATUALIZACOES
	// e a ROTA da ANNOTATION @PUTMAPPING vai ter o VALUE ID q é o ID
	// do USERUPDATEDTO/USER q queremos ATUALIZAR
	//
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {
		UserDTO newDto = service.update(id, dto);

		return ResponseEntity.ok().body(newDto);
	}


	// METODO/ENDPOINT para DELETAR um USER
	//
	//METODO/ENDPOINT DELETE (DELETEMAPPING), q é o METODO REST para DELETAR
	//e a ROTA da ANNOTATION @DELETEMAPPING vai ter o VALUE ID q é o ID
	//do USER q queremos DELETAR
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) { 
		service.delete(id);

		return ResponseEntity.noContent().build();
	}
}