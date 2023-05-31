package org.tfg.spring.Vape.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tfg.spring.Vape.entities.Entrada;
import org.tfg.spring.Vape.entities.User;
import org.tfg.spring.Vape.exception.DangerException;
import org.tfg.spring.Vape.helpers.H;
import org.tfg.spring.Vape.helpers.PRG;
import org.tfg.spring.Vape.services.EntradaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/entrada")
public class EntradaController {

	@Autowired
	private EntradaService entradaService;
	

	
	@GetMapping("c")
	public String cGet(ModelMap m, HttpSession s) throws DangerException {
		try {
			H.isRolOk("admin", (User)(s.getAttribute("usuario")));
		} catch (Exception e) {
			PRG.error(e.getMessage());
		}
		m.put("view", "entrada/c");
		return "_t/frame";
	}

	@PostMapping("c")
	public String cPost(
			@RequestParam("numeroMax") int numeroMax,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@RequestParam("fecha") LocalDate fecha,
			HttpSession s
			) throws DangerException {
		//TO-DO
		//Comprobar aquí y en javascript si el numero maximo es un numero valido
		try {
			H.isRolOk("admin", (User)(s.getAttribute("usuario")));
		} catch (Exception e) {
			PRG.error("Acceso denegado");
		}
		int numeroVen = 0;
		try {
			entradaService.saveEntrada(numeroMax,numeroVen,fecha);
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/entrada/r");
		}
		return "redirect:/entrada/r";
	}

	@GetMapping("r")
	public String rGet(ModelMap m, HttpSession s) throws DangerException {
		try {
			H.isRolOk("admin", (User)(s.getAttribute("usuario")));
		} catch (Exception e) {
			PRG.error("Acceso denegado");
		}
		List<Entrada> entradas = entradaService.getEntradas();
		m.put("entradas", entradas);
		m.put("view", "entrada/r");
		return "_t/frame";
	}

	@GetMapping("u")
	public String uGet(@RequestParam("id") Long idEntrada, ModelMap m, HttpSession s) throws DangerException {
		try {
			H.isRolOk("admin", (User)(s.getAttribute("usuario")));
		} catch (Exception e) {
			PRG.error("Acceso denegado");
		}
		Entrada entrada = entradaService.getEntradaById(idEntrada);
		m.put("entrada", entrada);
		m.put("view", "entrada/u");

		return "_t/frame";
	}

	@PostMapping("u")
	public String uPost(
			@RequestParam("idEntrada") Long idEntrada,
			@RequestParam("numeroMax") int numeroMax,
			@RequestParam("numeroVen") int numeroVen,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@RequestParam("fecha") LocalDate fecha,
			HttpSession s
			) throws DangerException {
		String retorno = "redirect:/entrada/r";
		try {
			H.isRolOk("admin", (User)(s.getAttribute("usuario")));
		} catch (Exception e) {
			PRG.error("Acceso denegado");
		}
		
		try {
			
			entradaService.updateEntrada(idEntrada, numeroMax, numeroVen, fecha);
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/entrada/r");
		}
		return retorno;
	}

	@PostMapping("d")
	public String d(@RequestParam("id") Long id, HttpSession s) throws DangerException {
		try {
			H.isRolOk("admin", (User)(s.getAttribute("usuario")));
		} catch (Exception e) {
			PRG.error("Acceso denegado");
		}
		entradaService.deleteEntrada(id);
		return "redirect:/entrada/r";
	}

}

