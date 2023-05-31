package org.tfg.spring.Vape.dto;



import org.tfg.spring.Vape.repositories.PasswordMatches;
import org.tfg.spring.Vape.repositories.ValidEmail;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@PasswordMatches
public class UserDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
    private String nombre;
    
	@NonNull
    private String apellido1;
	
	@NonNull
    private String apellido2;
	
	@NonNull
	private String tarjeta;
	
	@NonNull
    @ValidEmail
    private String email;
    
	@NonNull
	//@ValidPassword
    private String password;
    private String matchingPassword;
    
    

}

