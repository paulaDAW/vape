package org.paula.spring.proyecto.repositories;

import org.paula.spring.proyecto.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
