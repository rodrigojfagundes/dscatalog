package com.devsuperior.dscatalog.dto;
//
//a classe USERINSERTDTO vai HERDAR TUDO do USERDTO e alem disso
//ela vai ter a SENHA do USER... Algo q o USERDTO nao tem
//
public class UserInsertDTO extends UserDTO {
	private static final long serialVersionUID = 1L;
	
	//declarando o atributo
	private String password;
	
	
	//criando o construtor vazio
	UserInsertDTO(){
		super();
	}
	
	
	//declarando os GET e SET
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	
}
