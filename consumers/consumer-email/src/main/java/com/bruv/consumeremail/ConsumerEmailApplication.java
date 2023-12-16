package com.bruv.consumeremail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bruv.consumeremail.service.EnvioEmailConsumerService;

@SpringBootApplication
public class ConsumerEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerEmailApplication.class, args);

		EnvioEmailConsumerService envioEmailService = new EnvioEmailConsumerService();

		envioEmailService.enviaEmail();
	}

}
