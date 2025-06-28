package com.financeiro.backend.application.services;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.financeiro.backend.domain.entitys.Usuario;
import com.financeiro.backend.domain.repositories.UsuarioRespository;
import com.financeiro.backend.infrastructure.config.JwtUtil;

@Service
public class AuthService {

  private final UsuarioRespository usuarioRespository;
  private final JwtUtil jwtUtil;
  private final PasswordEncoder passwordEncoder;

  public AuthService(UsuarioRespository usuarioRespository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
    this.usuarioRespository = usuarioRespository;
    this.jwtUtil = jwtUtil;
    this.passwordEncoder = passwordEncoder;
  }

  public String autenticar(String email, String senha){
    Usuario usuario = usuarioRespository.findByEmail(email)
      .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    if(!passwordEncoder.matches(senha, usuario.getSenha()))
      throw new RuntimeException("Senha inválida");
    
    return jwtUtil.gerarToken(usuario);
  }

  public void registrar(String nome, String email, String senha){
    if(usuarioRespository.findByEmail(email).isPresent())
      throw new RuntimeException("Email já cadastrado");

    Usuario novoUsuario = new Usuario();
    novoUsuario.setNome(nome);
    novoUsuario.setEmail(email);
    novoUsuario.setSenha(passwordEncoder.encode(senha));
    
    usuarioRespository.save(novoUsuario);
  }

  public Usuario getUsuarioLogado(){
    return usuarioRespository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
      .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
  }
}
