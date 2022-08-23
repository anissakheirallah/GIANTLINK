package com.giantlink.project.models.responses;

import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProjectResponse {

	private Long id;
	private String projectName;
	private String projectType;
	private Date startDate;
	private Date finishDate;

	private Set<TeamResponse> teams;
}
