package com.flashsale.event;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
class EventApplicationTests {

	@DynamicPropertySource
	static void loadEnv(DynamicPropertyRegistry registry) {

		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()
				.load();

		dotenv.entries().forEach(e ->
				registry.add(e.getKey(), e::getValue)
		);
	}

	@Test
	void contextLoads() {

	}

}
