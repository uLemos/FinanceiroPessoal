package com.financeiro.backend.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.financeiro.backend.domain.entitys.LancamentoFinanceiro;

public interface LancamentoFinanceiroRepository extends JpaRepository<LancamentoFinanceiro, Long>{
 
}
