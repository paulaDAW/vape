package org.tfg.spring.Vape.parteDos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tfg.spring.Vape.entities.User;

public interface VerificationTokenRepository 
extends JpaRepository<VerificationToken, Long> {

  VerificationToken findByToken(String token);

  VerificationToken findByUser(User user);
}
