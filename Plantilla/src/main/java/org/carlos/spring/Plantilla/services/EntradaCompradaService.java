package org.carlos.spring.Plantilla.services;

import java.time.LocalDate;
import java.util.List;

import org.carlos.spring.Plantilla.entities.Entrada;
import org.carlos.spring.Plantilla.entities.EntradaComprada;
import org.carlos.spring.Plantilla.entities.Tipo;
import org.carlos.spring.Plantilla.entities.Usuario;
import org.carlos.spring.Plantilla.repositories.EntradaCompradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntradaCompradaService {

	@Autowired
	private EntradaCompradaRepository EntradaCompradaRepository;
	
	@Autowired
	private EntradaService entradaService;
	
	@Autowired
	private TipoService tipoService;
	
	@Autowired
	private UsuarioService usuarioService;

	public List<EntradaComprada> getEntradaCompradas() {
		return EntradaCompradaRepository.findAll();
	}

	public void saveEntradaComprada(Usuario usuario, Long idTipo,Long idEntrada,int cantidad,LocalDate fecha, LocalDate fechaCompra) throws Exception {
		EntradaComprada entradaComprada = EntradaComprada.builder().cantidad(cantidad)
				.fecha(fecha).fechaCompra(fechaCompra).build();
		Tipo tipo = tipoService.getTipoById(idTipo);
		Entrada entrada = entradaService.getEntradaById(idEntrada);
		entradaComprada.setEntrada(entrada);
		entradaComprada.setTipo(tipo);
		entradaComprada.setUsuario(usuario);
		try {
			EntradaCompradaRepository.saveAndFlush(entradaComprada);
		} catch (Exception e) {
			throw new Exception("Error al comprar la entrada");
		}
	}

	public EntradaComprada getEntradaCompradaById(Long id) {
		return EntradaCompradaRepository.findById(id).get();
	}

	public void updateEntradaComprada(Long id, String nombre) throws Exception {
		EntradaComprada entradaComprada = EntradaCompradaRepository.findById(id).get();
		
		try {
			EntradaCompradaRepository.saveAndFlush(entradaComprada);
		} catch (Exception e) {
			throw new Exception("El/la EntradaComprada " + nombre + " ya existe");
		}
	}

	public void deleteEntradaComprada(Long id) {
		EntradaComprada EntradaComprada = EntradaCompradaRepository.findById(id).get();
		EntradaCompradaRepository.delete(EntradaComprada);
	}
}

