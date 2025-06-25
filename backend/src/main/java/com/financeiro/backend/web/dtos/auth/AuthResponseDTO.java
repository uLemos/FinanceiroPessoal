package com.financeiro.backend.web.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;

public class AuthResponseDTO {

  @Schema(description = "Token JWT gerado após autenticação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
  private String token;

  public AuthResponseDTO(String token) {
    this.token = token;
  }
  
  public String getToken(){
    return token;
  } 
}
