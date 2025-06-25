package com.financeiro.backend.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.financeiro.backend.domain.repositories.UsuarioRespository;
import com.financeiro.backend.domain.entitys.Usuario;

@Service
public class CustomUserDetailsService implements UserDetailsService{

  @Autowired
  private UsuarioRespository usuarioRespository;
  
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Usuario usuario = usuarioRespository.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
    
    return org.springframework.security.core.userdetails.User
      .withUsername(usuario.getEmail())
      .password(usuario.getSenha())
      .authorities("USER")
      .build();
  }
}
