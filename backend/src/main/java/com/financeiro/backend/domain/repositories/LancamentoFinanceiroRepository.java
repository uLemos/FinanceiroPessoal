package com.financeiro.backend.domain.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.financeiro.backend.domain.entitys.LancamentoFinanceiro;
import com.financeiro.backend.web.dtos.entitys.relatorioResumo.RelatorioPorCategoriaDTO;

public interface LancamentoFinanceiroRepository extends JpaRepository<LancamentoFinanceiro, Long>, JpaSpecificationExecutor<LancamentoFinanceiro>{
 
  @Query("SELECT l FROM LancamentoFinanceiro l WHERE " +
        "(:dataInicio IS NULL OR l.data >= :dataInicio) AND " +
        "(:dataFim IS NULL OR l.data <= :dataFim) AND " +
        "(:categoria IS NULL OR l.categoria = :categoria) AND " +
        "(:tipo IS NULL OR l.tipo = :tipo)")
  List<LancamentoFinanceiro> buscarPorFiltros(
    @Param("dataInicio") LocalDate dataInicio, 
    @Param("dataFim") LocalDate dataFim,
    @Param("categoria") String categoria,
    @Param("tipo") String tipo
  );

  @Query("SELECT SUM(l.valor) FROM LancamentoFinanceiro l WHERE l.tipo = :tipo AND l.data BETWEEN :dataInicio AND :dataFim")
  BigDecimal somarPorTipoEData(@Param("tipo") String tipo,
    @Param("dataInicio") LocalDate dataInicio,
    @Param("dataFim") LocalDate dataFim); 

  @Query("SELECT new com.financeiro.backend.web.dtos.entitys.relatorioResumo.RelatorioPorCategoriaDTO(l.categoria, SUM(l.valor)) " +
    "FROM LancamentoFinanceiro l " + 
    "WHERE l.data BETWEEN :dataInicio AND :dataFim " +
    "GROUP BY l.categoria")
  List<RelatorioPorCategoriaDTO> somarRelatorioPorCategoriaEntreDatas(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
}
