package org.carlos.spring.Plantilla.entities;

import java.time.LocalDate;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class EntradaComprada {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	@NonNull
	private LocalDate fechaCompra;
	
	@ManyToOne
	private Usuario usuario;
	
	@ManyToOne
	private Entrada entrada;
	
	@ManyToOne
	private Tipo tipo;
	
	private int cantidad;
	
	private LocalDate fecha;
	
	//ManyToMany

}

