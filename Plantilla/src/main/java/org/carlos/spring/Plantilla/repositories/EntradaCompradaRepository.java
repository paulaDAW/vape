package org.carlos.spring.Plantilla.repositories;

import java.util.List;

import org.carlos.spring.Plantilla.entities.EntradaComprada;
import org.carlos.spring.Plantilla.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntradaCompradaRepository extends JpaRepository<EntradaComprada, Long> {

	List<EntradaComprada> findByUsuario(Usuario usuario);
	

}
