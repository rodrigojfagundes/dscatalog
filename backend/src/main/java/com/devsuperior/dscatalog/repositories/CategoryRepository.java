package com.devsuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscatalog.entities.Category;

//criando a interface CATEGORY REPOSITORY, q irá fazer receber
//as solicitações da CLASSE CATEGORYSERVICE... e em sequencia
//a CATEGORYREPOSITORY irá fazer a conexao ao BANCO para
//realizar a solicitacao
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
