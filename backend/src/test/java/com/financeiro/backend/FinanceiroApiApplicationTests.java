package com.financeiro.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import com.financeiro.backend.infrastructure.config.DotenvInitializer;

@SpringBootTest
@ContextConfiguration(initializers = DotenvInitializer.class)
class FinanceiroApiApplicationTests {

	@Test
	void contextLoads() {
		//Sem uso no momento.
	}
}
