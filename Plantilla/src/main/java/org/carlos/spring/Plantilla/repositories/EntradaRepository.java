package org.carlos.spring.Plantilla.repositories;

import org.carlos.spring.Plantilla.entities.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntradaRepository extends JpaRepository<Entrada, Long>{
	
}