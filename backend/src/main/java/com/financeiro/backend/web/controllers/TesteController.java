package com.financeiro.backend.web.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class TesteController {

  @GetMapping("teste")
  public String getTeste() {
      return "Olá, tá funcionando bem GG";
  }
  
}
