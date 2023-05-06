package org.carlos.spring.Plantilla.services;

import java.time.LocalDate;
import java.util.List;
import org.carlos.spring.Plantilla.entities.Entrada;
import org.carlos.spring.Plantilla.repositories.EntradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntradaService {

	@Autowired
	private EntradaRepository entradaRepository;
	
	
	public List<Entrada> getEntradas() {
		return entradaRepository.findAll();
	}

	public void saveEntrada(int numeroMax, int numeroVen, LocalDate fecha) throws Exception {
		Entrada entrada = Entrada.builder()
				.numeroMaximo(numeroMax)
				.numeroVendido(numeroVen)
				.fecha(fecha)
				.build();
		
		try {
			entradaRepository.saveAndFlush(entrada);
		} catch (Exception e) {
			throw new Exception("La entrada ya existe");
		}
	}

	public Entrada getEntradaById(Long id) {
		return entradaRepository.findById(id).get();
	}

	public void updateEntrada(Long id, int numeroMax, int numeroVen, LocalDate fecha) 
					throws Exception {
		Entrada entrada = entradaRepository.findById(id).get();
		entrada.setNumeroMaximo(numeroMax);
		entrada.setNumeroVendido(numeroVen);
		entrada.setFecha(fecha);
		
		try {
			entradaRepository.saveAndFlush(entrada);
		} catch (Exception e) {
			throw new Exception("La entrada ya existe");
		}
	}

	public void deleteEntrada(Long id) {
		Entrada entrada = entradaRepository.findById(id).get();
		entradaRepository.delete(entrada);
	}

	public Entrada getEntradaByFecha(LocalDate fecha) {
		return entradaRepository.findByFecha(fecha);
	}
}
