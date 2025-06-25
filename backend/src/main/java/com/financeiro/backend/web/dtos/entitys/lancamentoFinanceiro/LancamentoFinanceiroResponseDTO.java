package com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

public class LancamentoFinanceiroResponseDTO {

  @Schema(description = "Identificador único do lançamento", example = "1")
  private Long id;

  @Schema(description = "Descrição do lançamento financeiro", example = "Compra de supermercado")
  private String descricao;

  @Schema(description = "Valor do lançamento financeiro", example = "150.50")
  private BigDecimal valor;

  @Schema(description = "Tipo do lançamento (Receita ou Despesa)", example = "Despesa")
  private String tipo;

  @Schema(description = "Categoria do lançamento financeiro", example = "Alimentação")
  private String categoria;

  @Schema(description = "Data do lançamento financeiro", example = "2025-06-25")
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
