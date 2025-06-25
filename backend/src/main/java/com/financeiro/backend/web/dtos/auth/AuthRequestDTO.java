package com.financeiro.backend.web.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO para autenticação do usuário (login)")
public class AuthRequestDTO {

  @NotBlank(message = "O e-mail é obrigatório")
  @Email(message = "Formato de e-mail inválido")
  @Schema(description = "E-mail do usuário", example = "usuario@email.com", required = true)
  private String email;

  @NotBlank(message = "A senha é obrigatória")
  @Schema(description = "Senha do usuário", example = "senha123", required = true)
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
