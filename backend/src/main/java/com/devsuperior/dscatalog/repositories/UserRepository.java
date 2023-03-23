package com.devsuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscatalog.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	//criando um metodo q busca o USER pelo o EMAIL
	//
	//metodo q retorna um USER de nome FINDBYEMAIL q recebe
	//um string email
	User findByEmail(String email);
}
