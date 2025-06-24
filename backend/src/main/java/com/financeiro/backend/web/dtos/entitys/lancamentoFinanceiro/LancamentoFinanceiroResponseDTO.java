package com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LancamentoFinanceiroResponseDTO {

  private Long id;
  private String descricao;
  private BigDecimal valor;
  private String tipo;
  private String categoria;
  private LocalDate data;
  
  public LancamentoFinanceiroResponseDTO(Long id, String descricao, BigDecimal valor, String tipo, String categoria,
      LocalDate data) {
    this.id = id;
    this.descricao = descricao;
    this.valor = valor;
    this.tipo = tipo;
    this.categoria = categoria;
    this.data = data;
  }

  public Long getId() {
    return id;
  }

  public String getDescricao() {
    return descricao;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public String getTipo() {
    return tipo;
  }

  public String getCategoria() {
    return categoria;
  }

  public LocalDate getData() {
    return data;
  }
}
