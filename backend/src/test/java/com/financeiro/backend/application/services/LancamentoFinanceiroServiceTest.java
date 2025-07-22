package com.financeiro.backend.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import com.financeiro.backend.domain.entitys.LancamentoFinanceiro;
import com.financeiro.backend.domain.entitys.Usuario;
import com.financeiro.backend.domain.repositories.LancamentoFinanceiroRepository;
import com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro.LancamentoFinanceiroRequestDTO;
import com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro.LancamentoFinanceiroResponseDTO;
import com.financeiro.backend.web.dtos.entitys.relatorioResumo.RelatorioPorCategoriaDTO;
import com.financeiro.backend.web.dtos.entitys.relatorioResumo.RelatorioResumoDTO;

@ExtendWith(MockitoExtension.class)
class LancamentoFinanceiroServiceTest {
  
  @Mock
  private LancamentoFinanceiroRepository lancamentoFinanceiroRepository;

  @Mock
  private AuthService authService;

  @InjectMocks
  private LancamentoFinanceiroService lancamentoFinanceiroService;

  @Test
  void DeveCriarLancamentoComSucesso(){
    
    //Arrange
    LancamentoFinanceiroRequestDTO requestDTO = new LancamentoFinanceiroRequestDTO();
    requestDTO.setDescricao("Salário");
    requestDTO.setValor(new BigDecimal("3000.00"));
    requestDTO.setTipo("Receita");
    requestDTO.setCategoria("Trabalho");
    requestDTO.setData(LocalDate.now());

    // Simula o usuário autenticado
    Usuario usuarioMock = new Usuario();
    usuarioMock.setId(1L);
    usuarioMock.setEmail("teste@teste.com");
    usuarioMock.setSenha("senha123");

    when(authService.getUsuarioLogado()).thenReturn(usuarioMock);

    // Simulando o retorno do banco, que atribui o ID
    LancamentoFinanceiro entityComId = new LancamentoFinanceiro();
    entityComId.setId(1L);
    entityComId.setDescricao(requestDTO.getDescricao());
    entityComId.setValor(requestDTO.getValor());
    entityComId.setTipo(requestDTO.getTipo());
    entityComId.setCategoria(requestDTO.getCategoria());
    entityComId.setData(requestDTO.getData());
    entityComId.setUsuario(usuarioMock);

    when(lancamentoFinanceiroRepository.save(argThat(l -> 
      l.getDescricao().equals(requestDTO.getDescricao()) &&
      l.getValor().equals(requestDTO.getValor()) &&
      l.getTipo().equals(requestDTO.getTipo()) &&
      l.getCategoria().equals(requestDTO.getCategoria()) &&
      l.getData().equals(requestDTO.getData())
    ))).thenReturn(entityComId);

    // Act
    LancamentoFinanceiroResponseDTO resultado = lancamentoFinanceiroService.criarLancamento(requestDTO);

    // Assert
    assertEquals(entityComId.getId(), resultado.getId());
    assertEquals(entityComId.getDescricao(), resultado.getDescricao());
    assertEquals(entityComId.getValor(), resultado.getValor());
    assertEquals(entityComId.getTipo(), resultado.getTipo());
    assertEquals(entityComId.getCategoria(), resultado.getCategoria());
    assertEquals(entityComId.getData(), resultado.getData());

    verify(authService, times(1)).getUsuarioLogado();
    verify(lancamentoFinanceiroRepository, times(1)).save(any(LancamentoFinanceiro.class));
  }

  @SuppressWarnings("unchecked")
  @Test
  void deveBuscarLancamentoPorFiltroComPaginacao() {
    // Arrange
    LocalDate dataInicio = LocalDate.of(2025, 6, 1);
    LocalDate dataFim = LocalDate.of(2025, 6, 30);
    String categoria = "Moradia";
    String tipo = "Despesa";
    Pageable pageable = PageRequest.of(0, 10);

    LancamentoFinanceiro lancamento1 = new LancamentoFinanceiro();
    lancamento1.setId(1L);
    lancamento1.setDescricao("Aluguel");
    lancamento1.setValor(new BigDecimal("1200.00"));
    lancamento1.setTipo("Despesa");
    lancamento1.setCategoria("Moradia");
    lancamento1.setData(LocalDate.of(2025, 6, 5));

    LancamentoFinanceiro lancamento2 = new LancamentoFinanceiro();
    lancamento2.setId(2L);
    lancamento2.setDescricao("Condomínio");
    lancamento2.setValor(new BigDecimal("300.00"));
    lancamento2.setTipo("Despesa");
    lancamento2.setCategoria("Moradia");
    lancamento2.setData(LocalDate.of(2025, 6, 10));

    List<LancamentoFinanceiro> lancamentos = Arrays.asList(lancamento1, lancamento2);
    Page<LancamentoFinanceiro> page = new PageImpl<>(lancamentos, pageable, lancamentos.size());

    when(lancamentoFinanceiroRepository.findAll((Specification<LancamentoFinanceiro>) any(), (Pageable) any(Pageable.class))).thenReturn(page);

    // Act
    Page<LancamentoFinanceiroResponseDTO> resultado = lancamentoFinanceiroService.buscarPorFiltros(dataInicio, dataFim, categoria, tipo, pageable);

    // Assert
    assertEquals(2, resultado.getContent().size());
    assertEquals("Aluguel", resultado.getContent().get(0).getDescricao());
    assertEquals("Condomínio", resultado.getContent().get(1).getDescricao());

    verify(lancamentoFinanceiroRepository, times(1)).findAll((Specification<LancamentoFinanceiro>) any(), (Pageable) any(Pageable.class));
  }

  @Test
  void deveAtualizarLancamentoComSucesso(){
    //Arrange
    Long id = 1L;

    LancamentoFinanceiroRequestDTO dto = new LancamentoFinanceiroRequestDTO();
    dto.setDescricao("Mercado");
    dto.setValor(new BigDecimal("250.00"));
    dto.setTipo("Despesa");
    dto.setCategoria("Alimentação");
    dto.setData(LocalDate.of(2025, 6, 20));

    LancamentoFinanceiro existente = new LancamentoFinanceiro();
    existente.setId(id);
    existente.setDescricao("Antiga");
    existente.setValor(new BigDecimal("100.00"));
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
