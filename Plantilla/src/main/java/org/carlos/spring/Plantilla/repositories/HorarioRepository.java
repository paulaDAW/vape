package org.carlos.spring.Plantilla.repositories;

import org.carlos.spring.Plantilla.entities.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioRepository extends JpaRepository<Horario, Long>{
	
}
