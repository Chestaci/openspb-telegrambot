package com.github.Chestaci.openspbtb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OpenSPbTelegramBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenSPbTelegramBotApplication.class, args);
	}

}
