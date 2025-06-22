package com.financeiro.backend.domain.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.financeiro.backend.domain.entitys.Usuario;

public interface UsuarioRespository extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByEmail(String email);  
}
