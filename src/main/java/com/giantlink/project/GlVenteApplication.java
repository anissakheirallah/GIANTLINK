
package com.giantlink.project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GlVenteApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GlVenteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Bienvenue dans le portail de vente");
	}
}