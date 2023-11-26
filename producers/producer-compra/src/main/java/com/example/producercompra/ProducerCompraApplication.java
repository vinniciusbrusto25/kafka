package com.example.producercompra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.producercompra.service.NovaCompraService;

@SpringBootApplication
public class ProducerCompraApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerCompraApplication.class, args);

		NovaCompraService novaCompraService = new NovaCompraService();

		for(int i=0; i<10; i++) {
			novaCompraService.produzirNovaMensagem();
		}
	}

}
