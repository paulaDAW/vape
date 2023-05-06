package org.carlos.spring.Plantilla.controllers;


import java.time.LocalDate;

import org.carlos.spring.Plantilla.entities.Entrada;
import org.carlos.spring.Plantilla.entities.Usuario;
import org.carlos.spring.Plantilla.exception.DangerException;
import org.carlos.spring.Plantilla.helpers.PRG;
import org.carlos.spring.Plantilla.services.EntradaCompradaService;


import org.carlos.spring.Plantilla.services.EntradaService;
import org.carlos.spring.Plantilla.services.TipoService;
import org.carlos.spring.Plantilla.helpers.H;
import org.carlos.spring.Plantilla.services.UsuarioService;
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
	private UsuarioService usuarioService;
	
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
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@RequestParam("fecha") LocalDate fecha,
			HttpSession s
			) throws DangerException {
		String retorno="redirect:/comprar/confirmacion";
		LocalDate fechaCompra = LocalDate.now();
		Entrada entrada = entradaService.getEntradaByFecha(fecha);
		try {
			H.isLogged((Usuario)(s.getAttribute("usuario")));
		}catch(Exception e){
			PRG.error(e.getMessage(),"/login");
		}
		try {

			if((entrada.getNumeroVendido()+cantidad) > entrada.getNumeroMaximo()) {
				throw new Exception("Supera el límite de entradas disponibles");
			}
			entradaCompradaService.saveEntradaComprada((Usuario)(s.getAttribute("usuario")) ,idTipo, entrada.getId(), cantidad, fechaCompra);

			//usuarioService.confirmacionCompra(email);


		}catch(Exception e){
			PRG.error(e.getMessage(),"/");
		}
		return retorno;
	}

	
	@GetMapping("confirmacion")
	public String email(ModelMap m) throws Exception {
		m.put("view", "comprar/confirmado");
		return "_t/frame";
	}
	/*@GetMapping("confirmacion")
	public String email(@RequestParam("id") Long idUsuario, ModelMap m) throws Exception {
		Usuario usuario = usuarioService.getUsuarioById(idUsuario);

		m.put("usuario", usuario);
		m.put("view", "comprar/confirmado");
		return "_t/frame";
	}*/
	
	/*@PostMapping("confirmacion")
	public String emailPost(
			@RequestParam("idUsuario") Long idUsuario,
			@RequestParam("email") String email
			) throws Exception {
		try {
			usuarioService.confirmacionCompra(email);
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/comprar/r");
		}
		return "redirect:/";
	}
	*/


}

