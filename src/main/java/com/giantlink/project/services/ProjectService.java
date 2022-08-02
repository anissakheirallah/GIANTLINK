package com.giantlink.project.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.project.models.requests.ProjectRequest;
import com.giantlink.project.models.responses.ProjectResponse;

public interface ProjectService {

	ProjectResponse addProject(ProjectRequest project);

	void deleteProject(Long id);

	ProjectResponse updateProject(Long id, ProjectRequest Project);

	ProjectResponse getProject(Long id);

	List<ProjectResponse> getProjects();

	Map<String, Object> getAllProjectsPaginations(Pageable pageable);
}
