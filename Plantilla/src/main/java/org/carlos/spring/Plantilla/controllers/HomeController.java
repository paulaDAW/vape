package org.carlos.spring.Plantilla.controllers;


import org.carlos.spring.Plantilla.services.TipoService;
import org.carlos.spring.Plantilla.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private TipoService tipoService;
	
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/")
	public String home(
			ModelMap m, HttpSession s
			)	 
	{
		m.put("tipos",tipoService.getTipos());
		m.put("view", "home/home");
		return "_t/frame";
	}
	
	@GetMapping("/confirmar/enviar")
	public void enviar() throws Exception {
		usuarioService.envioConfirmarRegistro("davidsuarezgarcia05@gmail.com");
		//Redirigir a vista de espera, crear vista
	}

	@GetMapping("/confirmar/registro")
	//Falla la partee del puerto 8080
	public String listo(ModelMap m) {
		m.put("view", "home/okey");//Misma vista, pero comprobar dato activo
		return "_t/frame";
	}
}