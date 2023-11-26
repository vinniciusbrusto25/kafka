package com.bruv.consumerfraude;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bruv.consumerfraude.service.DetectarFraudeService;

@SpringBootApplication
public class ConsumerFraudeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerFraudeApplication.class, args);

		DetectarFraudeService detectarFraudeService = new DetectarFraudeService();

		detectarFraudeService.detectarFraude();
	}

}
