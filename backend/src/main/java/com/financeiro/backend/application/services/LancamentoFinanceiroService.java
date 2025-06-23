package com.financeiro.backend.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.backend.domain.entitys.LancamentoFinanceiro;
import com.financeiro.backend.domain.repositories.LancamentoFinanceiroRepository;
import com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro.LancamentoFinanceiroRequestDTO;
import com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro.LancamentoFinanceiroResponseDTO;
import com.financeiro.backend.web.mappers.LancamentoFinanceiroMapper;

@Service
public class LancamentoFinanceiroService {
  
  private final LancamentoFinanceiroRepository lancamentoFinanceiroRepository;

  public LancamentoFinanceiroService(LancamentoFinanceiroRepository lancamentoFinanceiroRepository) {
    this.lancamentoFinanceiroRepository = lancamentoFinanceiroRepository;
  }

  @Transactional
  public LancamentoFinanceiroResponseDTO criarLancamento(LancamentoFinanceiroRequestDTO requestDTO){

    LancamentoFinanceiro lancamento = LancamentoFinanceiroMapper.toEntity(requestDTO);
    LancamentoFinanceiro lancamentoSalvo = lancamentoFinanceiroRepository.save(lancamento);

    return LancamentoFinanceiroMapper.toResponseDTO(lancamentoSalvo);
  }
}
