package org.carlos.spring.Plantilla.controllers;

import java.util.List;
import org.carlos.spring.Plantilla.entities.Rol;
import org.carlos.spring.Plantilla.entities.Usuario;
import org.carlos.spring.Plantilla.exception.DangerException;
import org.carlos.spring.Plantilla.helpers.PRG;
import org.carlos.spring.Plantilla.services.RolService;
import org.carlos.spring.Plantilla.helpers.H;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/rol")
public class RolController {

	@Autowired
	private RolService rolService;

	@GetMapping("c")
	public String cGet(ModelMap m, HttpSession s) throws DangerException {
		try {
			H.isRolOk("Admin", (Usuario)(s.getAttribute("usuario")));
		} catch (Exception e) {
			PRG.error("No puedes acceder ("+ e.getMessage() + ")");
		}
		m.put("view", "rol/c");
		return "_t/frame";
	}

	@PostMapping("c")
	public String cPost(
			@RequestParam("nombre") String nombre,
			HttpSession s
			) throws DangerException {
		try {
			H.isRolOk("Admin", (Usuario)(s.getAttribute("usuario")));
		} catch (Exception e) {
			PRG.error("Acceso denegado");
		}
		try {
			rolService.saveRol(nombre);
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/rol/r");
		}
		return "redirect:/rol/r";
	}

	@GetMapping("r")
	public String rGet(
			ModelMap m,
			HttpSession s
			) throws DangerException {
		try {
			H.isRolOk("Admin", (Usuario)(s.getAttribute("usuario")));
		} catch (Exception e) {
			PRG.error("Acceso denegado");
		}

		List<Rol> roles = rolService.getRoles();

		m.put("roles", roles);
		m.put("view", "rol/r");
		return "_t/frame";
	}

	@GetMapping("u")
	public String uGet(@RequestParam("id") Long idRol, ModelMap m, HttpSession s) throws DangerException {
		try {
			H.isRolOk("Admin", (Usuario)(s.getAttribute("usuario")));
		} catch (Exception e) {
			PRG.error("Acceso denegado");
		}
		Rol rol = rolService.getRolById(idRol);

		m.put("rol", rol);
		m.put("view", "rol/u");

		return "_t/frame";
	}

	@PostMapping("u")
	public String uPost(@RequestParam("idRol") Long idRol,
			@RequestParam("nombre") String nombre, HttpSession s) throws DangerException {
		
		String retorno = "redirect:/rol/r";
		try {
			H.isRolOk("Admin", (Usuario)(s.getAttribute("usuario")));
		} catch (Exception e) {
			PRG.error("Acceso denegado");
		}
		try {
			rolService.updateRol(idRol, nombre);
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/rol/r");
		}
		return retorno;
	}

	@PostMapping("d")
	public String d(@RequestParam("id") Long id, HttpSession s) throws DangerException {
		
		try {
			H.isRolOk("Admin", (Usuario)(s.getAttribute("usuario")));
		} catch (Exception e) {
			PRG.error("Acceso denegado");
		}
		rolService.deleteRol(id);
		return "redirect:/rol/r";
	}

}

