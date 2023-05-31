package org.tfg.spring.Vape.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tfg.spring.Vape.entities.Entrada;

public interface EntradaRepository extends JpaRepository<Entrada, Long>{
	public Entrada findByFecha(LocalDate fecha);
}
