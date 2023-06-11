package org.tfg.spring.Vape.controllers;

//import java.lang.reflect.Array;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tfg.spring.Vape.dto.UserDto;
import org.tfg.spring.Vape.entities.User;
import org.tfg.spring.Vape.exception.DangerException;
import org.tfg.spring.Vape.helpers.H;
import org.tfg.spring.Vape.helpers.PRG;
import org.tfg.spring.Vape.services.RolService;
import org.tfg.spring.Vape.services.UserService;

import jakarta.servlet.http.HttpSession;


//import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UserService usuarioService;
	
	@Autowired
	private RolService rolService;
	
	//@Autowired
	//private EntradaCompradaService entradaCompradaService;

	@GetMapping("c")
	public String cGet(
			ModelMap m,
			HttpSession s
			) throws DangerException {
		try {
			H.isRolOk("admin", (User)(s.getAttribute("usuario")));
		} catch (Exception e) {
			PRG.error("Acceso denegado");
		}
		m.put("roles", rolService.getRoles());
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
			@ModelAttribute UserDto usuario, //Recoge los campos del formulario de creación
			HttpSession s
			) throws DangerException {
		//System.out.println(usuario.getNombre());
		try {
			H.isRolOk("admin", (User)(s.getAttribute("usuario")));
		} catch (Exception e) {
			PRG.error("Acceso denegado");
		}
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
	public String rGet(ModelMap m, HttpSession s) throws DangerException {
		//List<UsuarioDTO> usuarios = usuarioService.getUsuarios();
		try {
			H.isRolOk("admin", (User)(s.getAttribute("usuario")));
		} catch (Exception e) {
			PRG.error("Acceso denegado");
		}
		List<User> usuarios = usuarioService.getUsuarios();
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
	public String uGet(
			@RequestParam("id") Long idUsuario,
			ModelMap m,
			HttpSession s
			) throws DangerException {
		try {
			H.isLogged((User)(s.getAttribute("usuario")));
		}catch(Exception e){
			PRG.error(e.getMessage(),"/login");
		}
		User usuario = usuarioService.getUsuarioById(idUsuario);

		/*

		String[] numerosTarjeta = usuario.getTarjeta().split(" ");
		String tarjeta = "**** **** **** "+ numerosTarjeta[numerosTarjeta.length -1];
		m.put("tarjeta", tarjeta);
		*/
		m.put("usuario", usuario);
		m.put("view", "usuario/u");

		return "_t/frame";
	}
	
	
	/*
	 Parte api-rest uPost
	  //@ModelAttribute Usuario usuario
	 
		RestTemplate rt = new RestTemplate();
		UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
		rt.postForEntity("http://localhost:8080/api/usuario/u", usuarioDTO, UsuarioDTO.class);
	
	 */

	@PostMapping("u")
	public String uPost(
			@RequestParam("idUsuario") Long idUsuario,
			@RequestParam("apellido2") String apellido2,
			@RequestParam("nombre") String nombre,
			@RequestParam("apellido1") String apellido1,
			@RequestParam("email") String email,
			HttpSession s
			) throws DangerException {
		String retorno = "redirect:/";
		/*
		 Si es admin, llevar a la lista de usuarios
		 Si es usuario, redirigir a su página del perfil
		 Si no está registrado, redirigir login o registro
		 
		 */
		
		try {
			H.isLogged((User)(s.getAttribute("usuario")));
		}catch(Exception e){
			PRG.error(e.getMessage(),"/login");
		}
		try {
			User usuarioActualizdo = usuarioService.updateUsuario(idUsuario, nombre, apellido1, apellido2, email);

			s.setAttribute("usuario", usuarioActualizdo);
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/");
		}
		return retorno;
	}

	@PostMapping("d")
	public String d(@RequestParam("id") Long id, HttpSession s) throws DangerException {
		try {
			H.isRolOk("admin", (User)(s.getAttribute("usuario")));
		} catch (Exception e) {
			PRG.error("Acceso denegado");
		}
		usuarioService.deleteUsuario(id);
		return "redirect:/usuario/r";
	}

}

