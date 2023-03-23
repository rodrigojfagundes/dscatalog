package com.devsuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscatalog.entities.Role;

//criando a interface ROLE REPOSITORY, q irá fazer receber
//as solicitações da CLASSE ROLESERVICE... e em sequencia
//a USERREPOSITORY irá fazer a conexao ao BANCO para
//realizar a solicitacao
	//e essa interface HERDA de JPAREPOSITORY para se conectar
	//ao BANCO, o JPAREPOSITORY recebe um TIPO da ENTIDADE
	//no caso USER, e um ID, q vai ser no formato LONG
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
