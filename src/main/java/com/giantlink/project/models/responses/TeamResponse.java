package com.giantlink.project.models.responses;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse {

	private Long id;
	private String teamName;

	private Set<UserResponse> team_users;

	private Set<ProjectResponse> projects;
}
