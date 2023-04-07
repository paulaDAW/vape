package org.paula.spring.proyecto.controllers;

import org.paula.spring.proyecto.exception.DangerException;
import org.paula.spring.proyecto.helpers.PRG;
import org.paula.spring.proyecto.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
	
	
	@Autowired
	UsuarioService usuarioService;

	
	@GetMapping("/login")
	public String login(
			ModelMap m
			) {
		m.put("view", "home/login");
		return "_t/frame";
	}
	
	@PostMapping("/login")
	public String loginPost(
			@RequestParam("loginName") String loginName,
			@RequestParam("password") String password,
			HttpSession s
			) throws DangerException {
		try {
			//s.setAttribute("usuario", usuarioService.autenticarUsuario(loginName, password));
		}
		catch (Exception e) {
			PRG.error(e.getMessage(),"/");
		}
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(
			HttpSession s
			) {
		if (s.getAttribute("usuario")!=null) {
			s.removeAttribute("usuario");
		}
		return "redirect:/";
	}
}
