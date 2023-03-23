package com.devsuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscatalog.entities.Product;


	//e essa interface HERDA de JPAREPOSITORY para se conectar
	//ao BANCO, o JPAREPOSITORY recebe um TIPO da ENTIDADE
	//no caso PRODUCT, e um ID, q vai ser no formato LONG
	//
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
