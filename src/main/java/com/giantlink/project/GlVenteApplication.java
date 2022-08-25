
package com.giantlink.project;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.giantlink.project.entities.ERole;
import com.giantlink.project.entities.Project;
import com.giantlink.project.entities.Role;
import com.giantlink.project.entities.Team;
import com.giantlink.project.repositories.ProjectRepository;
import com.giantlink.project.repositories.RoleRepository;
import com.giantlink.project.repositories.TeamRepository;

@SpringBootApplication
public class GlVenteApplication implements CommandLineRunner {

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(GlVenteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// insert default project
		Optional<Project> findProject = projectRepository.findByProjectName("default_project");
		Project pr = Project.builder().projectName("default_project").projectType("default").startDate(new Date())
				.finishDate(new Date()).build();
		if (findProject.isEmpty()) {
			projectRepository.save(pr);
		}

		// insert default team
		Optional<Team> findTeam = teamRepository.findByteamName("default_team");
		if (findTeam.isEmpty()) {
			Team tm = Team.builder().teamName("default_team").project(pr).build();
			teamRepository.save(tm);
		}

		// insert roles
		for (ERole erole : ERole.values()) {
			Optional<Role> findRole = roleRepository.findByName(erole);
			if (findRole.isEmpty()) {
				Role role = Role.builder().name(erole).build();
				roleRepository.save(role);
			}

		}

		System.out.println("Bienvenue dans le portail de vente");
	}
}