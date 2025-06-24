package com.financeiro.backend.web.dtos.entitys.relatorioResumo;

import java.math.BigDecimal;

public class RelatorioResumoDTO {
  private BigDecimal receitaTotal;
  private BigDecimal despesaTotal;
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
