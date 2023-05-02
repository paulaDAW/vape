package org.carlos.spring.Plantilla.dto;

import java.time.LocalDate;

import org.carlos.spring.Plantilla.entities.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UsuarioDTO {
	/*
	public UsuarioDTO(Usuario usuario) {
		this.name = usuario.getNombre();
		this.surname = usuario.getApellidos();
		this.fnac = usuario.getFnac();
		this.mail = usuario.getEmail();
		this.login = usuario.getLoginName();
		this.pass = usuario.getPassword();
		this.id = usuario.getId();
	}
	*/
	private Long id;
	
	private String name;
	
	private String surname;
	
	private LocalDate fnac;
	
	private String mail;
	
	private String login;
	
	private String pass;
}
