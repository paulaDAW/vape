package org.tfg.spring.Vape.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tfg.spring.Vape.entities.Entrada;
import org.tfg.spring.Vape.entities.EntradaComprada;
import org.tfg.spring.Vape.entities.Tipo;
import org.tfg.spring.Vape.entities.User;
import org.tfg.spring.Vape.repositories.EntradaCompradaRepository;

@Service
public class EntradaCompradaService {

	@Autowired
	private EntradaCompradaRepository entradaCompradaRepository;
	
	@Autowired
	private EntradaService entradaService;
	
	@Autowired
	private TipoService tipoService;
	
	/*@Autowired
	private UsuarioService usuarioService;*/

	public List<EntradaComprada> getEntradaCompradas() {
		return entradaCompradaRepository.findAll();
	}

	public EntradaComprada saveEntradaComprada(User usuario, Long idTipo,Long idEntrada,int cantidad, LocalDate fechaCompra) throws Exception {
		EntradaComprada entradaComprada = EntradaComprada.builder().cantidad(cantidad)
				.fechaCompra(fechaCompra).build();
		Tipo tipo = tipoService.getTipoById(idTipo);
		Entrada entrada = entradaService.getEntradaById(idEntrada);
		entradaComprada.setEntrada(entrada);
		entradaComprada.setTipo(tipo);
		entradaComprada.setUsuario(usuario);
		
		entrada.setNumeroVendido((entrada.getNumeroVendido()+cantidad));
		EntradaComprada entradaDevolver = null;
		try {
			entradaDevolver = entradaCompradaRepository.saveAndFlush(entradaComprada);
		} catch (Exception e) {
			throw new Exception("Error al comprar la entrada");
		}
		return entradaDevolver;
	}

	public EntradaComprada getEntradaCompradaById(Long id) {
		return entradaCompradaRepository.findById(id).get();
	}

	public void updateEntradaComprada(Long id, String nombre) throws Exception {
		EntradaComprada entradaComprada = entradaCompradaRepository.findById(id).get();
		
		try {
			entradaCompradaRepository.saveAndFlush(entradaComprada);
		} catch (Exception e) {
			throw new Exception("El/la EntradaComprada " + nombre + " ya existe");
		}
	}

	public void deleteEntradaComprada(Long id) {
		EntradaComprada entradaComprada = entradaCompradaRepository.findById(id).get();
		entradaCompradaRepository.delete(entradaComprada);
	}

	public List<EntradaComprada> getMisEntradas(User usuario) {

		return entradaCompradaRepository.findByUsuario(usuario);
	}

	public void updateRuta(String rutaCompleta, EntradaComprada entradaComprada) throws Exception {
		entradaComprada.setRutaImagenQR(rutaCompleta);
		try {
			entradaCompradaRepository.saveAndFlush(entradaComprada);
		}catch(Exception e) {
			throw new Exception("Error durante la compra");
		}
	}
}

