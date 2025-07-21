package com.financeiro.backend.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import com.financeiro.backend.infrastructure.config.JwtFilter;
import com.financeiro.backend.infrastructure.config.TestSecurityConfig;
import com.financeiro.backend.web.exceptions.GlobalExceptionHandler;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.context.annotation.FilterType;

@WebMvcTest(controllers = ExceptionTestController.class, 
  excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtFilter.class))
@Import({GlobalExceptionHandler.class, TestSecurityConfig.class})
public class ExceptionTestControllerTest {

  @Autowired
  private MockMvc mockMvc;
  
  @Test
  void deveRetornarBadRequestParaIllegalArgument() throws Exception {
    mockMvc.perform(get("/test/illegal-argument"))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.message").value("Parâmetro inválido enviado"));
  }

  @Test
  void deveRetornarConflictParaIllegalState() throws Exception {
    mockMvc.perform(get("/test/illegal-state"))
      .andExpect(status().isConflict())
      .andExpect(jsonPath("$.message").value("Estado ilegal da aplicação"));
  }
}
