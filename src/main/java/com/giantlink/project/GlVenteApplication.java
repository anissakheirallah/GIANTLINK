
package com.giantlink.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.giantlink.project.repositories.TeamRepository;

@SpringBootApplication
public class GlVenteApplication implements CommandLineRunner {

	@Autowired
	TeamRepository teamRepository;

	public static void main(String[] args) {
		SpringApplication.run(GlVenteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Bienvenue dans le portail de vente");

		/*
		 * User us1 = new User(); User us2 = new User(); Set<User> users = new
		 * HashSet<>(); users.add(us2); users.add(us1);
		 * 
		 * Team tm = new Team(); tm.setId(5l); tm.setTeamName("tm test");
		 * tm.setTeam_users(users);
		 * 
		 * Project pr = new Project(); pr.setId(56l); pr.setProjectName("pr name");
		 * pr.setProjectType("test type"); pr.setStartDate(new Date());
		 * pr.setFinishDate(new Date()); pr.setTeam(tm);
		 * 
		 * Set<Project> prs = new HashSet<>(); prs.add(pr); tm.setProjects(prs);
		 * 
		 * System.out.println(ProjectMapper.INSTANCE.mapEntity(pr));
		 */
		// System.out.println(TeamMapper.INSTANCE.mapEntity(tm));

//		Pageable pageable = PageRequest.of(0, 10);
//		TeamResponse tm = TeamMapper.INSTANCE.mapEntity(teamRepository.findAll(pageable).getContent().get(0));
//		System.out.println(tm.getTeamName()); 
	}
}