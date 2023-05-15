package org.carlos.spring.Plantilla.entities;


import java.time.LocalDate;
import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Entrada {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int numeroMaximo;
	
	private int numeroVendido;
	
	private LocalDate fecha;
	
	@OneToMany(mappedBy = "entrada")
	private Collection<EntradaComprada> entradasCompradas;
	

}
