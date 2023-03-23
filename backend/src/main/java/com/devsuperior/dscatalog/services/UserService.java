package com.devsuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.RoleDTO;
import com.devsuperior.dscatalog.dto.UserDTO;
import com.devsuperior.dscatalog.dto.UserInsertDTO;
import com.devsuperior.dscatalog.dto.UserUpdateDTO;
import com.devsuperior.dscatalog.entities.Role;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositories.RoleRepository;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;


//classe USERSERVICE, ela recebe a SOLICITAÇÂO da classe
//USERRESOURCE, e ela USERSERVICE executa os metodos
//aqui conforme o q foi solicitado, e quando PRECISA pegar 
//algum dado ela se conecta AO BANCO, fazendo solicitacao a 
//CLASSE USERREPOSITORY (repository)

@Service
public class UserService implements UserDetailsService {
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleRepository roleRepository;
	
	
	//criando um METODO do tipo PAGE de USERDTO
	//q vamos chamar de FINDALLPAGED q recebe um PEGEABLE
	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable) {
		//vamos chamar o OBJ/DEPEDENCIA/VARIAVEL repository do tipo
		//USERREPOSITORY e como ele o USERREPOSITORY herda os
		//METODOS DO JPA para acesso ao BANCO, nos vamos chamar o metodo
		//FINDALL...
		Page<User> list = repository.findAll(pageable);
		return list.map(x -> new UserDTO(x));
		//return listDto;
	}
	
	//
	//metodo FINDBYID q busca uma determinado USER conforme o ID
	//informado
	//
	//
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		//chamando o OBJ REPOSITORY que é o OBJ da classe USERREPOSITORY
		//e essa classe é a responsavel por ACESSO AO BANCO
		//e o resultado dessa busca, vamos armazenar em um OBJ OPTIONAL
		//do tipo USER
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

		return new UserDTO(entity);
	}
	
	//Metodo INSERT do tipo USERDTO q insere um USERINSERTDTO
	//no BANCO
	@Transactional
	public UserDTO insert(UserInsertDTO dto) {
		User entity = new User();
		copyDtoToEntity(dto, entity);

		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		
		//para SALVAR no BANCO
		//vamos chamar o REPOSITORY q é um OBJ do tipo USERREPOSITORY
		//dai para o SAVE do REPOSITORY vamos passar o valor q ta
		//na nossa VAR ENTITY q é do tipo USER
		entity = repository.save(entity);
		
		return new UserDTO(entity);
	}
	
	//metodo do TIPO USERDTO de nome UPDATE para ATUALIZAR
	//os valores de um USERDTO no BANCO
	
	@Transactional
	public UserDTO update(Long id, UserUpdateDTO dto) {

		try {
			User entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);

			return new UserDTO(entity);
		}

		catch (EntityNotFoundException e) {

			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}
	
	//criando um METODO para DELETAR um USER
	public void delete(Long id) {

		try {
			repository.deleteById(id);
		}

		catch (EmptyResultDataAccessException e) {

			throw new ResourceNotFoundException("Id not found " + id);
		}

		catch (DataIntegrityViolationException e) {

			throw new DatabaseException("Integrity violation");
		}
	}
	
	//
	//
	//criando um metodo AUXILIAR de nome COPYDTOTOENTITY para pegar 
	//as INFORMACOES/ATRIBUTOS q estao no USERDTO e passar para o
	//ENTITY que é uma VAR/OBJ do tipo USER
	private void copyDtoToEntity(UserDTO dto, User entity) {

		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());

		entity.getRoles().clear();

		for (RoleDTO roleDto : dto.getRoles()) {
			Role role = roleRepository.getOne(roleDto.getId());

			entity.getRoles().add(role);		
		}
	}
	
	
	//INTERFACE USERDETAILSSERVICE serve para nos passarmos 
	//um EMAIL e ela RETORNA o USERDETAILS com os dados de 
	//autenticacao desse usuario
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByEmail(username);

		if(user == null) {
			logger.error("User not found" + username);

			throw new UsernameNotFoundException("Email not found");
		}
		logger.info("user found" + username);
		return user;
	}	
}