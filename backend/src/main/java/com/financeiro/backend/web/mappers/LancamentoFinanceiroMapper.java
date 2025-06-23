package com.financeiro.backend.web.mappers;

import com.financeiro.backend.domain.entitys.LancamentoFinanceiro;
import com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro.LancamentoFinanceiroRequestDTO;
import com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro.LancamentoFinanceiroResponseDTO;

public class LancamentoFinanceiroMapper {
  
  public static LancamentoFinanceiro toEntity(LancamentoFinanceiroRequestDTO dto){
    LancamentoFinanceiro entity = new LancamentoFinanceiro();
    entity.setDescricao(dto.getDescricao());
    entity.setValor(dto.getValor());
    entity.setTipo(dto.getTipo());
    entity.setCategoria(dto.getCategoria());
    entity.setData(dto.getData());
    return entity;
  }

  public static LancamentoFinanceiroResponseDTO toResponseDTO(LancamentoFinanceiro entity){
    return new LancamentoFinanceiroResponseDTO(
      entity.getId(), 
      entity.getDescricao(),
      entity.getValor(),
      entity.getTipo(),
      entity.getCategoria(),
      entity.getData()
    );
  }
}