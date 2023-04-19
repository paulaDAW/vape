package org.carlos.spring.Plantilla.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.carlos.spring.Plantilla.dto.UsuarioDTO;
import org.carlos.spring.Plantilla.entities.Usuario;
import org.carlos.spring.Plantilla.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

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
	public void saveUsuario(Usuario usuario) throws Exception {
		/////Usuario usuario = Usuario.builder().nombre(nombre).apellidos(apellidos).fnac(fnac).email(email).loginName(loginName).password(password).build();
		//Usuario usuario = new Usuario(usuarioDTO);
		try {
			usuarioRepository.saveAndFlush(usuario);
			//usuario = usuarioRepository.saveAndFlush(usuario);
		} catch (Exception e) {
			throw new Exception("El usuario " + usuario.getLoginName() + " ya existe");
		}
		//return usuario;
	}

	public Usuario getUsuarioById(Long id) {
		return usuarioRepository.findById(id).get();
	}

	//public void updateUsuario(UsuarioDTO usuarioDTO) throws Exception {
	public void updateUsuario(Long idUsuario/*,String nombre, String apellidos*/,String loginName/*,String email,LocalDate fnac*/) throws Exception {
		Usuario usuario = usuarioRepository.findById(idUsuario).get();
		//Usuario usuario = new Usuario(usuarioDTO);
		usuario.setLoginName(loginName);
		/*
		usuario.setNombre(nombre);
		usuario.setApellidos(apellidos);
		usuario.setEmail(email);
		usuario.setFnac(fnac);*/
		
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
}
