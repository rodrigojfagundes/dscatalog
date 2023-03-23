package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.services.validation.UserUpdateValid;

//a classe USERUPDATEDTO vai HERDAR TUDO do USERDTO
//
//chamando a ANNOTATION UserUpdateValid q criamos com a classe
//USERUPDATEVALID e q serve para fazer VALIDACOES
//a nivel de BANCO... Exemplo, verificar se o EMAIL e REPETIDO, etc...
@UserUpdateValid
public class UserUpdateDTO extends UserDTO {
	private static final long serialVersionUID = 1L;
		
}
