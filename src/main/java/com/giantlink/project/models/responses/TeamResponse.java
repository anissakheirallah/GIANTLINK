package com.giantlink.project.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamResponse {

	private Long id;
	private String teamName;
	
	private UserResponse sup;
	
	private ProjectResponse project;
}
