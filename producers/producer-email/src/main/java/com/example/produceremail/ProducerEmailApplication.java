package com.example.produceremail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.produceremail.service.EnvioEmailService;

@SpringBootApplication
public class ProducerEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerEmailApplication.class, args);

		EnvioEmailService envioEmailService = new EnvioEmailService();

		for(int i=0; i<10; i++) {
			envioEmailService.produzirNovaMensagem();
		}
	}

}
