package com.financeiro.backend.web.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class ExceptionTestController {
  
  @GetMapping("/test/illegal-argument")
  public void throwIllegalArgument() {
    throw new IllegalArgumentException("Parâmetro inválido enviado");
  }

  @GetMapping("/test/illegal-state")
  public void throwIllegalState() {
    throw new IllegalStateException("Estado ilegal da aplicação");
  }
}
