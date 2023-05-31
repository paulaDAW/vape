package org.tfg.spring.Vape.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tfg.spring.Vape.entities.Entrada;
import org.tfg.spring.Vape.repositories.EntradaRepository;

@Service
public class EntradaService {

	@Autowired
	private EntradaRepository entradaRepository;
	
	
	public List<Entrada> getEntradas() {
		return entradaRepository.findAll();
	}

	public Entrada saveEntrada(int numeroMax, int numeroVen, LocalDate fecha) throws Exception {
		Entrada entrada = Entrada.builder()
				.numeroMaximo(numeroMax)
				.numeroVendido(numeroVen)
				.fecha(fecha)
				.build();
		Entrada entradaDev = new Entrada();
		try {
			entradaDev = entradaRepository.saveAndFlush(entrada);
		} catch (Exception e) {
			throw new Exception("La entrada ya existe"+e.getLocalizedMessage());
		}
		return entradaDev;
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

	public void cancelarEntrada(Entrada entrada, int cantidad) throws Exception {
		// TODO Auto-generated method stub
		entrada.setNumeroVendido(entrada.getNumeroVendido()-cantidad);
		
		try {
			entradaRepository.saveAndFlush(entrada);
		} catch (Exception e) {
			throw new Exception("Error al actualizar stock de entradas");
		}
	}
}
