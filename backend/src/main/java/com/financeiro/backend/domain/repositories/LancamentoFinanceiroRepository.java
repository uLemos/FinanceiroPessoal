package com.financeiro.backend.domain.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.financeiro.backend.domain.entitys.LancamentoFinanceiro;

public interface LancamentoFinanceiroRepository extends JpaRepository<LancamentoFinanceiro, Long>{
 
  @Query("SELECT 1 FROM LancamentoFinanceiro 1 WHERE " +
        "(:dataInicio IS NULL OR 1.data >= :dataInicio) AND " +
        "(:dataFim IS NULL OR 1.data <= :dataFim) AND " +
        "(:categoria IS NULL OR 1.categoria = :categoria) AND " +
        "(:tipo IS NULL OR 1.tipo = :tipo)")
  List<LancamentoFinanceiro> buscarPorFiltros(
    @Param("dataInicio") LocalDate dataInicio, 
    @Param("dataFim") LocalDate dataFim,
    @Param("categoria") String categoria,
    @Param("tipo") String tipo
  );
}
