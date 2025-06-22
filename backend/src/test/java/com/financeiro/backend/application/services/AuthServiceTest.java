package com.financeiro.backend.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.financeiro.backend.domain.entitys.Usuario;
import com.financeiro.backend.domain.repositories.UsuarioRespository;
import com.financeiro.backend.infrastructure.config.JwtUtil;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
  
  @Mock
  private UsuarioRespository usuarioRespository;

  @Mock
  private JwtUtil jwtUtil;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private AuthService authService;
  
  @Test
  void deveAutenticarUsuarioComCredenciaisValidasERetornarToken(){
    //Arrange
    String email = "usuario@email.com";
    String senha = "123456";
    String senhaCriptografada = "$2a$10$abcdefg";
    String tokenEsperado = "token.jwt.mockado"; 

    Usuario usuario = new Usuario();
    usuario.setEmail(email);
    usuario.setSenha(senhaCriptografada);

    when(usuarioRespository.findByEmail(email)).thenReturn(Optional.of(usuario));
    when(passwordEncoder.matches(senha, senhaCriptografada)).thenReturn(true);
    when(jwtUtil.gerarToken(usuario)).thenReturn(tokenEsperado);

    //Act
    String tokenGerado = authService.autenticar(email, senha);

    //Assert

    assertEquals(tokenEsperado, tokenGerado);
    verify(usuarioRespository, times(1)).findByEmail(email);
    verify(passwordEncoder, times(1)).matches(senha, senhaCriptografada);
    verify(jwtUtil, times(1)).gerarToken(usuario);
  }

  @Test
  void deveLancarExcecaoSeUsuarioNaopForEncontrado(){
    String email = "naoexiste@email.com";
    String senha = "123456";

    when(usuarioRespository.findByEmail(email)).thenReturn(Optional.empty());
    
    assertThrows(RuntimeException.class, () -> authService.autenticar(email, senha));
  }

  @Test
  void deveLancarExcecaoSeSenhaForInvalida(){
    String email = "usuario@email.com";
    String senha = "senhaErrada";
    String senhaCriptografada = "$2a$10$valida";

    Usuario usuario = new Usuario();
    usuario.setEmail(email);
    usuario.setSenha(senhaCriptografada);

    when(usuarioRespository.findByEmail(email)).thenReturn(Optional.of(usuario));
    when(passwordEncoder.matches(senha, senhaCriptografada)).thenReturn(false);

    assertThrows(RuntimeException.class, () -> authService.autenticar(email, senha));
  }

  @Test
  void deveRegistrarUsuarioComDadosValidos(){
    String nome = "Fernando";
    String email = "fernando@email.com";
    String senha = "123456";
    String senhaCriptografada = "$2a$10$criptografada";

    when(usuarioRespository.findByEmail(email)).thenReturn(Optional.empty());
    when(passwordEncoder.encode(senha)).thenReturn(senhaCriptografada);
  
    authService.registrar(nome, email, senha);

    verify(usuarioRespository, times(1)).save(org.mockito.Mockito.argThat(usuario -> 
      usuario.getNome().equals(nome) &&
      usuario.getEmail().equals(email) &&
      usuario.getSenha().equals(senhaCriptografada) 
    ));
  }

  @Test
  void deveLancarExcecaoAoRegistrarEmailExistente(){
    String email = "fernando@email.com";
    
    when(usuarioRespository.findByEmail(email)).thenReturn(Optional.of(new Usuario()));

    assertThrows(RuntimeException.class, () -> {
      authService.registrar("Fernando", email, "senha");
    });
  }
}