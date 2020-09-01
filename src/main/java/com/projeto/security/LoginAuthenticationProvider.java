package com.projeto.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import com.projeto.model.Usuario;
import com.projeto.service.UsuarioService;
import com.projeto.service.exceptions.EmailUsuarioDeveSerInformadoException;
import com.projeto.service.exceptions.SenhaUsuarioDeveSerInformadaException;

@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {

		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) auth;
		
		String email = auth.getName();
		String password = auth.getCredentials().toString();
		
		
		if ("".equals(email)) {
			throw new EmailUsuarioDeveSerInformadoException("O E-mail do Usuário deve ser informado!");
		}
		
		if ("".equals(password)) {
			throw new SenhaUsuarioDeveSerInformadaException("A Senha do usuário deve ser informada!");
		}
	
		Optional<Usuario> usuario = usuarioService.loginUsuarioByEmail(email);
		
		if (!usuario.isPresent()) {
			throw new UsernameNotFoundException("Usuário não está cadastrado!");
		}
		
		if ( email.equals(usuario.get().getEmail()) && usuario.get().isAtivo() == Boolean.FALSE ) {
			throw new LockedException("Usuário bloqueado no sistema!");
		}
		
		if ( email.equals(usuario.get().getEmail()) && BCrypt.checkpw(password, usuario.get().getPassword())) {
			token = new UsernamePasswordAuthenticationToken(new UsuarioSistema(usuario.get()), 
																usuario.get().getPassword(),
																usuario.get().getAuthorities());
		} else {
			throw new BadCredentialsException("A Senha informada é inválida!");
		}
		
		
		return token;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
