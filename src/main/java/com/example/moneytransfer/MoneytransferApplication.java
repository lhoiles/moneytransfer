package com.example.moneytransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.moneytransfer", "com.moneytransfer"})
public class MoneytransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneytransferApplication.class, args);
	}

}
