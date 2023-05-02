package org.carlos.spring.Plantilla.services;

import java.util.List;
import org.carlos.spring.Plantilla.entities.Tipo;
import org.carlos.spring.Plantilla.repositories.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoService {

	@Autowired
	private TipoRepository tipoRepository;

	public List<Tipo> getTipos() {
		return tipoRepository.findAll();
	}

	public void saveTipo(String nombre, double precio) throws Exception {
		Tipo tipo = Tipo.builder().nombre(nombre).precio(precio).build();
		try {
			tipoRepository.saveAndFlush(tipo);
		} catch (Exception e) {
			throw new Exception("El tipo de entrada ( " + nombre + " ) ya existe");
		}
	}

	public Tipo getTipoById(Long id) {
		return tipoRepository.findById(id).get();
	}

	public void updateTipo(Long id, String nombre, double precio) throws Exception {
		Tipo tipo = tipoRepository.findById(id).get();
		tipo.setNombre(nombre);
		tipo.setPrecio(precio);
		try {
			tipoRepository.saveAndFlush(tipo);
		} catch (Exception e) {
			throw new Exception("Error al intentar actualizar.");
		}
	}

	public void deleteTipo(Long id) {
		Tipo tipo = tipoRepository.findById(id).get();
		tipoRepository.delete(tipo);
	}
}

