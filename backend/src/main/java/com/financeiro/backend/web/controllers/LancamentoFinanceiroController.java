package com.financeiro.backend.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.financeiro.backend.application.services.LancamentoFinanceiroService;
import com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro.LancamentoFinanceiroRequestDTO;
import com.financeiro.backend.web.dtos.entitys.lancamentoFinanceiro.LancamentoFinanceiroResponseDTO;

import java.time.LocalDate;
import java.util.List;

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
@RequestMapping("/api/lancamentos")
public class LancamentoFinanceiroController {
  
  private final LancamentoFinanceiroService lancamentoFinanceiroService;

  public LancamentoFinanceiroController(LancamentoFinanceiroService lancamentoFinanceiroService) {
    this.lancamentoFinanceiroService = lancamentoFinanceiroService;
  }

  @PostMapping
  public ResponseEntity<LancamentoFinanceiroResponseDTO> criarLancamento(@RequestBody LancamentoFinanceiroRequestDTO requestDTO) {
      LancamentoFinanceiroResponseDTO responseDTO = lancamentoFinanceiroService.criarLancamento(requestDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO); 
  }

  @GetMapping
  public ResponseEntity<List<LancamentoFinanceiroResponseDTO>> listarLancamentos(
    @RequestParam(required = false) LocalDate dataInicio,
    @RequestParam(required = false) LocalDate dataFim,
    @RequestParam(required = false) String categoria,
    @RequestParam(required = false) String tipo) {
      List<LancamentoFinanceiroResponseDTO> lista = lancamentoFinanceiroService.buscarPorFiltros(dataInicio, dataFim, categoria, tipo);
      return ResponseEntity.ok(lista);
  }

  @PutMapping("/{id}")
  public ResponseEntity<LancamentoFinanceiroResponseDTO> atualizarLancamento(@PathVariable Long id, @RequestBody LancamentoFinanceiroRequestDTO dto) {
      return ResponseEntity.ok(lancamentoFinanceiroService.atualizarLancamento(id, dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<LancamentoFinanceiroResponseDTO> deletarFinanceiro(@PathVariable Long id){
    lancamentoFinanceiroService.deletarLancamento(id);
    return ResponseEntity.noContent().build();
  }
}
