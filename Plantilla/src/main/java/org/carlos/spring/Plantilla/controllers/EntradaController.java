package org.carlos.spring.Plantilla.controllers;

import java.time.LocalDate;
import java.util.List;
import org.carlos.spring.Plantilla.entities.Entrada;
import org.carlos.spring.Plantilla.entities.Horario;
import org.carlos.spring.Plantilla.entities.Tipo;
import org.carlos.spring.Plantilla.exception.DangerException;
import org.carlos.spring.Plantilla.helpers.PRG;
import org.carlos.spring.Plantilla.services.EntradaService;
import org.carlos.spring.Plantilla.services.HorarioService;
import org.carlos.spring.Plantilla.services.TipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/entrada")
public class EntradaController {

	@Autowired
	private EntradaService entradaService;

	@Autowired
	private HorarioService horarioService;
	
	@Autowired
	private TipoService tipoService;
	
	@GetMapping("c")
	public String cGet(ModelMap m) {
		m.put("horarios", horarioService.getHorarios());
		m.put("view", "entrada/c");
		return "_t/frame";
	}

	@PostMapping("c")
	public String cPost(
			@RequestParam("numeroMax") int numeroMax,
			@RequestParam("idHorario") Long idHorario
			) throws DangerException {
		//TO-DO
		//Comprobar aquí y en javascript si el numero maximo es un numero valido
		 int numeroVen = 0;
		try {
			entradaService.saveEntrada(numeroMax,numeroVen,idHorario);
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/entrada/r");
		}
		return "redirect:/entrada/r";
	}

	@GetMapping("r")
	public String rGet(ModelMap m) {
		List<Entrada> entradas = entradaService.getEntradas();
		m.put("entradas", entradas);
		m.put("view", "entrada/r");
		return "_t/frame";
	}

	@GetMapping("u")
	public String uGet(@RequestParam("id") Long idEntrada, ModelMap m) {
		Entrada entrada = entradaService.getEntradaById(idEntrada);
		List<Horario> horarios=horarioService.getHorarios();
		m.put("entrada", entrada);
		m.put("horarios", horarios);
		m.put("view", "entrada/u");

		return "_t/frame";
	}

	@PostMapping("u")
	public String uPost(
			@RequestParam("idEntrada") Long idEntrada,
			@RequestParam("numeroMax") int numeroMax,
			@RequestParam("numeroVen") int numeroVen,
			@RequestParam("idHorario") Long idHorario
			) throws DangerException {
		String retorno = "redirect:/entrada/r";
		try {
			
			entradaService.updateEntrada(idEntrada, numeroMax, numeroVen, idHorario);
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/entrada/r");
		}
		return retorno;
	}

	@PostMapping("d")
	public String d(@RequestParam("id") Long id) {
		entradaService.deleteEntrada(id);
		return "redirect:/entrada/r";
	}

}

