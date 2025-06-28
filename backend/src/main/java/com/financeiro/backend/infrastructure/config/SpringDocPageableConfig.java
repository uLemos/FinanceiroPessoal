package com.financeiro.backend.infrastructure.config;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;

@Configuration
public class SpringDocPageableConfig {
  @Bean
  OperationCustomizer customizePageable() {
    return (operation, handlerMethod) -> {
      for (var parameter : handlerMethod.getMethodParameters()) {
        if (Pageable.class.equals(parameter.getParameterType())) {
          operation.addParametersItem(new Parameter()
            .in(ParameterIn.QUERY.toString())
            .name("page")
            .description("Número da página (0 baseado)")
            .required(false)
            .schema(new IntegerSchema()._default(0)));

          operation.addParametersItem(new Parameter()
            .in(ParameterIn.QUERY.toString())
            .name("size")
            .description("Tamanho da página")
            .required(false)
            .schema(new IntegerSchema()._default(10)));

          operation.addParametersItem(new Parameter()
            .in(ParameterIn.QUERY.toString())
            .name("sort")
            .description("Campo de ordenação (ex: data,asc ou valor,desc). Campos válidos: id, descricao, valor, data, tipo, categoria")
            .required(false)
            .example("data,asc")
            .schema(new StringSchema()));
        }
      }
      return operation;
    };
  }
}
