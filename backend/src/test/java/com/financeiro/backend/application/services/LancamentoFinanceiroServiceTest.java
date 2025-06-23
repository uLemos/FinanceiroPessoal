package com.financeiro.backend.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Date;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.financeiro.backend.domain.entitys.LancamentoFinanceiro;
import com.financeiro.backend.domain.repositories.LancamentoFinanceiroRepository;
import com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro.LancamentoFinanceiroRequestDTO;
import com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro.LancamentoFinanceiroResponseDTO;
import com.financeiro.backend.web.mappers.LancamentoFinanceiroMapper;

@ExtendWith(MockitoExtension.class)
public class LancamentoFinanceiroServiceTest {
  
  @Mock
  private LancamentoFinanceiroRepository lancamentoFinanceiroRepository;

  @InjectMocks
  private LancamentoFinanceiroService lancamentoFinanceiroService;

  @Test
  void DeveCriarLancamentoComSucesso(){
    
    //Arrange

    LancamentoFinanceiroRequestDTO requestDTO = new LancamentoFinanceiroRequestDTO();
    requestDTO.setDescricao("Salário");
    requestDTO.setValor(3000.00f);
    requestDTO.setTipo("Receita");
    requestDTO.setCategoria("Trabalho");
    requestDTO.setData(Date.valueOf("2025-06-01"));

    LancamentoFinanceiro entity = LancamentoFinanceiroMapper.toEntity(requestDTO);
    // Simulando o retorno do banco, que atribui o ID
    LancamentoFinanceiro entityComId = new LancamentoFinanceiro();
    entityComId.setId(1L);
    entityComId.setDescricao(entity.getDescricao());
    entityComId.setValor(entity.getValor());
    entityComId.setTipo(entity.getTipo());
    entityComId.setCategoria(entity.getCategoria());
    entityComId.setData(entity.getData());

    LancamentoFinanceiroResponseDTO responseDTO = LancamentoFinanceiroMapper.toResponseDTO(entityComId);

    when(lancamentoFinanceiroRepository.save(entity)).thenReturn(entityComId);

    //Act
    LancamentoFinanceiroResponseDTO resultado = lancamentoFinanceiroService.criarLancamento(requestDTO);

    //Assert
    assertEquals(responseDTO.getId(), resultado.getId());
    assertEquals(responseDTO.getDescricao(), resultado.getDescricao());
    assertEquals(responseDTO.getValor(), resultado.getValor());
    assertEquals(responseDTO.getTipo(), resultado.getTipo());
    assertEquals(responseDTO.getCategoria(), resultado.getCategoria());
    assertEquals(responseDTO.getData(), resultado.getData());

    verify(lancamentoFinanceiroRepository, times(1)).save(entity);
  }
}
