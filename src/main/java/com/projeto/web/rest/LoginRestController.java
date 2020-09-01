package com.projeto.web.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.model.Login;
import com.projeto.model.TokenResponse;
import com.projeto.model.Usuario;
import com.projeto.security.jwt.JwtTokenProvider;
import com.projeto.service.UsuarioService;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginRestController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ResponseEntity<TokenResponse> login(@RequestBody Login login){
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
			Optional<Usuario> usuario = usuarioService.loginUsuarioByEmail(login.getEmail());
			TokenResponse tokenResponse = new TokenResponse();
			tokenResponse.setEmail(login.getEmail());
			tokenResponse.setToken(tokenProvider.createToken(login.getEmail(), usuario.get().getRoles()));
			return ResponseEntity.ok(tokenResponse);
		}catch(UsernameNotFoundException e) {
		   throw new UsernameNotFoundException("Usuário não cadastrado!");
	    }catch(BadCredentialsException e) {
	    	throw new BadCredentialsException("Senha inválida!");
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
