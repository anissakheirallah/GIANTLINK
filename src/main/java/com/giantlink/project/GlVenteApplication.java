package com.giantlink.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.giantlink.project.entities.Role;
import com.giantlink.project.entities.User;
import com.giantlink.project.models.requests.UserRequest;
import com.giantlink.project.services.UserService;

@SpringBootApplication
public class GlVenteApplication implements CommandLineRunner{
	
	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(GlVenteApplication.class, args);
	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Bienvenue dans le portail de vente");
		
		/*UserRequest us = new UserRequest("brem","brem@gmail.com","123456",1l);
		UserRequest us2 = new UserRequest("brahim","brahim@gmail.com","123456",2l);
		UserRequest us3 = new UserRequest("br2","br2@gmail.com","123456",3l);

		userService.addUser(us);
		userService.addUser(us2);
		userService.addUser(us3);*/
		
	}


}
