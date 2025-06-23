package com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro;

import java.time.LocalDate;

public class LancamentoFinanceiroRequestDTO {
  
  private String descricao;
  private float valor;
  private String tipo;
  private String categoria;
  private LocalDate data;

  public String getDescricao() {
    return descricao;
  }
  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }
  public float getValor() {
    return valor;
  }
  public void setValor(float valor) {
    this.valor = valor;
  }
  public String getTipo() {
    return tipo;
  }
  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
  public String getCategoria() {
    return categoria;
  }
  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }
  public LocalDate getData() {
    return data;
  }
  public void setData(LocalDate data) {
    this.data = data;
  }
}
