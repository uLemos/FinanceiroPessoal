package com.financeiro.backend.web.dtos.entitys.relatorioResumo;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

public class RelatorioResumoDTO {

  @Schema(description = "Total de receitas no período", example = "5000.00")
  private BigDecimal receitaTotal;

  @Schema(description = "Total de despesas no período", example = "3000.00")
  private BigDecimal despesaTotal;

  @Schema(description = "Saldo resultante (receitas menos despesas)", example = "2000.00")
  private BigDecimal saldo;
  
  public RelatorioResumoDTO(BigDecimal receitaTotal, BigDecimal despesaTotal, BigDecimal saldo) {
    this.receitaTotal = receitaTotal;
    this.despesaTotal = despesaTotal;
    this.saldo = saldo;
  }

  public BigDecimal getReceitaTotal() {
    return receitaTotal;
  }

  public void setReceitaTotal(BigDecimal receitaTotal) {
    this.receitaTotal = receitaTotal;
  }

  public BigDecimal getDespesaTotal() {
    return despesaTotal;
  }

  public void setDespesaTotal(BigDecimal despesaTotal) {
    this.despesaTotal = despesaTotal;
  }

  public BigDecimal getSaldo() {
    return saldo;
  }

  public void setSaldo(BigDecimal saldo) {
    this.saldo = saldo;
  }
}
