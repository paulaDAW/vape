package org.tfg.spring.Vape.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tfg.spring.Vape.entities.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {

	public Rol findByNombre(String nombre);


}
