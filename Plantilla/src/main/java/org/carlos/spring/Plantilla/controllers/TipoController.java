package org.carlos.spring.Plantilla.controllers;

import java.util.List;
import org.carlos.spring.Plantilla.entities.Tipo;
import org.carlos.spring.Plantilla.exception.DangerException;
import org.carlos.spring.Plantilla.helpers.PRG;
import org.carlos.spring.Plantilla.services.TipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tipo")
public class TipoController {

	@Autowired
	private TipoService tipoService;

	@GetMapping("c")
	public String cGet(ModelMap m) {
		m.put("view", "tipo/c");
		return "_t/frame";
	}

	@PostMapping("c")
	public String cPost(@RequestParam("nombre") String nombre) throws DangerException {
		try {
			tipoService.saveTipo(nombre);
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/tipo/r");
		}
		return "redirect:/tipo/r";
	}

	@GetMapping("r")
	public String rGet(ModelMap m) {
		List<Tipo> tipos = tipoService.getTipos();
		m.put("tipos", tipos);
		m.put("view", "tipo/r");
		return "_t/frame";
	}

	@GetMapping("u")
	public String uGet(@RequestParam("id") Long idTipo, ModelMap m) {
		Tipo tipo = tipoService.getTipoById(idTipo);

		m.put("tipo", tipo);
		m.put("view", "tipo/u");

		return "_t/frame";
	}

	@PostMapping("u")
	public String uPost(@RequestParam("idTipo") Long idTipo,
			@RequestParam("nombre") String nombre) throws DangerException {
		String retorno = "redirect:/tipo/r";
		try {
			tipoService.updateTipo(idTipo, nombre);
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/tipo/r");
		}
		return retorno;
	}

	@PostMapping("d")
	public String d(@RequestParam("id") Long id) {
		tipoService.deleteTipo(id);
		return "redirect:/tipo/r";
	}

}

