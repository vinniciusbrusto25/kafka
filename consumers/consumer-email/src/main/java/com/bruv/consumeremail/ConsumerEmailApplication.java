package com.bruv.consumeremail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bruv.consumeremail.service.EnvioEmailService;

@SpringBootApplication
public class ConsumerEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerEmailApplication.class, args);

		EnvioEmailService envioEmailService = new EnvioEmailService();

		envioEmailService.enviaEmail();
	}

}
