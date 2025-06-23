package com.financeiro.backend.web.dtos.auth;

public class AuthResponseDTO {
  private String token;

  public AuthResponseDTO(String token) {
    this.token = token;
  }
  
  public String getToken(){
    return token;
  } 
}
