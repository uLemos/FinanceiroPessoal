package com.financeiro.backend.application.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

  @Transactional(readOnly = true)
  public List<LancamentoFinanceiroResponseDTO> buscarPorFiltros(LocalDate dataInicio, LocalDate dataFim, String categoria, String tipo){
   List<LancamentoFinanceiro> lancamentos = lancamentoFinanceiroRepository.buscarPorFiltros(dataInicio, dataFim, categoria, tipo);
   return lancamentos.stream()
    .map(LancamentoFinanceiroMapper::toResponseDTO)
    .toList();
  }
  
  @Transactional
  public LancamentoFinanceiroResponseDTO atualizarLancamento(Long id, LancamentoFinanceiroRequestDTO dto){
    LancamentoFinanceiro existente = lancamentoFinanceiroRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Lançamento não encontrado!"));
      
    existente.setDescricao(dto.getDescricao());
    existente.setValor(dto.getValor());
    existente.setTipo(dto.getTipo());
    existente.setCategoria(dto.getCategoria());
    existente.setData(dto.getData());

    LancamentoFinanceiro atualizado = lancamentoFinanceiroRepository.save(existente);
    return LancamentoFinanceiroMapper.toResponseDTO(atualizado);
  }

  @Transactional
  public void deletarLancamento(Long id){
    LancamentoFinanceiro lancamento = lancamentoFinanceiroRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Lançamento não encontrado!"));

    lancamentoFinanceiroRepository.delete(lancamento);
  }
}
