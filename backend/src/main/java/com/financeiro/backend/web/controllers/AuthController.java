package com.financeiro.backend.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.financeiro.backend.application.services.AuthService;
import com.financeiro.backend.web.dtos.auth.AuthRequest;
import com.financeiro.backend.web.dtos.auth.AuthResponse;
import com.financeiro.backend.web.dtos.register.RegisterRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {
  
  @Autowired
  private AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
      authService.registrar(request.getNome(), request.getEmail(), request.getSenha());
      return ResponseEntity.ok("Usuário registrado com sucesso!");
  }
  
  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
    String token = authService.autenticar(request.getEmail(), request.getSenha());
    
    return ResponseEntity.ok(new AuthResponse(token)) ;
  }
  
}
