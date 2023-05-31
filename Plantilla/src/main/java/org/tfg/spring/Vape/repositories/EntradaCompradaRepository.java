package org.tfg.spring.Vape.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tfg.spring.Vape.entities.EntradaComprada;
import org.tfg.spring.Vape.entities.User;


public interface EntradaCompradaRepository extends JpaRepository<EntradaComprada, Long> {

	List<EntradaComprada> findByUsuario(User usuario);
	

}
