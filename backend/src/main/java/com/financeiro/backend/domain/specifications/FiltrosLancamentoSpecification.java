package com.financeiro.backend.domain.specifications;

import java.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;
import com.financeiro.backend.domain.entitys.LancamentoFinanceiro;

public class FiltrosLancamentoSpecification {
  
  private FiltrosLancamentoSpecification(){}

  public static Specification<LancamentoFinanceiro> comCategoria(String categoria) {
    return (root, query, builder) ->
      categoria == null ? null : builder.equal(root.get("categoria"), categoria);
  }

  public static Specification<LancamentoFinanceiro> comTipo(String tipo) {
    return (root, query, builder) ->
      tipo == null ? null : builder.equal(root.get("tipo"), tipo);
  }

  public static Specification<LancamentoFinanceiro> comDataEntre(LocalDate dataInicio, LocalDate dataFim) {
    return (root, query, builder) -> {
      if (dataInicio != null && dataFim != null) {
        return builder.between(root.get("data"), dataInicio, dataFim);
      } else if (dataInicio != null) {
        return builder.greaterThanOrEqualTo(root.get("data"), dataInicio);
      } else if (dataFim != null) {
        return builder.lessThanOrEqualTo(root.get("data"), dataFim);
      } else {
        return null;
      }
    };
  }
}
