
package org.carlos.spring.Plantilla.entities;


import java.util.Collection;

//import org.carlos.spring.Plantilla.dto.UsuarioDTO;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	/*
	public Usuario(UsuarioDTO usuarioDTO) {
		this.nombre = usuarioDTO.getName();
		this.apellidos = usuarioDTO.getSurname();
		this.fnac = usuarioDTO.getFnac();
		this.email = usuarioDTO.getMail();
		this.loginName = usuarioDTO.getLogin();
		this.password = usuarioDTO.getPass();
		this.id = usuarioDTO.getId();
		
	}
	*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//@NonNull
	private String nombre;
	
	private String apellido1;
	
	private String apellido2;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	@Nullable
	private String tarjeta;
	
	@ManyToOne
	private Rol rol;
	
	@OneToMany(mappedBy = "usuario")
	private Collection<EntradaComprada> entradaComprada;
	
	

}

