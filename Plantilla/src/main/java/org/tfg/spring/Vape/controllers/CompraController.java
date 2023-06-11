package org.tfg.spring.Vape.controllers;


import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tfg.spring.Vape.entities.Entrada;
import org.tfg.spring.Vape.entities.EntradaComprada;
import org.tfg.spring.Vape.entities.Tarjeta;
import org.tfg.spring.Vape.entities.User;
import org.tfg.spring.Vape.exception.DangerException;
import org.tfg.spring.Vape.helpers.H;
import org.tfg.spring.Vape.helpers.PRG;
import org.tfg.spring.Vape.helpers.QR;
import org.tfg.spring.Vape.services.EntradaCompradaService;
import org.tfg.spring.Vape.services.EntradaService;
import org.tfg.spring.Vape.services.TarjetaService;
import org.tfg.spring.Vape.services.TipoService;
import org.tfg.spring.Vape.services.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/comprar")
public class CompraController {
	
	@Autowired
	private UserService usuarioService;
	
	@Autowired
	private EntradaService entradaService;
	
	@Autowired
	private TipoService tipoService;
	
	@Autowired
	private TarjetaService tarjetaService;
	
	@Autowired
	private EntradaCompradaService entradaCompradaService;
	
	//private final String imagePath ="./src/main/resources/static/qr/QRCode";
	@Value("${app.uploadFolder}")
	private String UPLOAD_FOLDER;

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
			@RequestParam("idTarjeta") Long idTarjeta,
			@RequestParam("numCvv") String numCvv,
			HttpSession s,
			ModelMap m
			) throws DangerException {
		int cantidad = (int)s.getAttribute("cantidad");
		String retorno="redirect:/comprar/confirmacion";
		Entrada entrada = entradaService.getEntradaById(idEntrada);
		LocalDate fechaCompra = LocalDate.now();
		
		//m.put("entrada", entradaReservada);
		try {
			H.isLogged((User)(s.getAttribute("usuario")));
		}catch(Exception e){
			PRG.error(e.getMessage(),"/login");
		}
		try {
			//s.setAttribute("reservada", false);//campo a cero
			User usuario = (User)(s.getAttribute("usuario"));
			
			//EntradaComprada entradaComprada = entradaCompradaService.getEntradaCompradaById(idEntrada);
			//Entrada entrada = entradaComprada.getEntrada();
			Tarjeta tarjetaCompra = tarjetaService.getTarjetaById(idTarjeta);
			if(!tarjetaCompra.getCvv().equals(numCvv.trim())) {
				m.put("entrada", entrada);
				m.put("tipo", (Long)s.getAttribute("tipo"));
				PRG.info("El número de compra no es el corecto.", "/comprar/r");
				//?cantidad="+cantidad+"&idTipo="
				//+(Long)s.getAttribute("tipo")+"&fecha="+(LocalDate)s.getAttribute("fecha")
			}
			EntradaComprada entradaComprada = entradaCompradaService.saveEntradaComprada((User)(s.getAttribute("usuario")) ,(Long)s.getAttribute("tipo"), entrada.getId(), cantidad, fechaCompra);
			//tarjeta.cvv == usuario.tarjeta.cvv
			//Revisar
			/*
			 * //Comprobación Tarjeta de crédito
			if(!numTarjeta.trim().toString().equals(usuario.getTarjeta().trim().toString())) {
				entradaCompradaService.deleteEntradaComprada(idEntrada);
				entradaService.cancelarEntrada(entrada, entradaComprada.getCantidad());
				throw new Exception("La tarjeta introducida no es correcta. Por favor, inténtelo de nuevo");
			}
			*/
			/*
			 Convertirlo en numeros, dividir entre cierto numero, y el resto que sea igual al ccv
			 */
			
			/*
			if(!usuario.getId().equals(entradaComprada.getUsuario().getId())) {
				entradaCompradaService.deleteEntradaComprada(idEntrada);
				System.out.println("Usuario "+usuario.getId());
				System.out.println("Usuario de la entrada "+ entradaComprada.getUsuario().getId());
				throw new Exception("Ha habido un error al registrar su compra.\n Reinténtalo");
			}
			*/
			//Generar qr y guardarlo en la entrada
			String cadenaqr = entradaComprada.getCantidad()+" Entradas para el dia "
			+ entradaComprada.getEntrada().getFecha() + "\n"
			+usuario.getName() +" "+ usuario.getPrimerApellido()
			+(usuario.getSegundoApellido() != null ? usuario.getSegundoApellido():"")+"\n"
					+ "Fecha de compra" + entradaComprada.getFechaCompra();
			String rutaCompleta = (UPLOAD_FOLDER+ "QRCode" + entradaComprada.getId() + ".png");
			QR.generateImageQRCode(cadenaqr, 250, 250, rutaCompleta );
			//entradaCompradaService.updateRuta(rutaCompleta.replace("./src/main/resources/static","") , entradaComprada);
			entradaCompradaService.updateRuta(rutaCompleta.replace("C:\\Users\\David\\Desktop\\TFG\\Plantilla\\src\\main\\resources\\static","") , entradaComprada);
			
			
			usuarioService.confirmacionCompra(usuario.getMail());

		}catch(Exception e){
			PRG.error("Este es tu error de compra:"+e.getLocalizedMessage(),"/");
		}
		return retorno;
	}
	
	@PostMapping("cancelar")
	public String cancelar(
			//@RequestParam("idEntrada") Long idEntrada,
			HttpSession s
			) throws DangerException {
		
		String retorno="redirect:/comprar/r";

		try {
			H.isLogged((User)(s.getAttribute("usuario")));
		}catch(Exception e){
			PRG.error(e.getMessage(),"/login");
		}
		/*try {
			//s.setAttribute("reservada", false);//campo a cero
			User usuario = (User)(s.getAttribute("usuario"));
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
		*/
		
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
		
		Entrada entrada = entradaService.getEntradaByFecha(fecha);
		try {
			H.isLogged((User)(s.getAttribute("usuario")));
		}catch(Exception e){
			PRG.error(e.getMessage(),"/login");
		}
		/*
		Antigua comprobación reserva de entrada
		try {
			//System.out.println("ID entrada"+entrada.getId());
			System.out.println((boolean)s.getAttribute("reservada"));
			if((boolean)s.getAttribute("reservada")== false && entrada == null && aforo >= cantidad ){//campo entradaMax nulo ó cero->poner a uno
				entrada = entradaService.saveEntrada(aforo, 0, fecha);
				s.setAttribute("reservada", true);
			}else if((boolean)s.getAttribute("reservada")==true){
				//TO-DO System.out.println("Hola que tal");
				//Arreglar el borrado, mirar bien cuando se cambia la sesión
				User usuarioActual = (User)s.getAttribute("usuario");
				List<EntradaComprada> listaEntradas =  (List<EntradaComprada>) usuarioActual.getEntradasCompradas();
				EntradaComprada ultima = listaEntradas.get(listaEntradas.size()-1);
				System.out.println(ultima.getId());
				entradaService.cancelarEntrada(entrada, ultima.getCantidad());
				entradaCompradaService.deleteEntradaComprada(ultima.getId());
				
				s.setAttribute("reservada", false);
				throw new Exception("Error al comprar. Se ha cancelado su reserva. Si desea comprar entradas, vuelva a seleccionarlas");
				//Cancelar la última entrada comprada creada y dismunir ventas en entrada
				
				
			}//si es uno o mas, lanzar error(Confirma o cancela tu compra actual)
			
			
			
			//Guarda la entrada, pero si en la confirmacion cancela, borrar la entrada y ajustar vendidas.
			s.setAttribute("reservada", true);
			
			m.put("precioTotal",(cantidad*(entradaReservada.getTipo().getPrecio())));
			
		}catch(Exception e){
			PRG.error(e.getMessage(),"/");
		}
		*/
		//Guardamos en la sesión los valores por si hay que volver atrás
		s.setAttribute("fecha", fecha);
		s.setAttribute("cantidad", cantidad);
		s.setAttribute("tipo", idTipo);
		m.put("tipo", tipoService.getTipoById(idTipo));
		
		try {
			if(entrada == null && aforo >= cantidad ){//campo entradaMax nulo ó cero->poner a uno
				entrada = entradaService.saveEntrada(aforo, 0, fecha);
			}
			
			if((entrada.getNumeroVendido()+cantidad) > entrada.getNumeroMaximo()) {
				PRG.info("Supera el número de entradas disponibles", "/comprar/r" );
				//Comprobar en el front y redirigira las entradas mejor
			}
			
		}catch (Exception e) {
			PRG.error("Ha ocurrido un erorr al seleccinar la entrada" + e.getMessage(), "/comprar/r");
		}
		m.put("entrada", entrada);
		m.put("view", "/comprar/resumen");
		return "_t/frame";
	}
	
	

	
	@GetMapping("confirmacion")
	public String email(ModelMap m, HttpSession s) throws Exception {
		try {
			H.isLogged((User)(s.getAttribute("usuario")));
		}catch(Exception e){
			PRG.error(e.getMessage(),"/login");
		}
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

