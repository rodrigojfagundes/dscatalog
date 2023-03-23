package com.devsuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscatalog.entities.Category;

//criando a interface CATEGORY REPOSITORY, q irá fazer receber
//as solicitações da CLASSE CATEGORYSERVICE... e em sequencia
//a CATEGORYREPOSITORY irá fazer a conexao ao BANCO para
//realizar a solicitacao
	//e essa classe HERDA de JPAREPOSITORY para se conectar
	//ao BANCO, o JPAREPOSITORY recebe um TIPO da ENTIDADE
	//no caso CATEGORY, e um ID, q vai ser no formato LONG
	//
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
