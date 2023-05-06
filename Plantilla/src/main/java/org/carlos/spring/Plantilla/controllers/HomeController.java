package org.carlos.spring.Plantilla.controllers;


import org.carlos.spring.Plantilla.services.TipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private TipoService tipoService;

	@GetMapping("/")
	public String home(
			ModelMap m, HttpSession s
			)	 
	{
		m.put("tipos",tipoService.getTipos());
		m.put("view", "home/home");
		return "_t/frame";
	}
	

	
}