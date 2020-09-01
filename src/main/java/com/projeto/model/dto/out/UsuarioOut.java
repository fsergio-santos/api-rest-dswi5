package com.projeto.model.dto.out;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Usuário",  description = "Dados do usuário de Saída")
public class UsuarioOut extends RepresentationModel<UsuarioOut>{

	@ApiModelProperty(example = "1")
	private Long id;
	@ApiModelProperty(value = "Francisco Roberto Carlos")
	private String username;
	@ApiModelProperty(value="francisco@roberto.com.br")
	private String email;
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}
