package com.financeiro.backend.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.financeiro.backend.application.services.AuthService;
import com.financeiro.backend.web.dtos.auth.AuthRequestDTO;
import com.financeiro.backend.web.dtos.auth.AuthResponseDTO;
import com.financeiro.backend.web.dtos.auth.RegisterRequestDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints para registro e login de usuários")
public class AuthController {
  
  @Autowired
  private AuthService authService;

  @Operation(summary = "Registrar novo usuário", description = "Realiza o cadastro de um novo usuário no sistema.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
      @ApiResponse(responseCode = "400", description = "Dados inválidos ou e-mail já existente")
  })
  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterRequestDTO request) {
      authService.registrar(request.getNome(), request.getEmail(), request.getSenha());
      return ResponseEntity.ok("Usuário registrado com sucesso!");
  }
  
  @Operation(summary = "Login do usuário", description = "Autentica o usuário e retorna um token JWT.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
      @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
  })
  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
    String token = authService.autenticar(request.getEmail(), request.getSenha());
    return ResponseEntity.ok(new AuthResponseDTO(token)) ;
  }
}
