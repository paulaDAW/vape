package org.tfg.spring.Vape.entities;


import java.util.Collection;

import javax.validation.Valid;

import org.tfg.spring.Vape.dto.UserDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data

@AllArgsConstructor
@Builder

@Entity
@Table(name = "usuario")
public class User {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String primerApellido;

    private String segundoApellido;
    
    private String mail;
    
    private String tarjeta;

    @Column(length = 60)
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToOne
	private Rol rol;
	
	@OneToMany(mappedBy = "usuario",fetch=FetchType.EAGER)
	private Collection<EntradaComprada> entradasCompradas;
    /*
    private boolean isUsing2FA;

    private String secret;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Rol> roles;

	public void setRoles(List<String> asList) {
		// TODO Auto-generated method stub
		
	}
	*/
	 public User() {
	        super();
	        this.enabled=false;
	    }
	public User(@Valid UserDto userDto) {
		this.name = userDto.getNombre();
		this.primerApellido = userDto.getApellido1();
		this.segundoApellido = userDto.getApellido2();
		this.password = userDto.getPassword();
		this.mail = userDto.getEmail();
		this.tarjeta = userDto.getTarjeta();
		this.enabled = false;
		this.rol = null; 
	}

    

}
