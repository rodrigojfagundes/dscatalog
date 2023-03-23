package com.devsuperior.dscatalog.services.validation;

//
//atraves da BIBLIOTECA BEANS VALIDATION, vamos inserir nela uma 
//validacao mais complexa para validar se uma INFORMACAO ESTA OK 
//no BANCO DE DADOS
//EX: verificar SE o EMAIL NAO E REPETIDO

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UserInsertValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)

public @interface UserInsertValid {
	String message() default "Validation error";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}