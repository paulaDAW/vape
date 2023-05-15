package org.carlos.spring.Plantilla.controllers;



import org.carlos.spring.Plantilla.entities.Usuario;
import org.carlos.spring.Plantilla.exception.DangerException;
import org.carlos.spring.Plantilla.helpers.PRG;
import org.carlos.spring.Plantilla.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("/login")
	public String login(ModelMap m) {
		m.put("view", "home/login");
		return "_t/frame";
	}
	
	@PostMapping("/login")
	public String loginPost(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			HttpSession s
			) throws DangerException {
		try {
			Usuario usuario = usuarioService.autenticarUsuario(email,password);
			if(usuario.isActivado()==false) {
				throw new Exception("Debe de confirmar su registro para poder continuar");
			}
			s.setAttribute("usuario", usuario);
			s.setAttribute("rolAdmin", "admin");
			s.setAttribute("reservada",false);
		} catch (Exception e) {
			PRG.error(e.getMessage(),"/");
		}
		return "redirect:/";
	}
	
	@GetMapping("/register")
	public String register(ModelMap m) {
		m.put("view", "home/register");
		return "_t/frame";
	}
	
	@PostMapping("/register")
	public String registerPost(
			@ModelAttribute Usuario usuario,
			HttpSession s
			) throws DangerException {
		try {
			
			s.setAttribute("usuario", usuarioService.saveUsuario(usuario));
			s.setAttribute("reservada",false);
		} catch (Exception e) {
			PRG.error(e.getMessage(),"/");
		}
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession s) {
		if(s.getAttribute("usuario")!=null) {
			s.removeAttribute("usuario");
		}
		return "redirect:/";
	}
}
