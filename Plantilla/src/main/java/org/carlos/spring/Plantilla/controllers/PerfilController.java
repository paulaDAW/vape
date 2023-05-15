package org.carlos.spring.Plantilla.controllers;

import java.util.List;

import org.carlos.spring.Plantilla.entities.EntradaComprada;
import org.carlos.spring.Plantilla.entities.Usuario;
import org.carlos.spring.Plantilla.exception.DangerException;
import org.carlos.spring.Plantilla.helpers.H;
import org.carlos.spring.Plantilla.helpers.PRG;
import org.carlos.spring.Plantilla.services.EntradaCompradaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;


import jakarta.servlet.http.HttpSession;

@Controller
public class PerfilController {
	
	@Autowired
	private EntradaCompradaService entradaCompradaService;


	@GetMapping("/perfil")
	public String r(ModelMap m, HttpSession s) throws DangerException {
	
		try {
			H.isLogged((Usuario)(s.getAttribute("usuario")));
		}catch(Exception e){
			PRG.error(e.getMessage(),"/login");
		}
		
		Usuario usuario = (Usuario)(s.getAttribute("usuario"));
		String[] numerosTarjeta = usuario.getTarjeta().split(" ");
		String tarjeta = "**** **** **** "+ numerosTarjeta[numerosTarjeta.length -1];
		m.put("tarjeta", tarjeta);
		m.put("usuario", usuario );
		m.put("view", "perfil/r");
		return "_t/frame";
	}
	
	@GetMapping("/misentradas")
	public String rEntradas(ModelMap m, HttpSession s) throws DangerException {
		try {
			H.isLogged((Usuario)(s.getAttribute("usuario")));
		}catch(Exception e){
			PRG.error(e.getMessage(),"/login");
		}
		Usuario usuarioActual = (Usuario)(s.getAttribute("usuario"));
		List<EntradaComprada> entradas = entradaCompradaService.getMisEntradas(usuarioActual);
		//Comprobar que solo cada usuario pueda ver sus entradas
		//Pasar el id y comprobarlo con el de la sesion, tambien comprueba
		//que la sesion este iniciada
		m.put("entradas",entradas);
		m.put("view", "usuario/entradas");
		return "_t/frame";
	}
	

}

