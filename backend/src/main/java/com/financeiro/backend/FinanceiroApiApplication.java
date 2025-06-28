package com.financeiro.backend;

// import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.financeiro.backend.infrastructure.config.DotenvInitializer;

@SpringBootApplication
public class FinanceiroApiApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(FinanceiroApiApplication.class)
			.initializers(new DotenvInitializer())
			.run(args);
		// SpringApplication.run(FinanceiroApiApplication.class, args);
	}

}
