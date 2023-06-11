package org.tfg.spring.Vape.controllers;


import org.tfg.spring.Vape.entities.Tarjeta;
import org.tfg.spring.Vape.entities.User;
import org.tfg.spring.Vape.exception.DangerException;
import org.tfg.spring.Vape.helpers.H;
import org.tfg.spring.Vape.helpers.PRG;
import org.tfg.spring.Vape.services.TarjetaService;
import org.tfg.spring.Vape.services.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/tarjeta")
public class TarjetaController {

	@Autowired
	private TarjetaService tarjetaService;
	
	@Autowired
	private UserService userService;

	@GetMapping("c")
	public String cGet(ModelMap m, HttpSession s) throws DangerException {
		try {
			H.isLogged((User)(s.getAttribute("usuario")));
		}catch(Exception e){
			PRG.error(e.getMessage(),"/login");
		}
		m.put("view", "tarjeta/c");
		return "_t/frame";
	}

	@GetMapping("create")
	public String create(ModelMap m,HttpSession s) throws DangerException{
		try {
			H.isLogged((User)(s.getAttribute("usuario")));
		}catch(Exception e){
			PRG.error(e.getMessage(),"/login");
		}
		m.put("view", "tarjeta/add");
		return "_t/frame";
	}
	
	@PostMapping("c")
	public String cPost(
			@ModelAttribute Tarjeta tarjeta,
			HttpSession s,
			ModelMap m
			) throws DangerException {
		try {
			H.isLogged((User)(s.getAttribute("usuario")));
		}catch(Exception e){
			PRG.error(e.getMessage(),"/login");
		}
		User usuario = (User)s.getAttribute("usuario");
		try {
			tarjetaService.saveTarjeta(tarjeta, usuario.getId());
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/tarjeta/r");
		}
		m.put("usuario", userService.getUsuarioById(usuario.getId()));
		//m.put("view", "perfil/r");
		//return "_t/frame";
		return "redirect:/tarjeta/confirmada";
	}
	
	@GetMapping("confirmada")
	public String addConfirmada(ModelMap m) {
		m.put("view", "tarjeta/confirmada");
		return "_t/frame";
	} 

	/*
	@GetMapping("r")
	public String rGet(ModelMap m) {
		List<Tarjeta> tarjetas = tarjetaService.getTarjetas();
		m.put("tarjetas", tarjetas);
		m.put("view", "tarjeta/r");
		return "_t/frame";
	}
	*/
	
	@GetMapping("u")
	public String uGet(@RequestParam("id") Long idTarjeta, ModelMap m) {
		Tarjeta tarjeta = tarjetaService.getTarjetaById(idTarjeta);

		m.put("tarjeta", tarjeta);
		m.put("view", "tarjeta/update");

		return "_t/frame";
	}
	
	@PostMapping("u")
	public String uPost(@ModelAttribute Tarjeta tarjeta,
			@RequestParam("id") Long idTarjeta
			) throws DangerException {
		String retorno = "redirect:/tarjeta/actualizada";
		try {
			tarjetaService.updateTarjeta(idTarjeta, tarjeta);
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/tarjeta/r");
		}
		return retorno;
	}
	
	@GetMapping("actualizada")
	public String updateConfirmada(ModelMap m) {
		m.put("view", "tarjeta/actualizada");
		return "_t/frame";
	} 

	@PostMapping("d")
	public String d(@RequestParam("id") Long id, ModelMap m) {
		Tarjeta tarjeta = tarjetaService.getTarjetaById(id);
		tarjetaService.deleteTarjeta(id);
		m.put("usuario", userService.getUsuarioById(tarjeta.getUsuario().getId()));
		return "redirect:/perfil";
	}
	
	
	@PostMapping("aniadirCompra")
	public String createDesdeCompra(
			@ModelAttribute Tarjeta tarjeta,
			HttpSession s,
			ModelMap m
			) throws DangerException {
		try {
			H.isLogged((User)(s.getAttribute("usuario")));
		}catch(Exception e){
			PRG.error(e.getMessage(),"/login");
		}
		User usuario = (User)s.getAttribute("usuario");
		try {
			tarjetaService.saveTarjeta(tarjeta, usuario.getId());
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/tarjeta/r");
		}
		m.put("usuario", userService.getUsuarioById(usuario.getId()));
		//m.put("view", "perfil/r");
		//return "_t/frame";
		return "redirect:../comprar/resumen";
	}

}

