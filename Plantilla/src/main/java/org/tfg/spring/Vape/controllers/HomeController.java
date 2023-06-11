package org.tfg.spring.Vape.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tfg.spring.Vape.exception.DangerException;
import org.tfg.spring.Vape.helpers.PRG;
import org.tfg.spring.Vape.services.TipoService;
import org.tfg.spring.Vape.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private TipoService tipoService;
	
	@Autowired
	private UserService userService;
	
	/*
	@Autowired
	private UsuarioService usuarioService;
	*/
	
	@GetMapping("/")
	public String home(
			ModelMap m, HttpSession s
			)	 
	{
		m.put("tipos",tipoService.getTipos());
		m.put("view", "home/home");
		return "_t/frame";
	}
	
	@GetMapping("/contacto")
	public String contacto(
			ModelMap m, HttpSession s
			)	 
	{
		m.put("view", "home/contacto");
		return "_t/frame";
	}
	
	@GetMapping("/aviso_legal")
	public String avisoLegal(
			ModelMap m, HttpSession s
			)	 
	{
		m.put("view", "home/aviso_legal");
		return "_t/frame";
	}
	
	@GetMapping("/trabaja_con_nosotros")
	public String trabajaConNosotros(
			ModelMap m, HttpSession s
			)	 
	{
		m.put("view", "home/trabaja_con_nosotros");
		return "_t/frame";
	}
	
	@GetMapping("/quiero_exponer")
	public String quieroExponer(
			ModelMap m, HttpSession s
			)	 
	{
		m.put("view", "home/exponer");
		return "_t/frame";
	}
	
	@PostMapping("/quiero_exponer")
	public String quieroExponerPost(
			@RequestParam("nombre") String nombre,
			@RequestParam("apellido1") String apellido1,
			@RequestParam("apellido2") String apellido2,
			@RequestParam("dni") String dni,
			@RequestParam("email") String email
			) throws DangerException {
		try {
			userService.emailExponer(nombre,apellido1,apellido2,dni,email);
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/quiero_exponer");
		}
		return "redirect:/";
	}
	
	/*Desde el formulario register
	@PostMapping("/confirmar/enviar")
	public String enviar(
			@ModelAttribute Usuario usuario,
			ModelMap m,
			HttpSession s
			) throws Exception {
		Usuario usuarioGuardado = new Usuario();
		try {
			 usuarioGuardado = usuarioService.saveUsuario(usuario);
		}catch (Exception e) {
			PRG.error("Usuario ya registrado");
		}
		
		s.setAttribute("usuario", usuarioGuardado);
		s.setAttribute("reservada",false);
		usuarioService.envioConfirmarRegistro(usuario.getEmail());
		//Redirigir a vista de espera, crear vista
		m.put("usuario", usuario);
		m.put("view", "home/esperaConfirmar");
		return "_t/frame";
	}
	*/

	
}