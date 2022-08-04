package com.giantlink.project.models.responses;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectResponse {

	private Long id;
	private String projectName;
	private String projectType;
	private Date startDate;
	private Date finishDate;

	private TeamResponse team;
}
