package com.projeto.util;


import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

import com.projeto.util.validator.ValidarSenhaValidator;

@Target({ TYPE, FIELD, ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidarSenhaValidator.class })
public @interface ValidarSenha {
	
	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "Senhas não conferem!";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	String senha_usuario();
	
	String confirmeSenha_usuario();

}
