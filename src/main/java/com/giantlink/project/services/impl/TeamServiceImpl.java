package com.giantlink.project.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giantlink.project.entities.Project;
import com.giantlink.project.entities.Team;
import com.giantlink.project.entities.User;
import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.mappers.TeamMapper;
import com.giantlink.project.models.requests.TeamRequest;
import com.giantlink.project.models.responses.TeamResponse;
import com.giantlink.project.repositories.ProjectRepository;
import com.giantlink.project.repositories.TeamRepository;
import com.giantlink.project.repositories.UserRepository;
import com.giantlink.project.services.TeamService;

@Service
public class TeamServiceImpl implements TeamService {

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Override
	public TeamResponse addTeam(TeamRequest teamRequest) throws GlNotFoundException, GlAlreadyExistException {
		Optional<Team> teamSearch = teamRepository.findByteamName(teamRequest.getTeamName());
		if (teamSearch.isEmpty()) {
			throw new GlAlreadyExistException(teamRequest.getTeamName(), Team.class.getSimpleName());
		}
		Set<User> users = new HashSet<>();
		for (Long idSup : teamRequest.getIdsSup()) {
			Optional<User> userSearch = userRepository.findById(idSup);
			if (userSearch.isEmpty()) {
				throw new GlNotFoundException(idSup.toString(), User.class.getSimpleName());
			} else {
				users.add(userSearch.get());
			}
		}
		Set<Project> projects = new HashSet<>();
		for (Long idProject : teamRequest.getIdsProject()) {
			Optional<Project> projectSearch = projectRepository.findById(idProject);
			if (projectSearch.isEmpty()) {
				throw new GlNotFoundException(idProject.toString(), Project.class.getSimpleName());
			} else {
				projects.add(projectSearch.get());
			}
		}

		Team team = Team.builder().teamName(teamRequest.getTeamName()).sups(users).projects(projects).build();
		return TeamMapper.INSTANCE.mapEntity(teamRepository.save(team));
	}

	@Override
	public void deleteTeam(Long id) throws GlNotFoundException {
		Optional<Team> teamSearch = teamRepository.findById(id);
		if (teamSearch.isEmpty()) {
			throw new GlNotFoundException(id.toString(), Team.class.getSimpleName());
		} else {
			teamRepository.delete(teamSearch.get());
		}
	}

	@Override
	public TeamResponse updateTeam(Long id, TeamRequest teamRequest) throws GlNotFoundException {
		Optional<Team> teamSearch = teamRepository.findById(id);
		if (teamSearch.isEmpty()) {
			throw new GlNotFoundException(id.toString(), Team.class.getSimpleName());
		}
		Set<User> users = new HashSet<>();
		for (Long idSup : teamRequest.getIdsSup()) {
			Optional<User> userSearch = userRepository.findById(idSup);
			if (userSearch.isEmpty()) {
				throw new GlNotFoundException(idSup.toString(), User.class.getSimpleName());
			} else {
				users.add(userSearch.get());
			}
		}
		Set<Project> projects = new HashSet<>();
		for (Long idProject : teamRequest.getIdsProject()) {
			Optional<Project> projectSearch = projectRepository.findById(idProject);
			if (projectSearch.isEmpty()) {
				throw new GlNotFoundException(idProject.toString(), Project.class.getSimpleName());
			} else {
				projects.add(projectSearch.get());
			}
		}
		teamSearch.get().setProjects(projects);
		teamSearch.get().setSups(users);
		teamSearch.get().setTeamName(teamRequest.getTeamName());
		return TeamMapper.INSTANCE.mapEntity(teamRepository.save(teamSearch.get()));
	}

	@Override
	public TeamResponse getTeam(Long id) throws GlNotFoundException {
		Optional<Team> teamSearch = teamRepository.findById(id);
		if (teamSearch.isEmpty()) {
			throw new GlNotFoundException(id.toString(), Team.class.getSimpleName());
		} else {
			return TeamMapper.INSTANCE.mapEntity(teamSearch.get());
		}
	}

	@Override
	public List<TeamResponse> getTeams() {
		return TeamMapper.INSTANCE.mapResponses(teamRepository.findAll());
	}

	@Override
	public Map<String, Object> getAllPaginations(Pageable pageable) {
		List<TeamResponse> teamResponses = new ArrayList<>();
		Page<Team> teams = teamRepository.findAll(pageable);

		teams.getContent().forEach(team -> {
			teamResponses.add(TeamMapper.INSTANCE.mapEntity(team));
		});

		Map<String, Object> requestResponse = new HashMap<>();
		requestResponse.put("content", teamResponses);
		requestResponse.put("currentPage", teams.getNumber());
		requestResponse.put("totalElements", teams.getTotalElements());
		requestResponse.put("totalPages", teams.getTotalPages());
		return requestResponse;
	}

}
