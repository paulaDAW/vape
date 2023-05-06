package org.carlos.spring.Plantilla.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.carlos.spring.Plantilla.dto.UsuarioDTO;
import org.carlos.spring.Plantilla.entities.Rol;
import org.carlos.spring.Plantilla.entities.Usuario;
import org.carlos.spring.Plantilla.repositories.RolRepository;
import org.carlos.spring.Plantilla.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RolRepository rolRepository;
	
	public List<Usuario> getUsuarios() {
		return usuarioRepository.findAll();
	}
	
	/*
	public List<UsuarioDTO> getUsuarios() {
		List<UsuarioDTO> usuariosDTO = new ArrayList<UsuarioDTO>();
		for(Usuario usuario : usuarioRepository.findAll()) {
			usuariosDTO.add(new UsuarioDTO(usuario));
		}
		return usuariosDTO;
	}
	*/

	//public UsuarioDTO saveUsuario(UsuarioDTO usuarioDTO) throws Exception {
	public Usuario saveUsuario(Usuario usuario) throws Exception {
		/////Usuario usuario = Usuario.builder().nombre(nombre).apellidos(apellidos).fnac(fnac).email(email).loginName(loginName).password(password).build();
		//Usuario usuario = new Usuario(usuarioDTO);
		usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
		
		Rol rol = rolRepository.findByNombre("Cliente");
		usuario.setRol(rol);
		try {
			usuarioRepository.saveAndFlush(usuario);
			//usuario = usuarioRepository.saveAndFlush(usuario);
		} catch (Exception e) {
			throw new Exception("El email " + usuario.getEmail() + " ya se ha sido registrado");
		}
		return usuario;
	}

	public Usuario getUsuarioById(Long id) {
		return usuarioRepository.findById(id).get();
	}

	//public void updateUsuario(UsuarioDTO usuarioDTO) throws Exception {
	public void updateUsuario(
			Long idUsuario,
			String nombre,
			String apellido1,
			String apellido2,
			String tarjeta,
			String email) throws Exception {
		Usuario usuario = usuarioRepository.findById(idUsuario).get();
		//Usuario usuario = new Usuario(usuarioDTO);
		usuario.setNombre(nombre);
		usuario.setApellido1(apellido1);
		usuario.setApellido2(apellido2);
		usuario.setEmail(email);
		usuario.setTarjeta(tarjeta);//Encriptar		
		try {
			usuarioRepository.saveAndFlush(usuario);
		} catch (Exception e) {
			throw new Exception("Ha ocurrido un error. Inténtelo de nuevo.");
		}
		
	}

	public void deleteUsuario(Long id) {
		Usuario usuario = usuarioRepository.findById(id).get();
		usuarioRepository.delete(usuario);
	}

	public Usuario autenticarUsuario(String email, String password) throws Exception {
		Usuario usuario = null;
		try {
			usuario = usuarioRepository.findByEmail(email);
			if(usuario == null) {
				throw new Exception("No esta registrado");
			}
		} catch (Exception e) {
			throw new Exception("El usuario " + email.split("@")[0] + " no existe.");
		}
		if(!(new BCryptPasswordEncoder()).matches(password, usuario.getPassword())) {
			throw new Exception("La contraseña no es correcta");
		}
		return usuario;
	}
}
