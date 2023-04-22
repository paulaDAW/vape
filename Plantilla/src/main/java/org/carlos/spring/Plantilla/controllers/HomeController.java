package org.carlos.spring.Plantilla.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(
			ModelMap m, HttpSession s
			)
			 
	{
		m.put("view", "home/home");
		return "_t/frame";
	}
	

	
}