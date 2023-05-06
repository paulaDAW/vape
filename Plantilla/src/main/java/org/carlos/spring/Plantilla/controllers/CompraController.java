package org.carlos.spring.Plantilla.controllers;


import java.time.LocalDate;

import org.carlos.spring.Plantilla.entities.Usuario;
import org.carlos.spring.Plantilla.exception.DangerException;
import org.carlos.spring.Plantilla.helpers.PRG;
import org.carlos.spring.Plantilla.services.EntradaCompradaService;
import org.carlos.spring.Plantilla.services.EntradaCompradaService;

//import org.carlos.spring.Plantilla.entities.entityMayuscula;

import org.carlos.spring.Plantilla.services.EntradaService;
import org.carlos.spring.Plantilla.services.TipoService;
//import org.carlos.spring.Plantilla.services.entityMayusculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/comprar")
public class CompraController {
	
	@Autowired
	private EntradaService entradaService;
	
	@Autowired
	private TipoService tipoService;
	
	@Autowired
	private EntradaCompradaService entradaCompradaService;

	@GetMapping("r")
	public String r(ModelMap m) {
		m.put("entradas", entradaService.getEntradas());
		m.put("tipos", tipoService.getTipos());
		m.put("view", "comprar/r");
		return "_t/frame";
	}
	
	@PostMapping("r")
	public String rPost(
			@RequestParam("idTipo") Long idTipo,
			@RequestParam("cantidad") int cantidad,
			@RequestParam("idEntrada") Long idEntrada,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@RequestParam("fecha") LocalDate fecha,
			HttpSession s
			) throws DangerException {
		String retorno="redirect:/";
		LocalDate fechaCompra = LocalDate.now();
		try {
			entradaCompradaService.saveEntradaComprada((Usuario) (s.getAttribute("usuario")) ,idTipo, idEntrada, cantidad, fecha,fechaCompra);
		}catch(Exception e){
			PRG.error(e.getMessage(),"/");
		}
		return retorno;
	}


	

	


}

