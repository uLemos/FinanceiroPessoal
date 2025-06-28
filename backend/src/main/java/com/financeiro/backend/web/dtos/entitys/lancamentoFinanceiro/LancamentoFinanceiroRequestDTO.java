package com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LancamentoFinanceiroRequestDTO {
  
  @NotBlank(message = "A descrição é obrigatória")
  @Schema(description = "Descrição do lançamento financeiro", example = "Compra de supermercado", required = true)
  private String descricao;

  @NotNull(message = "A data é obrigatória")
  @Schema(description = "Valor do lançamento financeiro", example = "150.50", required = true)
  private BigDecimal valor;

  @NotNull(message = "O tipo é obrigatória")
  @Schema(description = "Tipo do lançamento (Receita ou Despesa)", example = "Despesa", required = true)
  private String tipo;

  @NotNull(message = "A categoria é obrigatória")
  @Schema(description = "Categoria do lançamento financeiro", example = "Alimentação", required = true)
  private String categoria;

  @NotNull(message = "A data é obrigatória")
  @Schema(description = "Data do lançamento financeiro", example = "2025-06-25", required = true)
  private LocalDate data;

  public String getDescricao() {
    return descricao;
  }
  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }
  public BigDecimal getValor() {
    return valor;
  }
  public void setValor(BigDecimal valor) {
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
