package com.financeiro.backend.web.dtos.auth;

public class AuthRequestDTO {
  private String email;
  private String senha;
  
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getSenha() {
    return senha;
  }
  public void setSenha(String senha) {
    this.senha = senha;
  }
}
