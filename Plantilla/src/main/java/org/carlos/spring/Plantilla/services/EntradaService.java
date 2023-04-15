package org.carlos.spring.Plantilla.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.carlos.spring.Plantilla.entities.Entrada;
import org.carlos.spring.Plantilla.entities.Horario;
import org.carlos.spring.Plantilla.entities.Tipo;
import org.carlos.spring.Plantilla.repositories.EntradaRepository;
import org.carlos.spring.Plantilla.repositories.HorarioRepository;
import org.carlos.spring.Plantilla.repositories.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntradaService {

	@Autowired
	private EntradaRepository entradaRepository;
	
	@Autowired
	private TipoRepository tipoRepository;
	
	@Autowired
	private HorarioRepository horarioRepository;
	
	
	public List<Entrada> getEntradas() {
		return entradaRepository.findAll();
	}

	public void saveEntrada(int numero, Long idHorario,Long idTipos) throws Exception {
		Entrada entrada = Entrada.builder().numero(numero).build();
		
		Horario horario=horarioRepository.getById(idHorario);
		
		entrada.setHorario(horario);
		
		horario.getEntradas().add(entrada);
		
		Tipo tipo=tipoRepository.getById(idTipos);
		
		entrada.setTipo(tipo);
		
		try {
			entradaRepository.saveAndFlush(entrada);
		} catch (Exception e) {
			throw new Exception("El/la entrada con nº de referencia " + numero + " ya existe");
		}
	}

	public Entrada getEntradaById(Long id) {
		return entradaRepository.findById(id).get();
	}

	public void updateEntrada(Long id, int numero,Long idHorario,Long idTipos) throws Exception {
		Entrada entrada = entradaRepository.findById(id).get();
		entrada.setNumero(numero);
		
		if ( entrada.getHorario()== null || idHorario != entrada.getHorario().getId() )  {
			Horario nuevoHorario= horarioRepository.getById(idHorario);
			entrada.setHorario(nuevoHorario);
		}
		
		if ( entrada.getTipo()== null || idTipos != entrada.getTipo().getId() )  {
			Tipo nuevoTipo= tipoRepository.getById(idTipos);
			entrada.setTipo(nuevoTipo);
		}
		
		
		try {
			entradaRepository.saveAndFlush(entrada);
		} catch (Exception e) {
			throw new Exception("El/la entrada con nº de referencia " + numero + " ya existe");
		}
	}

	public void deleteEntrada(Long id) {
		Entrada entrada = entradaRepository.findById(id).get();
		entradaRepository.delete(entrada);
	}
}
