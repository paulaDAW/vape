package org.carlos.spring.Plantilla.controllers;

import java.time.LocalDate;
import java.util.List;
import org.carlos.spring.Plantilla.entities.Horario;
import org.carlos.spring.Plantilla.exception.DangerException;
import org.carlos.spring.Plantilla.helpers.PRG;
import org.carlos.spring.Plantilla.services.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/horario")
public class HorarioController {

	@Autowired
	private HorarioService horarioService;

	@GetMapping("c")
	public String cGet(ModelMap m) {
		m.put("view", "horario/c");
		return "_t/frame";
	}

	@PostMapping("c")
	public String cPost(
			@RequestParam("hora") String hora
			) throws DangerException {
		try {
			horarioService.saveHorario(hora);
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/horario/r");
		}
		return "redirect:/horario/r";
	}

	@GetMapping("r")
	public String rGet(ModelMap m) {
		List<Horario> horarios = horarioService.getHorarios();
		m.put("horarios", horarios);
		m.put("view", "horario/r");
		return "_t/frame";
	}

	@GetMapping("u")
	public String uGet(@RequestParam("id") Long idHorario, ModelMap m) {
		Horario horario = horarioService.getHorarioById(idHorario);

		m.put("horario", horario);
		m.put("view", "horario/u");

		return "_t/frame";
	}

	
	@PostMapping("u")
	public String uPost(
			@RequestParam("idHorario") Long idHorario,
			@RequestParam("hora") String hora
			) throws DangerException {
		String retorno = "redirect:/horario/r";
		try {
			horarioService.updateHorario(idHorario,hora);
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/horario/r");
		}
		return retorno;
	}
	

	@PostMapping("d")
	public String d(@RequestParam("id") Long id) {
		horarioService.deleteHorario(id);
		return "redirect:/horario/r";
	}

}

