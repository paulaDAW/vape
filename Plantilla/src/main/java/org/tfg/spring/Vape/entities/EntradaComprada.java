package org.tfg.spring.Vape.entities;

import java.time.LocalDate;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

	@NonNull
	private LocalDate fechaCompra;
	
	@ManyToOne
	private User usuario;
	
	@ManyToOne
	private Entrada entrada;
	
	@ManyToOne
	private Tipo tipo;
	
	private int cantidad;
	
	private String rutaImagenQR;
	
	//ManyToMany

}

