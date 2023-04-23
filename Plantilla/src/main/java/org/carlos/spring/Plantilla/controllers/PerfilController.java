package org.carlos.spring.Plantilla.controllers;

import java.util.List;

import org.carlos.spring.Plantilla.entities.Usuario;
import org.carlos.spring.Plantilla.exception.DangerException;
import org.carlos.spring.Plantilla.helpers.PRG;

import org.carlos.spring.Plantilla.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class PerfilController {
	
	@Autowired
	private UsuarioService usuarioService;


	@GetMapping("/perfil")
	public String r(ModelMap m, HttpSession s) {
	
		m.put("usuario", s.getAttribute("usuario"));
		m.put("view", "perfil/r");
		return "_t/frame";
	}
	

}

