package com.financeiro.backend.web.dtos.entitys.relatorioResumo;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

public class RelatorioPorCategoriaDTO {

  @Schema(description = "Nome da categoria", example = "Alimentação")
  private String categoria;

  @Schema(description = "Total somado para a categoria", example = "1500.75")
  private BigDecimal total;
  
  public RelatorioPorCategoriaDTO(String categoria, BigDecimal total) {
    this.categoria = categoria;
    this.total = total;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }
}
