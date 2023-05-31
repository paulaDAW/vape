package org.tfg.spring.Vape.parteDos;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.tfg.spring.Vape.entities.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class VerificationToken {
    private static final int EXPIRATION = 15;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String token;
  
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    
    private Date expiryDate;
   
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

	public VerificationToken(String token2, User user2) {
		this.token = token2;
		this.user = user2;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}
    
    // standard constructors, getters and setters
}
