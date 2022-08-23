package com.giantlink.project.models.requests;

import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {

	private String projectName;
	private String projectType;
	private Date startDate;
	private Date finishDate;

}
