package com.giantlink.project.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giantlink.project.entities.Project;
import com.giantlink.project.entities.Team;
import com.giantlink.project.exceptions.GlAlreadyExistException;
import com.giantlink.project.exceptions.GlNotFoundException;
import com.giantlink.project.mappers.ProjectMapper;
import com.giantlink.project.models.requests.ProjectRequest;
import com.giantlink.project.models.responses.ProjectResponse;
import com.giantlink.project.repositories.ProjectRepository;
import com.giantlink.project.repositories.TeamRepository;
import com.giantlink.project.services.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Override
	public ProjectResponse addProject(ProjectRequest projectRequest)
			throws GlNotFoundException, GlAlreadyExistException {
		Optional<Project> projectSearch = projectRepository.findByprojectName(projectRequest.getProjectName());
		if (projectSearch.isPresent()) {
			throw new GlAlreadyExistException(projectRequest.getProjectName(), Project.class.getSimpleName());
		} else {
			Optional<Team> team = teamRepository.findById(projectRequest.getIdTeam());
			if (team.isEmpty()) {
				throw new GlNotFoundException(projectRequest.getIdTeam().toString(), Team.class.getSimpleName());
			}
			Project project = Project.builder().projectName(projectRequest.getProjectName())
					.projectType(projectRequest.getProjectType()).team(team.get())
					.startDate(projectRequest.getStartDate()).finishDate(projectRequest.getFinishDate()).build();
			return ProjectMapper.INSTANCE.mapEntity(projectRepository.save(project));
		}
	}

	@Override
	public void deleteProject(Long id) throws GlNotFoundException {
		Optional<Project> projectSearch = projectRepository.findById(id);
		if (projectSearch.isEmpty()) {
			throw new GlNotFoundException(id.toString(), Project.class.getSimpleName());
		} else {
			projectRepository.delete(projectSearch.get());
		}
	}

	@Override
	public ProjectResponse updateProject(Long id, ProjectRequest Project) throws GlNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProjectResponse getProject(Long id) throws GlNotFoundException {
		Optional<Project> projectSearch = projectRepository.findById(id);
		if (projectSearch.isEmpty()) {
			throw new GlNotFoundException(id.toString(), Project.class.getSimpleName());
		} else {
			return ProjectMapper.INSTANCE.mapEntity(projectSearch.get());
		}
	}

	@Override
	public List<ProjectResponse> getProjects() {
		return ProjectMapper.INSTANCE.mapResponses(projectRepository.findAll());
	}

	@Override
	public Map<String, Object> getAllProjectsPaginations(Pageable pageable) {
		List<ProjectResponse> projectResponses = new ArrayList<>();
		Page<Project> projects = projectRepository.findAll(pageable);

		projects.getContent().forEach(project -> {
			projectResponses.add(ProjectMapper.INSTANCE.mapEntity(project));
		});

		Map<String, Object> requestResponse = new HashMap<>();
		requestResponse.put("content", projectResponses);
		requestResponse.put("currentPage", projects.getNumber());
		requestResponse.put("totalElements", projects.getTotalElements());
		requestResponse.put("totalPages", projects.getTotalPages());
		return requestResponse;
	}

}
