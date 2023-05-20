package org.carlos.spring.Plantilla.controllers;



import org.carlos.spring.Plantilla.entities.Usuario;
import org.carlos.spring.Plantilla.exception.DangerException;
import org.carlos.spring.Plantilla.helpers.PRG;
import org.carlos.spring.Plantilla.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("/login")
	public String login(ModelMap m) {
		m.put("view", "home/login");
		return "_t/frame";
	}
	
	@PostMapping("/login")
	public String loginPost(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			HttpSession s
			) throws DangerException {
		try {
			Usuario usuario = usuarioService.autenticarUsuario(email,password);
			if(usuario.isActivado()==false) {
				throw new Exception("Debe de confirmar su registro para poder continuar");
			}
			s.setAttribute("usuario", usuario);
			s.setAttribute("rolAdmin", "admin");
			s.setAttribute("reservada",false);
		} catch (Exception e) {
			PRG.error(e.getMessage(),"/");
		}
		return "redirect:/";
	}
	
	@GetMapping("/register")
	public String register(ModelMap m) {
		m.put("view", "home/register");
		return "_t/frame";
	}
	
	@PostMapping("/register")
	public String registerPost(
			@ModelAttribute Usuario usuario,
			@RequestParam("passwordConf") String confirmPass,
			HttpSession s,
			ModelMap m
			) throws Exception {
		//Primero comprobar las pass
		try {
			if(!confirmPass.trim().toString().equals(usuario.getPassword().trim().toString())) {
				throw new Exception("La contrasaña no coincide");
			}
		}catch(Exception e) {
			PRG.error(e.getMessage(),"/register");
		}
		
		//Si son iguales guarda el usuario
		Usuario usuarioGuardado = new Usuario();
		try {
			usuarioGuardado = usuarioService.saveUsuario(usuario);
			
		} catch (Exception e) {
			PRG.error(e.getMessage(),"/");
		}
		//Guarda el usuario en la sesion
		s.setAttribute("usuario", usuarioGuardado);
		System.out.println(usuarioGuardado.getEmail());
		s.setAttribute("reservada",false);
		//Le envía el correo de confirmación
		usuarioService.envioConfirmarRegistro(usuarioGuardado.getEmail());
		m.put("usuario", usuarioGuardado);
		m.put("view", "home/esperaConfirmar");
		return "_t/frame";
		
	}
	
	@PostMapping("/confirmar/registro")
	public String listo(
			ModelMap m,
			HttpSession s
			) throws Exception {
		Usuario usuario = (Usuario)s.getAttribute("usuario");
		System.out.println(usuario.getEmail());
		//System.out.println(usuario.isActivado());
		Usuario usuarioActivo = usuarioService.activarUsuario(usuario.getEmail());
		s.setAttribute("usuario", usuarioActivo);
		m.put("usuario", usuario);
		m.put("view", "home/esperaConfirmar");//Misma vista, pero comprobar dato activo
		return "_t/frame";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession s) {
		if(s.getAttribute("usuario")!=null) {
			s.removeAttribute("usuario");
		}
		return "redirect:/";
	}
}
