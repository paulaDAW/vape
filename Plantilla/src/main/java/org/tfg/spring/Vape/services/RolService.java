package org.tfg.spring.Vape.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tfg.spring.Vape.entities.Rol;
import org.tfg.spring.Vape.repositories.RolRepository;

@Service
public class RolService {

	@Autowired
	private RolRepository rolRepository;


	public List<Rol> getRoles() {

		return rolRepository.findAll();
	}

	public Rol saveRol(String nombre) throws Exception {
		Rol rol = Rol.builder().nombre(nombre).build();
		try {
			rolRepository.saveAndFlush(rol);
		} catch (Exception e) {
			throw new Exception("El/la rol " + nombre + " ya existe");
		}
		return rol;
	}

	public Rol getRolById(Long id) {
		return rolRepository.findById(id).get();
	}

	public void updateRol(Long id, String nombre) throws Exception {
		Rol rol = rolRepository.findById(id).get();
		rol.setNombre(nombre);
		try {
			rolRepository.saveAndFlush(rol);
		} catch (Exception e) {
			throw new Exception("El/la rol " + nombre + " ya existe");
		}
	}

	public void deleteRol(Long id) {
		Rol rol = rolRepository.findById(id).get();
		rolRepository.delete(rol);
	}
}
