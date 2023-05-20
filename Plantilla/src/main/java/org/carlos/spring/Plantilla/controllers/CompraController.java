package org.carlos.spring.Plantilla.controllers;


import java.time.LocalDate;
import java.util.List;

import org.carlos.spring.Plantilla.entities.Entrada;
import org.carlos.spring.Plantilla.entities.EntradaComprada;
import org.carlos.spring.Plantilla.entities.Usuario;
import org.carlos.spring.Plantilla.exception.DangerException;
import org.carlos.spring.Plantilla.helpers.PRG;
import org.carlos.spring.Plantilla.helpers.QR;
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
	
	private final String imagePath ="./src/main/resources/static/qr/QRCode";

	@GetMapping("r")
	public String r(ModelMap m) {
		m.put("entradas", entradaService.getEntradas());
		m.put("tipos", tipoService.getTipos());
		m.put("view", "comprar/r");
		return "_t/frame";
	}
	
	@PostMapping("confirmar")
	public String confirmar(
			@RequestParam("idEntrada") Long idEntrada,
			@RequestParam("numTarjeta") String numTarjeta,
			@RequestParam("numCcv") int numCcv,
			HttpSession s
			) throws DangerException {
		String retorno="redirect:/comprar/confirmacion";

		try {
			H.isLogged((Usuario)(s.getAttribute("usuario")));
		}catch(Exception e){
			PRG.error(e.getMessage(),"/login");
		}
		try {
			s.setAttribute("reservada", false);//campo a cero
			Usuario usuario = (Usuario)(s.getAttribute("usuario"));
			EntradaComprada entradaComprada = entradaCompradaService.getEntradaCompradaById(idEntrada);
			Entrada entrada = entradaComprada.getEntrada();
			
			//Comprobación Tarjeta de crédito
			if(!numTarjeta.trim().toString().equals(usuario.getTarjeta().trim().toString())) {
				entradaCompradaService.deleteEntradaComprada(idEntrada);
				entradaService.cancelarEntrada(entrada, entradaComprada.getCantidad());
				throw new Exception("La tarjeta introducida no es correcta. Por favor, inténtelo de nuevo");
			}
			/*
			 Convertirlo en numeros, dividir entre cierto numero, y el resto que sea igual al ccv
			 */
			
			
			if(usuario.getId() != entradaComprada.getUsuario().getId()) {
				entradaCompradaService.deleteEntradaComprada(idEntrada);
				throw new Exception("Ha habido un error al registrar su compra.\n Reinténtalo");
			}
			//Generar qr y guardarlo en la entrada
			String cadenaqr = entradaComprada.getCantidad()+" Entradas para el dia "
			+ entradaComprada.getEntrada().getFecha() + "\n"
			+usuario.getNombre() +" "+ usuario.getApellido1()
			+(usuario.getApellido2() != null ? usuario.getApellido2():"")+"\n"
					+ "Fecha de compra" + entradaComprada.getFechaCompra();
			String rutaCompleta = (imagePath + entradaComprada.getId() + ".png");
			QR.generateImageQRCode(cadenaqr, 250, 250, rutaCompleta );
			entradaCompradaService.updateRuta(rutaCompleta.replace("./src/main/resources/static","") , entradaComprada);
			
			
			usuarioService.confirmacionCompra(usuario.getEmail());

		}catch(Exception e){
			PRG.error(e.getMessage(),"/");
		}
		return retorno;
	}
	
	@PostMapping("cancelar")
	public String cancelar(
			@RequestParam("idEntrada") Long idEntrada,
			HttpSession s
			) throws DangerException {
		
		String retorno="redirect:/comprar/confirmacion";

		try {
			H.isLogged((Usuario)(s.getAttribute("usuario")));
		}catch(Exception e){
			PRG.error(e.getMessage(),"/login");
		}
		try {
			s.setAttribute("reservada", false);//campo a cero
			Usuario usuario = (Usuario)(s.getAttribute("usuario"));
			EntradaComprada entradaComprada = entradaCompradaService.getEntradaCompradaById(idEntrada);
			Entrada entrada = entradaComprada.getEntrada();
			if(usuario.getId() == entradaComprada.getUsuario().getId()) {
				entradaCompradaService.deleteEntradaComprada(idEntrada);
				entradaService.cancelarEntrada(entrada, entradaComprada.getCantidad());
				//metodo a entrada, con id entrada, cantidad de entradacomprada, para dismunir el numVendido
			}

		}catch(Exception e){
			PRG.error(e.getMessage(),"/");
		}
		
		return retorno;
	}
	
	@GetMapping("resumen")
	public String resumen(
			@RequestParam("idTipo") Long idTipo,
			@RequestParam("cantidad") int cantidad,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@RequestParam("fecha") LocalDate fecha,
			HttpSession s,
			ModelMap m
			) throws Exception {
		int aforo = 200;
		//String retorno="redirect:/comprar/resumen";
		LocalDate fechaCompra = LocalDate.now();
		Entrada entrada = entradaService.getEntradaByFecha(fecha);
		try {
			H.isLogged((Usuario)(s.getAttribute("usuario")));
		}catch(Exception e){
			PRG.error(e.getMessage(),"/login");
		}
		try {
			//System.out.println("ID entrada"+entrada.getId());
			System.out.println((boolean)s.getAttribute("reservada"));
			if((boolean)s.getAttribute("reservada")== false && entrada == null && aforo >= cantidad ){//campo entradaMax nulo ó cero->poner a uno
				entrada = entradaService.saveEntrada(aforo, 0, fecha);
				s.setAttribute("reservada", true);
			}else if((boolean)s.getAttribute("reservada")==true){
				System.out.println("Hola que tal");
				Usuario usuarioActual = (Usuario)s.getAttribute("usuario");
				List<EntradaComprada> listaEntradas =  (List<EntradaComprada>) usuarioActual.getEntradasCompradas();
				EntradaComprada ultima = listaEntradas.get(listaEntradas.size()-1);
				System.out.println(ultima.getId());
				entradaService.cancelarEntrada(entrada, ultima.getCantidad());
				entradaCompradaService.deleteEntradaComprada(ultima.getId());
				
				s.setAttribute("reservada", false);
				throw new Exception("Error al comprar. Se ha cancelado su reserva. Si desea comprar entradas, vuelva a seleccionarlas");
				//Cancelar la última entrada comprada creada y dismunir ventas en entrada
				
				
			}//si es uno o mas, lanzar error(Confirma o cancela tu compra actual)
			if((entrada.getNumeroVendido()+cantidad) > entrada.getNumeroMaximo()) {
				throw new Exception("Supera el límite de entradas disponibles");
			}
			EntradaComprada entradaReservada = entradaCompradaService.saveEntradaComprada((Usuario)(s.getAttribute("usuario")) ,idTipo, entrada.getId(), cantidad, fechaCompra);
			//Guarda la entrada, pero si en la confirmacion cancela, borrar la entrada y ajustar vendidas.
			s.setAttribute("reservada", true);
			m.put("entrada", entradaReservada);
			m.put("precioTotal",(cantidad*(entradaReservada.getTipo().getPrecio())));
			m.put("view", "/comprar/resumen");
		}catch(Exception e){
			PRG.error(e.getMessage(),"/");
		}
		return "_t/frame";
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

