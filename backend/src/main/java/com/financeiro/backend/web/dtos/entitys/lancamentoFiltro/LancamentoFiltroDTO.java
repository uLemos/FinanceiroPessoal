package com.financeiro.backend.web.dtos.entitys.lancamentoFiltro;

import java.time.LocalDate;

public class LancamentoFiltroDTO {
  
  private LocalDate dataInicio;
  private LocalDate dataFim;
  private String categoria;
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
