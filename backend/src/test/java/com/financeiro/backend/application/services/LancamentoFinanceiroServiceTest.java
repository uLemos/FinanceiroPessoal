package com.financeiro.backend.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.financeiro.backend.domain.entitys.LancamentoFinanceiro;
import com.financeiro.backend.domain.repositories.LancamentoFinanceiroRepository;
import com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro.LancamentoFinanceiroRequestDTO;
import com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro.LancamentoFinanceiroResponseDTO;
import com.financeiro.backend.web.dtos.entitys.relatorioResumo.RelatorioPorCategoriaDTO;
import com.financeiro.backend.web.dtos.entitys.relatorioResumo.RelatorioResumoDTO;
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
    requestDTO.setData(LocalDate.now());

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

  @Test
  void deveBuscarLancamentoPorFiltro(){
    //Arrange
    LocalDate dataInicio = LocalDate.of(2025, 6, 1);
    LocalDate dataFim = LocalDate.of(2025, 6, 30);
    String categoria = "Moradia";
    String tipo = "Despesa";

    LancamentoFinanceiro lancamento1 = new LancamentoFinanceiro();
    lancamento1.setId(1L);
    lancamento1.setDescricao("Aluguel");
    lancamento1.setValor(1200.0f);
    lancamento1.setTipo("Despesa");
    lancamento1.setCategoria("Moradia");
    lancamento1.setData(LocalDate.of(2025, 6, 5));

    LancamentoFinanceiro lancamento2 = new LancamentoFinanceiro();
    lancamento2.setId(2L);
    lancamento2.setDescricao("Condomínio");
    lancamento2.setValor(300.0f);
    lancamento2.setTipo("Despesa");
    lancamento2.setCategoria("Moradia");
    lancamento2.setData(LocalDate.of(2025, 6, 10));

    List<LancamentoFinanceiro> mockLancamentos = Arrays.asList(lancamento1, lancamento2);

    when(lancamentoFinanceiroRepository.buscarPorFiltros(dataInicio, dataFim, categoria, tipo)).thenReturn(mockLancamentos);

    //Act
    List<LancamentoFinanceiroResponseDTO> resultado = lancamentoFinanceiroService.buscarPorFiltros(dataInicio, dataFim, categoria, tipo);

    //Assert
    assertEquals(2, resultado.size());
    assertEquals("Aluguel", resultado.get(0).getDescricao());
    assertEquals("Condomínio", resultado.get(1).getDescricao());

    verify(lancamentoFinanceiroRepository, times(1)).buscarPorFiltros(dataInicio, dataFim, categoria, tipo);
  }

  @Test
  void deveAtualizarLancamentoComSucesso(){
    //Arrange
    Long id = 1L;

    LancamentoFinanceiroRequestDTO dto = new LancamentoFinanceiroRequestDTO();
    dto.setDescricao("Mercado");
    dto.setValor(250.0f);
    dto.setTipo("Despesa");
    dto.setCategoria("Alimentação");
    dto.setData(LocalDate.of(2025, 6, 20));

    LancamentoFinanceiro existente = new LancamentoFinanceiro();
    existente.setId(id);
    existente.setDescricao("Antiga");
    existente.setValor(100.0f);
    existente.setTipo("Despesa");
    existente.setCategoria("Outros");
    existente.setData(LocalDate.of(2025, 6, 10));

    when(lancamentoFinanceiroRepository.findById(id)).thenReturn(Optional.of(existente));
    when(lancamentoFinanceiroRepository.save(existente)).thenReturn(existente);

    //Act
    LancamentoFinanceiroResponseDTO response = lancamentoFinanceiroService.atualizarLancamento(id, dto);

    //Assert
    assertEquals(dto.getDescricao(), response.getDescricao());
    assertEquals(dto.getValor(), response.getValor());
    assertEquals(dto.getTipo(), response.getTipo());
    assertEquals(dto.getCategoria(), response.getCategoria());
    assertEquals(dto.getData(), response.getData());

    verify(lancamentoFinanceiroRepository, times(1)).findById(id);
    verify(lancamentoFinanceiroRepository, times(1)).save(existente);
  }

  @Test
  void deveDeletarLancamentoComSucesso(){
    //Arrange
    Long id = 1L;
    LancamentoFinanceiro lancamento = new LancamentoFinanceiro();

    lancamento.setId(id);

    when(lancamentoFinanceiroRepository.findById(id)).thenReturn(Optional.of(lancamento));

    //Act
    lancamentoFinanceiroService.deletarLancamento(id);

    //Assert
    verify(lancamentoFinanceiroRepository, times(1)).findById(id);
    verify(lancamentoFinanceiroRepository, times(1)).delete(lancamento);
  }

  @Test 
  void deveGerarResumoComSucesso(){
    //Arrange
    LocalDate dataInicio = LocalDate.of(2025, 6, 1);
    LocalDate dataFim = LocalDate.of(2025, 6, 30);

    BigDecimal totalReceitas = new BigDecimal("5000.00");
    BigDecimal totalDespesas = new BigDecimal("2000.00");

    when(lancamentoFinanceiroRepository.somarPorTipoEData("Receita", dataInicio, dataFim)).thenReturn(totalReceitas);
    when(lancamentoFinanceiroRepository.somarPorTipoEData("Despesa", dataInicio, dataFim)).thenReturn(totalDespesas);

    //Act
    RelatorioResumoDTO resumo = lancamentoFinanceiroService.gerarResumoPorPeriodo(dataInicio, dataFim);

    //Assert
    assertEquals(new BigDecimal("5000.00"), resumo.getReceitaTotal());
    assertEquals(new BigDecimal("2000.00"), resumo.getDespesaTotal());
    assertEquals(new BigDecimal("3000.00"), resumo.getSaldo());

    verify(lancamentoFinanceiroRepository, times(1)).somarPorTipoEData("Receita", dataInicio, dataFim);
    verify(lancamentoFinanceiroRepository, times(1)).somarPorTipoEData("Despesa", dataInicio, dataFim);
  }

  @Test
  void deveGerarResumoComValoresNulos() {
      // Arrange
      LocalDate dataInicio = LocalDate.of(2025, 6, 1);
      LocalDate dataFim = LocalDate.of(2025, 6, 30);

      when(lancamentoFinanceiroRepository.somarPorTipoEData("Receita", dataInicio, dataFim)).thenReturn(null);
      when(lancamentoFinanceiroRepository.somarPorTipoEData("Despesa", dataInicio, dataFim)).thenReturn(null);

      // Act
      RelatorioResumoDTO resumo = lancamentoFinanceiroService.gerarResumoPorPeriodo(dataInicio, dataFim);

      // Assert
      assertEquals(BigDecimal.ZERO, resumo.getReceitaTotal());
      assertEquals(BigDecimal.ZERO, resumo.getDespesaTotal());
      assertEquals(BigDecimal.ZERO, resumo.getSaldo());
  }

  @Test
  void deveGerarResumoPorCategoriaComSucesso(){
    //Arrange
    LocalDate dataInicio = LocalDate.of(2025,6,1);
    LocalDate dataFim = LocalDate.of(2025, 6, 30);

    List<RelatorioPorCategoriaDTO> mockResultado = Arrays.asList(
      new RelatorioPorCategoriaDTO("Moradia", new BigDecimal("1500.00")),
      new RelatorioPorCategoriaDTO("Alimentação", new BigDecimal("600.00"))
    );

    when(lancamentoFinanceiroRepository.somarRelatorioPorCategoriaEntreDatas(dataInicio, dataFim)).thenReturn(mockResultado);

    //Act
    List<RelatorioPorCategoriaDTO> resultado = lancamentoFinanceiroService.gerarResumoPorCategoria(dataInicio, dataFim);
  
    //Assert
    assertEquals(2, resultado.size());
    assertEquals("Moradia", resultado.get(0).getCategoria());
    assertEquals(new BigDecimal("1500.00"), resultado.get(0).getTotal());
    assertEquals("Alimentação", resultado.get(1).getCategoria());
    assertEquals(new BigDecimal("600.00"), resultado.get(1).getTotal());

    verify(lancamentoFinanceiroRepository, times(1)).somarRelatorioPorCategoriaEntreDatas(dataInicio, dataFim);
  }

  @Test
  void deveRetornarListaVaziaQuandoNaoHouverLancamentosNaData(){
    //Arrange
    LocalDate dataInicio = LocalDate.of(2025, 1, 1);
    LocalDate dataFim = LocalDate.of(2025, 1, 31);

    when(lancamentoFinanceiroRepository.somarRelatorioPorCategoriaEntreDatas(dataInicio, dataFim)).thenReturn(List.of());

    //Act
    List<RelatorioPorCategoriaDTO> resultado = lancamentoFinanceiroService.gerarResumoPorCategoria(dataInicio, dataFim);

    //Assert
    assertEquals(0, resultado.size());
    
    verify(lancamentoFinanceiroRepository, times(1)).somarRelatorioPorCategoriaEntreDatas(dataInicio, dataFim);
  }
}
