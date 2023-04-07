package org.carlos.spring.Plantilla.services;

import java.time.LocalDate;
import java.util.List;
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

	public void saveUsuario(
			String nombre,
			String apellidos,
			LocalDate fnac,
			String email,
			String loginName,
			String password
			) throws Exception {
		Usuario usuario = Usuario.builder().nombre(nombre).apellidos(apellidos).fnac(fnac).email(email).loginName(loginName).password(password).build();
		try {
			usuarioRepository.saveAndFlush(usuario);
		} catch (Exception e) {
			throw new Exception("El usuario " + loginName + " ya existe");
		}
	}

	public Usuario getUsuarioById(Long id) {
		return usuarioRepository.findById(id).get();
	}

	public void updateUsuario(Long id, String loginName) throws Exception {
		Usuario usuario = usuarioRepository.findById(id).get();
		usuario.setLoginName(loginName);
		try {
			usuarioRepository.saveAndFlush(usuario);
		} catch (Exception e) {
			throw new Exception("El usuario " + loginName + " ya existe");
		}
	}

	public void deleteUsuario(Long id) {
		Usuario usuario = usuarioRepository.findById(id).get();
		usuarioRepository.delete(usuario);
	}
}
