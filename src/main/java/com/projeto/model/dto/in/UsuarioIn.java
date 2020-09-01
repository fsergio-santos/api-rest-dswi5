package com.projeto.model.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Usuário Entrada",  description = "Dados do usuário de entrada")
public class UsuarioIn {
	
	@ApiModelProperty(value="1", required = true)
	private Long Id;
	@ApiModelProperty(value = "Francisco Roberto Carlos",required = true)
	private String username;
	@ApiModelProperty(value="francisco@roberto.com.br", required = true)
	private String email;
	
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
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
