package org.carlos.spring.Plantilla.controllers;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.carlos.spring.Plantilla.dto.UsuarioDTO;
import org.carlos.spring.Plantilla.entities.Usuario;
import org.carlos.spring.Plantilla.exception.DangerException;
import org.carlos.spring.Plantilla.helpers.PRG;
import org.carlos.spring.Plantilla.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("c")
	public String cGet(ModelMap m) {
		m.put("view", "usuario/c");
		return "_t/frame";
	}
	/*
	@PostMapping("c")
	public String cPost(
			@RequestParam("nombre") String nombre,
			@RequestParam("apellidos") String apellidos,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@RequestParam("fnac") LocalDate fnac,
			@RequestParam("email") String email,
			@RequestParam("loginName") String loginName,
			@RequestParam("password") String password
			) throws DangerException {
		try {
			usuarioService.saveUsuario(nombre,apellidos,fnac,email,loginName,password);
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/usuario/r");
		}
		return "redirect:/usuario/r";
	}
	*/
	
	@PostMapping("c")
	public String cPost(
			@ModelAttribute Usuario usuario //Recoge los campos del formulario de creación
			) throws DangerException {
		//System.out.println(usuario.getNombre());
		try {
			usuarioService.saveUsuario(usuario);
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/usuario/r");
		}
		/*
		RestTemplate rt = new RestTemplate();
		UsuarioDTO u = new UsuarioDTO(usuario);
		rt.postForEntity("http://localhost:8080/api/usuario/c", u, UsuarioDTO.class);
		*/
		return "redirect:/usuario/r";
		
	}

	@GetMapping("r")
	public String rGet(ModelMap m) {
		//List<UsuarioDTO> usuarios = usuarioService.getUsuarios();
		List<Usuario> usuarios = usuarioService.getUsuarios();
		m.put("usuarios", usuarios);
		m.put("view", "usuario/r");
		return "_t/frame";
		
		
		/*
		 * Como estaba antes
		 * 
		 *  RestTemplate rt = new RestTemplate();
			m.put("usuarios", rt.getForEntity("http://localhost:8080/api/usuario/r",
				Usuario[].class).getBody());
				
			Si lo hago como esta fuera, que sentido tiene el DTO??
		 */
		/*
		RestTemplate rt = new RestTemplate();
		ArrayList<Usuario> usuarios = new ArrayList<>();
		UsuarioDTO[] usuariosDTO = rt.getForEntity("http://localhost:8080/api/usuario/r",
				UsuarioDTO[].class).getBody();
		for( UsuarioDTO userDTO: usuariosDTO) {
			usuarios.add(new Usuario(userDTO));
		}
		
		m.put("usuarios", usuarios);
		//Esto devuelve una lista de usuariosDTO y lo vuelca en una lista de usarios
		//pero al ser dos tipos distintos, los atributos no coinciden y no se muestran en r
		m.put("view", "usuario/r");
		return "_t/frame";
		*/
	}

	@GetMapping("u")
	public String uGet(@RequestParam("id") Long idUsuario, ModelMap m) {
		Usuario usuario = usuarioService.getUsuarioById(idUsuario);
		
		m.put("usuario", usuario);
		m.put("view", "usuario/u");

		return "_t/frame";
	}

	@PostMapping("u")
	public String uPost(
			@RequestParam("idUsuario") Long idUsuario,
			@RequestParam("loginName") String loginName
			/*
			 * @RequestParam("nombre") String nombre,
			@RequestParam("apellidos") String apellidos,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@RequestParam("fnac") LocalDate fnac,
			@RequestParam("email") String email
			*/
			
			//@ModelAttribute Usuario usuario
			) throws DangerException {
		//System.out.println(usuario.getApellidos()+"--"+usuario.getId());
		String retorno = "redirect:/usuario/r";
		try {
			usuarioService.updateUsuario(idUsuario, loginName /*, nombre, apellidos, loginName,email, fnac*/);
			/*
			RestTemplate rt = new RestTemplate();
			UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
			rt.postForEntity("http://localhost:8080/api/usuario/u", usuarioDTO, UsuarioDTO.class);
			*/
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/usuario/r");
		}
		return retorno;
	}

	@PostMapping("d")
	public String d(@RequestParam("id") Long id) {
		usuarioService.deleteUsuario(id);
		return "redirect:/usuario/r";
	}

}

