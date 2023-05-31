package org.tfg.spring.Vape.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.tfg.spring.Vape.services.TipoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private TipoService tipoService;
	
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