package com.financeiro.backend.application.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.financeiro.backend.domain.entitys.LancamentoFinanceiro;
import com.financeiro.backend.domain.repositories.LancamentoFinanceiroRepository;
import com.financeiro.backend.domain.specifications.FiltrosLancamentoSpecification;
import com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro.LancamentoFinanceiroRequestDTO;
import com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro.LancamentoFinanceiroResponseDTO;
import com.financeiro.backend.web.dtos.entitys.relatorioResumo.RelatorioPorCategoriaDTO;
import com.financeiro.backend.web.dtos.entitys.relatorioResumo.RelatorioResumoDTO;
import com.financeiro.backend.web.mappers.LancamentoFinanceiroMapper;

@Service
public class LancamentoFinanceiroService {
  
  private final LancamentoFinanceiroRepository lancamentoFinanceiroRepository;
  private final AuthService authService;

  public LancamentoFinanceiroService(LancamentoFinanceiroRepository lancamentoFinanceiroRepository, AuthService authService) {
    this.lancamentoFinanceiroRepository = lancamentoFinanceiroRepository;
    this.authService = authService;
  }

  @Transactional
  public LancamentoFinanceiroResponseDTO criarLancamento(LancamentoFinanceiroRequestDTO requestDTO){
    
    LancamentoFinanceiro lancamento = LancamentoFinanceiroMapper.toEntity(requestDTO);
    lancamento.setUsuario(authService.getUsuarioLogado());

    LancamentoFinanceiro lancamentoSalvo = lancamentoFinanceiroRepository.save(lancamento);

    return LancamentoFinanceiroMapper.toResponseDTO(lancamentoSalvo);
  }

  @Transactional(readOnly = true)
  public Page<LancamentoFinanceiroResponseDTO> buscarPorFiltros(LocalDate dataInicio, LocalDate dataFim, String categoria, String tipo, Pageable pageable){
    // List<LancamentoFinanceiro> lancamentos = lancamentoFinanceiroRepository.buscarPorFiltros(dataInicio, dataFim, categoria, tipo);
    Specification<LancamentoFinanceiro> spec = 
      FiltrosLancamentoSpecification.comCategoria(categoria)
      .and(FiltrosLancamentoSpecification.comTipo(tipo))
      .and(FiltrosLancamentoSpecification.comDataEntre(dataInicio, dataFim));

    Page<LancamentoFinanceiro> page = lancamentoFinanceiroRepository.findAll(spec, pageable);

    return page.map(LancamentoFinanceiroMapper::toResponseDTO);
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

  @Transactional(readOnly = true)
  public RelatorioResumoDTO gerarResumoPorPeriodo(LocalDate dataInicio, LocalDate dataFim){
    BigDecimal totalReceitas = lancamentoFinanceiroRepository.somarPorTipoEData("Receita", dataInicio, dataFim);
    BigDecimal totalDespesas = lancamentoFinanceiroRepository.somarPorTipoEData("Despesa", dataInicio, dataFim);

    // Tratando null para evitar NullPointerException ao subtrair

    totalReceitas = totalReceitas != null ? totalReceitas : BigDecimal.ZERO;
    totalDespesas = totalDespesas != null ? totalDespesas : BigDecimal.ZERO;
  
    BigDecimal saldo = totalReceitas.subtract(totalDespesas);

    return new RelatorioResumoDTO(totalReceitas, totalDespesas, saldo);
  } 

  @Transactional(readOnly = true)
  public List<RelatorioPorCategoriaDTO> gerarResumoPorCategoria(LocalDate dataInicio, LocalDate dataFim){
    return lancamentoFinanceiroRepository.somarRelatorioPorCategoriaEntreDatas(dataInicio, dataFim);
  }
}
