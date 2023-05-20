package org.carlos.spring.Plantilla.controllers;

import java.util.List;

import org.carlos.spring.Plantilla.entities.EntradaComprada;
import org.carlos.spring.Plantilla.entities.Usuario;
import org.carlos.spring.Plantilla.exception.DangerException;
import org.carlos.spring.Plantilla.helpers.H;
import org.carlos.spring.Plantilla.helpers.PRG;
import org.carlos.spring.Plantilla.services.EntradaCompradaService;
import org.carlos.spring.Plantilla.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class PerfilController {
	
	@Autowired
	private EntradaCompradaService entradaCompradaService;
	
	@Autowired
	private UsuarioService usuarioService;

	

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
	
	/*@GetMapping("/qr")
	public String getQRcode(ModelMap m) {
		String urlGo="https://www.google.es";
		byte[] image = new byte[0];
		try {
			image = QRCodeGenerator.getQRCodeImage(urlGo,250,250);
		}catch (Exception e) {
			
		}
		return "_t/frame";
	}
	*/
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
	

	@GetMapping("/perfil/cambiarPassword")
	public String cambioPass(ModelMap m,
			@RequestParam("idU")Long id,
			HttpSession s
	) throws DangerException {
		try {
			H.isLogged((Usuario)(s.getAttribute("usuario")));
		}catch(Exception e){
			PRG.error(e.getMessage(),"/login");
		}
		m.put("id", id);
		m.put("view", "/perfil/cambiarPassword");
		return "_t/frame";
	}
	
	@PostMapping("/perfil/cambiarPassword")
	public String cambioPassPost(
			ModelMap m,
			@RequestParam("id")Long id,
			@RequestParam("nuevaPass")String nuevaPass,
			@RequestParam("nuevaPassConfirmar")String nuevaPassConfirmar,
			HttpSession s
			) throws Exception {
		try {
			H.isLogged((Usuario)(s.getAttribute("usuario")));
		}catch(Exception e){
			PRG.error(e.getMessage(),"/login");
		}
		try {
			if(!nuevaPass.equals(nuevaPassConfirmar)) {
				throw new Exception("Las contraseñas no coinciden. Intentalo de nuevo");
			}
		}catch (Exception e) {
			PRG.error(e.getMessage(),"/" );
		}
		Usuario nuevoUsuario = usuarioService.updatePass(id, nuevaPass);
		s.setAttribute("usuario", nuevoUsuario);
		m.put("usuario", nuevoUsuario);
		m.put("view", "/perfil/r");
		return "_t/frame";
	}
}

