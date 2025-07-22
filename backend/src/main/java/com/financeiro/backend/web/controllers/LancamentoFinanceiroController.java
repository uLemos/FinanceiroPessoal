package com.financeiro.backend.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.financeiro.backend.application.services.LancamentoFinanceiroService;
import com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro.LancamentoFinanceiroRequestDTO;
import com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro.LancamentoFinanceiroResponseDTO;
import com.financeiro.backend.web.dtos.entitys.relatorioResumo.RelatorioPorCategoriaDTO;
import com.financeiro.backend.web.dtos.entitys.relatorioResumo.RelatorioResumoDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/v1/api/lancamentos")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Lançamentos Financeiros", description = "Endpoints para gerenciar lançamentos financeiros")
public class LancamentoFinanceiroController {
  
  private final LancamentoFinanceiroService lancamentoFinanceiroService;

  public LancamentoFinanceiroController(LancamentoFinanceiroService lancamentoFinanceiroService) {
    this.lancamentoFinanceiroService = lancamentoFinanceiroService;
  }

  @Operation(summary = "Criar novo lançamento financeiro", description = "Cria um novo lançamento com base nas informações fornecidas.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Lançamento criado com sucesso"),
      @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
  })
  @PostMapping
  public ResponseEntity<LancamentoFinanceiroResponseDTO> criarLancamento(@Valid @RequestBody LancamentoFinanceiroRequestDTO requestDTO) {
      LancamentoFinanceiroResponseDTO responseDTO = lancamentoFinanceiroService.criarLancamento(requestDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO); 
  }

  @Operation(summary = "Listar lançamentos", description = "Lista lançamentos filtrando por data, categoria e tipo")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lista de lançamentos retornada com sucesso")
  })
  @GetMapping
  public ResponseEntity<Page<LancamentoFinanceiroResponseDTO>> listarLancamentos(
    @Parameter(description = "Data de início do filtro") @RequestParam(required = false) LocalDate dataInicio,
    @Parameter(description = "Data de fim do filtro") @RequestParam(required = false) LocalDate dataFim,
    @Parameter(description = "Categoria do lançamento") @RequestParam(required = false) String categoria,
    @Parameter(description = "Tipo do lançamento (RECEITA ou DESPESA)") @RequestParam(required = false) String tipo,
    Pageable pageable) {
      Page<LancamentoFinanceiroResponseDTO> lista = lancamentoFinanceiroService.buscarPorFiltros(dataInicio, dataFim, categoria, tipo, pageable);
      return ResponseEntity.ok(lista);
  }

  @Operation(summary = "Gerar resumo financeiro", description = "Gera um resumo com total de receitas, despesas e saldo no período")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Resumo gerado com sucesso")
  })
  @GetMapping("/resumo")
  public ResponseEntity<RelatorioResumoDTO> gerarResumo(
    @Parameter(description = "Data de início do período") @RequestParam LocalDate dataInicio,
    @Parameter(description = "Data de fim do período") @RequestParam LocalDate dataFim) {
      return ResponseEntity.ok(lancamentoFinanceiroService.gerarResumoPorPeriodo(dataInicio, dataFim));
  }
  
  @Operation(summary = "Gerar resumo por categoria", description = "Agrupa e totaliza lançamentos por categoria no período definido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Resumo por categoria gerado com sucesso")
  })
  @GetMapping("/resumo/categorias")
  public ResponseEntity<List<RelatorioPorCategoriaDTO>> gerarResumoPorCategoria(
    @Parameter(description = "Data de início do período") @RequestParam LocalDate dataInicio,
    @Parameter(description = "Data de fim do período") @RequestParam LocalDate dataFim) {
      return ResponseEntity.ok(lancamentoFinanceiroService.gerarResumoPorCategoria(dataInicio, dataFim));
  }

  @Operation(summary = "Atualizar lançamento financeiro", description = "Atualiza um lançamento com base no ID e dados fornecidos")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lançamento atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Lançamento não encontrado")
  })
  @PutMapping("/{id}")
  public ResponseEntity<LancamentoFinanceiroResponseDTO> atualizarLancamento(
    @Parameter(description = "ID do lançamento") @PathVariable Long id,
    @RequestBody @Valid LancamentoFinanceiroRequestDTO dto) {
      return ResponseEntity.ok(lancamentoFinanceiroService.atualizarLancamento(id, dto));
  }

  @Operation(summary = "Deletar lançamento financeiro", description = "Remove um lançamento do sistema com base no ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Lançamento deletado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Lançamento não encontrado")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<LancamentoFinanceiroResponseDTO> deletarFinanceiro(
    @Parameter(description = "ID do lançamento") @PathVariable Long id){
    lancamentoFinanceiroService.deletarLancamento(id);
    return ResponseEntity.noContent().build();
  }
}
