package org.carlos.spring.Plantilla.entities;

import java.time.LocalDate;

import org.carlos.spring.Plantilla.dto.UsuarioDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Usuario {
	
	public Usuario(UsuarioDTO usuarioDTO) {
		this.nombre = usuarioDTO.getName();
		this.apellidos = usuarioDTO.getSurname();
		this.fnac = usuarioDTO.getFnac();
		this.email = usuarioDTO.getMail();
		this.loginName = usuarioDTO.getLogin();
		this.password = usuarioDTO.getPass();
		this.id = usuarioDTO.getId();
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//@NonNull
	private String nombre;
	
	private String apellidos;
	
	private LocalDate fnac;
	
	private String email;
	
	@Column(unique = true)
	private String loginName;
	
	private String password;
	
	
	/*@ManyToOne
	private Rol rol;
	*/

}

