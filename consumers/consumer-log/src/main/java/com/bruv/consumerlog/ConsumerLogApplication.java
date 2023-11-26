package com.bruv.consumerlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bruv.consumerlog.service.LogService;

@SpringBootApplication
public class ConsumerLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerLogApplication.class, args);

		LogService logService = new LogService();

		logService.geraLog();
	}

}
