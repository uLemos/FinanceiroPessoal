package com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

public class LancamentoFiltroDTO {
  
  @Schema(description = "Data inicial para filtro dos lançamentos financeiros", example = "2025-06-01", nullable = true)
  private LocalDate dataInicio;

  @Schema(description = "Data final para filtro dos lançamentos financeiros", example = "2025-06-15", nullable = true)
  private LocalDate dataFim;

  @Schema(description = "Categoria para filtro dos lançamentos financeiros", example = "Alimentação", nullable = true)
  private String categoria;

  @Schema(description = "Tipo para filtro dos lançamentos financeiros (ex: Receita, Despesa)", example = "Receita", nullable = true)
  private String tipo;

  public LocalDate getDataInicio() {
    return dataInicio;
  }
  public void setDataInicio(LocalDate dataInicio) {
    this.dataInicio = dataInicio;
  }
  public LocalDate getDataFim() {
    return dataFim;
  }
  public void setDataFim(LocalDate dataFim) {
    this.dataFim = dataFim;
  }
  public String getCategoria() {
    return categoria;
  }
  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }
  public String getTipo() {
    return tipo;
  }
  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
}
