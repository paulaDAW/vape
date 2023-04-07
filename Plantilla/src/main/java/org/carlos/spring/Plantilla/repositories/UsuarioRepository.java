package org.carlos.spring.Plantilla.repositories;

import org.carlos.spring.Plantilla.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
