
package com.giantlink.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.giantlink.project.services.TeamService;
import com.giantlink.project.services.UserService;

@SpringBootApplication
public class GlVenteApplication implements CommandLineRunner {

	@Autowired
	UserService userService;

	@Autowired
	TeamService teamService;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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

		/*
		 * UserRequest us =
		 * UserRequest.builder().firstName("brem").lastName("arfa").language("fr")
		 * .userName("brem@gmail.com").password("123456azerty").idRole(1l).idTeam(1l).
		 * build(); userService.updateUser(1l, us); UserRequest us2 =
		 * UserRequest.builder().firstName("brahim").lastName("arfa").language("fr")
		 * .userName("brahim@gmail.com").password("123456").idRole(2l).idTeam(1l).build(
		 * ); UserRequest us3 =
		 * UserRequest.builder().firstName("br").lastName("arfa").language("eng")
		 * .userName("br@gmail.com").password("123456").idRole(3l).idTeam(2l).build();
		 * 
		 * userService.updateUser(2l, us2); userService.updateUser(3l, us3);
		 */

	}
}