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
			throw new Exception("El usuario " + usuario.getLoginName() + " ya existe");
		}
		return usuario;
	}

	public Usuario getUsuarioById(Long id) {
		return usuarioRepository.findById(id).get();
	}

	//public void updateUsuario(UsuarioDTO usuarioDTO) throws Exception {
	public void updateUsuario(Long idUsuario,String nombre, String apellidos,String loginName,String email,LocalDate fnac) throws Exception {
		Usuario usuario = usuarioRepository.findById(idUsuario).get();
		//Usuario usuario = new Usuario(usuarioDTO);
		usuario.setLoginName(loginName);
		usuario.setNombre(nombre);
		usuario.setApellidos(apellidos);
		usuario.setEmail(email);
		usuario.setFnac(fnac);
		try {
			usuarioRepository.saveAndFlush(usuario);
		} catch (Exception e) {
			throw new Exception("El usuario " + usuario.getLoginName() + " ya existe");
		}
		
	}

	public void deleteUsuario(Long id) {
		Usuario usuario = usuarioRepository.findById(id).get();
		usuarioRepository.delete(usuario);
	}

	public Usuario autenticarUsuario(String loginname, String password) throws Exception {
		Usuario usuario = null;
		try {
			usuario = usuarioRepository.findByLoginName(loginname);
			if(usuario == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new Exception("El usuario " + loginname.split("@")[0] + " no existe.");
		}
		if(!(new BCryptPasswordEncoder()).matches(password, usuario.getPassword())) {
			throw new Exception("La contraseña no es correcta");
		}
		return usuario;
	}
}
