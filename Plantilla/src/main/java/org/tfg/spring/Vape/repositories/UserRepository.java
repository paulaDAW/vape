package org.tfg.spring.Vape.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tfg.spring.Vape.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    void delete(User user);

	User findByMail(String email);

}
