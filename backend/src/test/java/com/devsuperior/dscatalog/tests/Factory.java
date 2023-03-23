package com.devsuperior.dscatalog.tests;

import java.time.Instant;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;

//
//criando a classe FACTORY/FABRICA... q é uma classe responsavel
//por INSTANCIAR OBJETOS
public class Factory {
	
	//criando um metodo de nome CREATEPRODUCT do tipo PRODUCT
	//q basicamente sera uma classe para nos criarmos
	//um OBJ do tipo PRODUTO
	public static Product createProduct() {

		Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
		product.getCategories().add(new Category(1L, "Electronics"));
		
		return product;		
	}
	
	//criando um PRODUCTDTO
	public static ProductDTO createProductDTO() {
		Product product = createProduct();

		return new ProductDTO(product, product.getCategories());
	}
}
