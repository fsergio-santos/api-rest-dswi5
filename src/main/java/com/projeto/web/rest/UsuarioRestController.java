package com.projeto.web.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.model.Usuario;
import com.projeto.model.dto.converter.ConverterUsuario;
import com.projeto.model.dto.in.UsuarioIn;
import com.projeto.model.dto.out.UsuarioOut;
import com.projeto.service.JasperReportsService;
import com.projeto.service.UsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Usuario")
@RestController
@RequestMapping(value="/rest/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioRestController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private JasperReportsService jasperReportsService;
	
	@Autowired
	private ConverterUsuario converterUsuario;
	
	@ApiOperation("Lista todos os usuários")
	@ResponseBody
	@GetMapping("/list")
	public CollectionModel<UsuarioOut> listarUsuario(){
		List<Usuario> listaUsuario = usuarioService.findAll();
		
		List<UsuarioOut> listaUsuarioOut = converterUsuario.toCollectionsModel(listaUsuario); 
		
		listaUsuarioOut.forEach(usuarioOut -> {
			usuarioOut.add(linkTo(methodOn(UsuarioRestController.class).buscarUsuarioAlterar(usuarioOut.getId())).withSelfRel());
		});
		
		CollectionModel<UsuarioOut> usuarioOutCollectionModel = new CollectionModel<>(listaUsuarioOut);
		
		usuarioOutCollectionModel.add(linkTo(methodOn(UsuarioRestController.class).listarUsuario()).withRel("usuarios"));
		
		return  usuarioOutCollectionModel;
	}
	
	@ApiOperation("Cadastra novo Usuário")
	@ResponseBody
	@GetMapping("/novo")
	public UsuarioOut cadastroUsuario(@ApiParam(name = "Dados do Usuário", value="Representação de um usuário" ) UsuarioIn in) {
		Usuario usuario = converterUsuario.converterInToUsuario(in);
		return converterUsuario.converterUsuarioToOut(usuario);
	}
	
	@ApiOperation("Busca Usuários por Id")
	@ResponseBody
	@GetMapping("/alterar/{id}")
	public UsuarioOut buscarUsuarioAlterar(@ApiParam(value = "ID de um Usuário", example = "1") @PathVariable Long id) {
		Usuario usuario = usuarioService.getOne(id);
		UsuarioOut usuarioOut = converterUsuario.converterUsuarioToOut(usuario);
		usuarioOut.add(linkTo(methodOn(UsuarioRestController.class).buscarUsuarioAlterar(usuarioOut.getId())).withSelfRel());
		
		usuarioOut.add(linkTo(methodOn(UsuarioRestController.class).listarUsuario()).withRel("usuarios"));
		
		return usuarioOut;
	}
	
	@ApiOperation("Relatório de usuário em pdf")
	@GetMapping(path = "/usuario/relatorio", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> imprimeRelatorioPdf(){
		
		byte[] relatorio = jasperReportsService.imprimeRelatorioNoNavegador("usuario");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(relatorio);
	}
	

}
