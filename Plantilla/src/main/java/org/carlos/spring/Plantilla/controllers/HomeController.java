package org.carlos.spring.Plantilla.controllers;


import org.carlos.spring.Plantilla.entities.Usuario;
import org.carlos.spring.Plantilla.helpers.PRG;
import org.carlos.spring.Plantilla.services.TipoService;
import org.carlos.spring.Plantilla.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

	@PostMapping("/confirmar/registro")
	public String listo(ModelMap m,HttpSession s) throws Exception {
		Usuario usuario = (Usuario)s.getAttribute("usuario");
		/*System.out.println(usuario.getEmail());
		System.out.println(usuario.isActivado());*/
		Usuario usuarioActivo = usuarioService.activarUsuario(usuario.getEmail());
		s.setAttribute("usuario", usuarioActivo);
		m.put("usuario", usuario);
		m.put("view", "home/esperaConfirmar");//Misma vista, pero comprobar dato activo
		return "_t/frame";
	}
}