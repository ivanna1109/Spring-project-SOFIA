package com.ris.izdavacka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(value="model")
public class KnjigeSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnjigeSpringApplication.class, args);
	}

}
