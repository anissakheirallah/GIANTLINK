package com.giantlink.project.models.responses;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamResponse {

	private Long id;
	private String teamName;

	private Set<UserResponse> sups;

	private Set<ProjectResponse> projects;
}
